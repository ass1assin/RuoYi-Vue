<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="服务名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入服务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务描述" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="请输入服务描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类别名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入类别名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="每小时价格" prop="hourRate" label-width="100px">
        <el-input
          v-model="queryParams.hourRate"
          placeholder="请输入每小时价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:service:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:service:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:service:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="serviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="服务编号" align="center" prop="id" />
      <el-table-column label="服务名称" align="center" prop="name" />
      <el-table-column label="服务描述" align="center" prop="description" />
      <el-table-column label="服务类别" align="center" prop="categoryName" />
      <el-table-column label="供选择的小时数例" align="center" prop="hours" />
      <el-table-column label="每小时价格" align="center" prop="hourlyRate" />
      <el-table-column label="服务图片" align="center">
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.imageUrls && scope.row.imageUrls.length > 0"
            :src="scope.row.imageUrls[0]"
            style="width: 50px; height: 50px"
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:service:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:service:remove']"
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

    <!-- 添加或修改服务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="服务名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="服务描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入服务描述" />
        </el-form-item>
        <el-form-item label="服务类别" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择类别">
            <el-option
              v-for="category in categoryList"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="供选择的小时数例" prop="hours">
          <el-input v-model="form.hours" placeholder="请输入供选择的小时数例" />
        </el-form-item>
        <el-form-item label="每小时价格" prop="hourlyRate">
          <el-input v-model="form.hourlyRate" placeholder="请输入每小时价格" />
        </el-form-item>
        <el-form-item label="服务图片" prop="imageUrls">
          <el-upload
            class="upload-demo"
            :action="upload.url"
            :headers="upload.headers"
            :file-list="fileList"
            :on-preview="handlePreview"
            :before-upload="beforeUpload"
            :on-change="handleChange"
            :http-request="customUpload"
            :on-remove="handleRemove"
            multiple
            list-type="picture-card"
            :auto-upload="false"
            ref="upload"
          >
            <i class="el-icon-plus"></i>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过2MB</div>
          </el-upload>
          <el-dialog :visible.sync="previewVisible">
            <img width="100%" :src="previewUrl" alt="">
          </el-dialog>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listService, getService, delService, addService, updateService } from "@/api/system/service";
import { getToken } from "@/utils/auth";
import { upload } from "@/api/common/upload";
import { yuanlistCategory } from "@/api/system/category"; // 导入获取类别列表的函数

