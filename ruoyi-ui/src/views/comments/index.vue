<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务名" prop="serviceName">
        <el-input
          v-model="queryParams.serviceName"
          placeholder="请输入服务名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分" prop="rating">
        <el-input
          v-model="queryParams.rating"
          placeholder="请输入评分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>


    <el-table v-loading="loading" :data="getParentComments" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="评论编号" align="center" prop="id" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="服务名" align="center" prop="serviceName" />
      <el-table-column label="评分" align="center" prop="rating" />
      <el-table-column label="评论内容" align="center">
        <template slot-scope="scope">
          <div>{{ scope.row.content }}</div>
          <!-- 子评论展示区域 -->
          <div v-if="showReplyMap[scope.row.id]" class="reply-list">
            <div v-for="reply in getReplyComments(scope.row.id)" :key="reply.id" class="reply-item">
              <div class="reply-content">
                <span class="reply-user">{{ reply.userName }}：</span>
                <span>{{ reply.content }}</span>
              </div>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="评论图片" align="center">
        <template slot-scope="scope">
          <div v-if="scope.row.imageUrls">
            <el-image
              v-for="(imageUrl, index) in scope.row.imageUrls.split(',')"
              :key="index"
              :src="imageUrl"
              style="width: 50px; height: 50px; margin-right: 5px;"
              fit="cover"
            />
          </div>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="hasReply(scope.row.id)"
            type="text"
            @click="toggleReply(scope.row.id)"
          >
            {{ showReplyMap[scope.row.id] ? '收起回复' : '查看回复' }}
          </el-button>
          <el-button
            v-if="!hasReply(scope.row.id)"
            size="mini"
            type="text"
            icon="el-icon-add"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:comments:add']"
          >回复</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:comments:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改评论对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评论内容">
          <editor v-model="commentContent" :min-height="192"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(form.id)">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listComments, getComments, delComments, addComments, updateComments } from "@/api/system/comments";

export default {
  name: "Comments",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 评论表格数据
      commentsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        serviceName: null,
        serviceId: null,
        rating: null,
        content: null,
        imageUrls: null,
        parentId: null,
      },
      // 表单参数
      form: {},
      commentContent: "",
      // parentId: null,
      // 表单校验
      rules: {
      },
      showReplyMap: {}, // 控制回复显示状态
    };
  },
  created() {
    this.getList();
  },
  computed: {
    // 只获取父评论
    getParentComments() {
      return this.commentsList.filter(item => !item.parentId);
    }
  },
  methods: {
    /** 查询评论列表 */
    getList() {
      this.loading = true;
      listComments(this.queryParams).then(response => {
        this.commentsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        serviceId: null,
        rating: null,
        content: null,
        imageUrls: null,
        parentId: null,
        createTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加评论";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form.parentId = row.id
      this.form.serviceId = row.serviceId
      this.open = true;
      this.title = "回复评论";
    },
    /** 提交按钮 */
    submitForm(id) {
      this.$refs["form"].validate(valid => {
        if (valid) {
            this.form.content =this.commentContent;
            this.form.userId = 102;
            addComments(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除评论编号为"' + ids + '"的数据项？').then(function() {
        return delComments(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/comments/export', {
        ...this.queryParams
      }, `comments_${new Date().getTime()}.xlsx`)
    },
    // 检查评论是否有回复
    hasReply(commentId) {
      return this.commentsList.some(item => item.parentId === commentId);
    },
    // 获取某条评论的所有回复
    getReplyComments(commentId) {
      return this.commentsList.filter(item => item.parentId === commentId);
    },
    // 切换回复的显示状态
    toggleReply(commentId) {
      this.$set(this.showReplyMap, commentId, !this.showReplyMap[commentId]);
    },
  }
};
</script>

<style scoped>
.operation-buttons {
  margin: 5px 0;
}
.reply-list {
  margin-top: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}
.reply-item {
  padding: 10px 15px;
  border-bottom: 1px solid #ebeef5;
}
.reply-item:last-child {
  border-bottom: none;
}
.reply-content {
  margin-bottom: 5px;
}
.reply-user {
  font-weight: bold;
  margin-right: 5px;
}
.reply-info {
  font-size: 12px;
  color: #909399;
}
</style>
