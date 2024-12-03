<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单ID" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入订单ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务名称" prop="serviceName">
        <el-input
          v-model="queryParams.serviceName"
          placeholder="请输入服务名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="服务人员姓名" prop="personnelName" label-width="110px">
        <el-input
          v-model="queryParams.personnelName"
          placeholder="请输入服务人员姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="订单总价" prop="totalPrice">
        <el-input
          v-model="queryParams.totalPrice"
          placeholder="请输入订单总价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="选择套餐" prop="orderPackage">
        <el-input
          v-model="queryParams.orderPackage"
          placeholder="请输入选择套餐"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务地点" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入服务地点"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="订单ID" align="center" prop="orderId" />
      <el-table-column label="服务名称" align="center" prop="serviceName" />
      <el-table-column label="服务人员" align="center" prop="personnelName" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="图片地址" align="center" prop="imageUrl" />
      <el-table-column label="订单总价" align="center" prop="totalPrice" />
      <el-table-column label="订单状态" align="center">
        <template slot-scope="scope">
          <span :class="getStatusClass(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="选择套餐" align="center" prop="orderPackage" />
      <el-table-column label="服务地点" align="center" prop="location" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handlePreview(scope.row)"
          >查看详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改订单管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="订单ID">
          <span>{{ form.orderId }}</span>
        </el-form-item>
        <el-form-item label="服务名称">
          <span>{{ form.serviceName }}</span>
        </el-form-item>
        <el-form-item label="服务人员姓名">
          <span>{{ form.personnelName }}</span>
        </el-form-item>
        <el-form-item label="用户名">
          <span>{{ form.userName }}</span>
        </el-form-item>
        <el-form-item label="图片地址">
          <span>{{ form.imageUrl }}</span>
        </el-form-item>
        <el-form-item label="订单总价">
          <span>{{ form.totalPrice }}</span>
        </el-form-item>
        <el-form-item label="订单状态">
          <span :class="getStatusClass(form.status)">{{ getStatusText(form.status) }}</span>
        </el-form-item>
        <el-form-item label="选择套餐">
          <span>{{ form.orderPackage }}</span>
        </el-form-item>
        <el-form-item label="服务地点">
          <span>{{ form.location }}</span>
        </el-form-item>
<!--        <el-form-item label="服务ID">-->
<!--          <span>{{ form.serviceId }}</span>-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder } from "@/api/housekeeping/order";

export default {
  name: "Order",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单管理表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderId: null,
        serviceName: null,
        personnelName: null,
        userName: null,
        totalPrice: null,
        orderPackage: null,
        location: null,
        status: null
      },
      // 表单参数
      form: {
        orderId: null,
        serviceName: null,
        personnelName: null,
        userName: null,
        imageUrl: null,
        totalPrice: null,
        status: null,
        orderPackage: null,
        location: null,
        serviceId: null,
        servicePostId: null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单管理列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
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
      this.form = {};
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
    /** 预览按钮操作 */
    handlePreview(row) {
      this.reset();
      getOrder(row.id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "订单详情";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除订单编号为"' + row.id + '"的数据项？').then(() => {
        return delOrder(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 状态样式
    getStatusClass(status) {
      const statusMap = {
        0: 'status-waiting',    // 待商家接单
        1: 'status-processing', // 服务进行中
        2: 'status-completed',  // 已完成
        3: 'status-cancelled'   // 已取消
      }
      return statusMap[status] || ''
    },
    // 状态文本
    getStatusText(status) {
      const statusMap = {
        0: '待商家接单',
        1: '服务进行中',
        2: '已完成',
        3: '已取消'
      }
      return statusMap[status] || '未知状态'
    }
  }
};
</script>

<style lang="scss" scoped>
.status-waiting {
  color: #e6a23c;
  background-color: #fdf6ec;
  padding: 2px 6px;
  border-radius: 4px;
}

.status-processing {
  color: #409eff;
  background-color: #ecf5ff;
  padding: 2px 6px;
  border-radius: 4px;
}

.status-completed {
  color: #67c23a;
  background-color: #f0f9eb;
  padding: 2px 6px;
  border-radius: 4px;
}

.status-cancelled {
  color: #f56c6c;
  background-color: #fef0f0;
  padding: 2px 6px;
  border-radius: 4px;
}
</style>