export default {
  name: "Service",
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
      // 服务格数据
      serviceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        description: null,
        categoryName: null,
        hours: null,
        hourRate: null,
      },
      // 表单参数
      form: {
        id: null,
        name: null,
        description: null,
        categoryId: null,
        hours: null,
        hourlyRate: null,
        imageUrls: []
      },
      // 表单校验
      rules: {},
      upload: {
        url: process.env.VUE_APP_BASE_API + "/common/uploads",
        headers: { Authorization: "Bearer " + getToken() }
      },
      fileList: [],
      previewVisible: false,
      previewUrl: '',
      uploadFiles: [],
      categoryList: [], // 存储服务类别列表
    };
  },
  created() {
    this.getList();
    this.getCategoryList(); // 获取服务类别列表
  },
  methods: {
    /** 查询服务列表 */
    getList() {
      this.loading = true;
      listService(this.queryParams).then(response => {
        this.serviceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 表单重置 */
    reset() {
      this.form = {
        id: null,
        name: null,
        description: null,
        categoryId: null,
        hours: null,
        hourlyRate: null,
        imageUrls: []
      };
      this.fileList = [];
      this.uploadFiles = [];
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

    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加服务";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getService(id).then(response => {
        this.form = response.data;
        if (this.form.imageUrls && this.form.imageUrls.length > 0) {
          this.fileList = this.form.imageUrls.map((url, index) => ({
            name: `图片${index + 1}`,
            url: url
          }));
        }
        this.open = true;
        this.title = "修改服务";
      });
    },

    /** 提交按钮 */
    async submitForm() {
      this.$refs["form"].validate(async valid => {
        if (valid) {
          try {
            const uploadSuccess = await this.uploadAllImages();
            if (!uploadSuccess) {
              return;
            }

            // 添加日志查看提交的数据
            console.log('提交到后端的表单数据:', this.form);

            if (this.form.id != null) {
              await updateService(this.form);
              this.$modal.msgSuccess("修改成功");
            } else {
              await addService(this.form);
              this.$modal.msgSuccess("新增成功");
            }
            this.open = false;
            this.getList();
          } catch (error) {
            console.error('提交过程错误:', error);
            this.$message.error("操作失败：" + (error.message || '请重试'));
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除服务编号为"' + ids + '"的数据项？').then(function() {
        return delService(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    // 取消按
    cancel() {
      this.open = false;
      this.reset();
    },

    /** 图片上传相关方法 */
    handlePreview(file) {
      this.previewUrl = file.url;
      this.previewVisible = true;
    },

    beforeUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传图片只能是 JPG/PNG 格式!');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!');
        return false;
      }
      return true;
    },

    handleChange(file, fileList) {
      this.fileList = fileList;
      if (file.status === 'ready') {
        this.uploadFiles.push({
          file: file.raw,
          onSuccess: () => {
            file.status = 'success';
          },
          onError: () => {
            file.status = 'error';
          }
        });
      }
    },

    customUpload() {
      return false;
    },

    handleRemove(file) {
      const index = this.uploadFiles.findIndex(item => item.file === file.raw);
      if (index !== -1) {
        this.uploadFiles.splice(index, 1);
      }
      if (file.url) {
        const urlIndex = this.form.imageUrls.indexOf(file.url);
        if (urlIndex > -1) {
          this.form.imageUrls.splice(urlIndex, 1);
        }
      }
      this.fileList = this.fileList.filter(f => f.uid !== file.uid);
    },

    /** 上传所有图片 */
    async uploadAllImages() {
      if (this.uploadFiles.length === 0) {
        return true;
      }

      try {
        // 清空之前的图片URLs
        this.form.imageUrls = [];

        // 创建FormData，一次性添加所有文件
        const formData = new FormData();
        this.uploadFiles.forEach(item => {
          formData.append('files', item.file);
        });
        formData.append('path', 'service');

        console.log('正在上传图片... formData: %o', formData)
        // 一次性上传所有文件(发送请求)
        const response = await upload(formData);
        if (response.code === 200) {
          // 假设后端返回的是URL数组
          // this.form.imageUrls = response.urls;
          this.form.imageUrls = response.urls.split(","); // 提取返回的 URLs，并保存到 imageUrls 数组
          this.uploadFiles.forEach(item => item.onSuccess(response));
          this.$message.success(`成功上传${this.uploadFiles.length}张图片`);
          this.uploadFiles = [];
          return true;
        } else {
          throw new Error(response.msg || '上传失败');
        }
      } catch (error) {
        this.$message.error("图片上传失败：" + (error.message || '请重试'));
        return false;
      }
    },

    /** 获取服务类别列表 */
    getCategoryList() {
      yuanlistCategory().then(response => {
        console.log(response); // 检查API返回的数据
        if (response.code === 200) {
          this.categoryList = response.rows; // 确保使用response.rows
        } else {
          this.$message.error(response.msg || '获取类别列表失败');
        }
      }).catch(error => {
        console.error('获取类别列表错误:', error);
        this.$message.error('获取类别列表失败');
      });
    },

    // getImageUrl(url) {
    //   if (url && url.startsWith('http://localhost:8080/profile/upload/')) {
    //     // 从URL中提取日期和文件名部分
    //     const pathPart = url.replace('http://localhost:8080/profile/upload/', '');
    //     // 转换为本地文件路径
    //     return `file:///D:/aaaa/upload/${pathPart}`;
    //   }
    //   return url;
    // }
  }
};
</script>

<style lang="scss" scoped>
.upload-demo {
  .el-upload--picture-card {
    width: 148px;
    height: 148px;
    line-height: 148px;
  }

  .el-upload-list--picture-card .el-upload-list__item {
    width: 148px;
    height: 148px;
  }
}

.el-upload__tip {
  color: #909399;
  font-size: 12px;
  margin-top: 7px;
}
</style>
