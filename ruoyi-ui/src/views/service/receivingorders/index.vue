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
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务人员ID" prop="personnelId">
        <el-input
          v-model="queryParams.personnelId"
          placeholder="请输入服务人员ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务发布ID" prop="servicePostId">
        <el-input
          v-model="queryParams.servicePostId"
          placeholder="请输入服务发布ID"
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
      <el-form-item label="图片地址" prop="imageUrl">
        <el-input
          v-model="queryParams.imageUrl"
          placeholder="请输入图片地址"
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
      <el-form-item label="服务ID" prop="serviceId">
        <el-input
          v-model="queryParams.serviceId"
          placeholder="请输入服务ID"
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
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="服务人员ID" align="center" prop="personnelId" />
      <el-table-column label="服务发布ID" align="center" prop="servicePostId" />
      <el-table-column label="服务名称" align="center" prop="serviceName" />
      <el-table-column label="图片地址" align="center" prop="imageUrl" />
      <el-table-column label="订单总价" align="center" prop="totalPrice" />
      <el-table-column label="订单状态(0进行中，1已完成，2已取消)" align="center" prop="status" />
      <el-table-column label="选择套餐" align="center" prop="orderPackage" />
      <el-table-column label="开始时间" align="center" prop="startTime" :formatter="formatDate"  />
      <el-table-column label="结束时间" align="center" prop="endTime"  :formatter="formatDate" />
      <el-table-column label="服务地点" align="center" prop="location" />
      <el-table-column label="服务ID" align="center" prop="serviceId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['housekeeping:order:edit']"
          >指派服务人员</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"

            v-hasPermi="['housekeeping:order:edit']"
          >取消接单</el-button>
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

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!-- 服务人员列表 -->
        <el-table
          :data="personnelList"
          style="width: 100%"
          @row-click="handleRowClick"
          highlight-current-row
        >
          <el-table-column label="服务人员ID" prop="id" />
          <el-table-column label="服务人员姓名" prop="name" />
          <el-table-column label="联系电话" prop="phone" />
          <el-table-column label="擅长服务类型" prop="serviceType" />
          <el-table-column label="资格认证" prop="qualification" />
          <el-table-column label="经验" prop="experience" />
          <el-table-column label="工作日" prop="workDay" />
        </el-table>
        <pagination
          v-show="totaltwo>0"
          :total="totaltwo"
          :page.sync="query.pageNum"
          :limit.sync="query.pageSize"
          @pagination="getAvailablePersonnels"
        />
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
// import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/housekeeping/order";
import {
  addReceivingOrders,
  delReceivingOrders,
  getAvailablePersonnel,
  getReceivingOrders,
  listReceivingOrders,
  updateReceivingOrders
} from "@/api/system/receivingorders";

export default {
  name: "Receivingorders",
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

      totaltwo: 0,
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
        userId: null,
        personnelId: null,
        servicePostId: null,
        serviceName: null,
        imageUrl: null,
        totalPrice: null,
        status: null,
        orderPackage: null,
        startTime: null,
        endTime: null,
        location: null,
        serviceId: null
      },
      query: {
        pageNum: 1,
        pageSize: 10,
        startTime: null,
        endTime: null,
        location: null,
      },
      // 表单参数
      // form: {},
      form: {
        // personnelId: null, // 服务人员ID
      },
      personnelList: [], // 服务人员列表
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        personnelId: [
          { required: true, message: "服务人员ID不能为空", trigger: "blur" }
        ],
        servicePostId: [
          { required: true, message: "服务发布ID不能为空", trigger: "blur" }
        ],
        totalPrice: [
          { required: true, message: "订单总价不能为空", trigger: "blur" }
        ],
        location: [
          { required: true, message: "服务地点不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    formatDate(row, column, cellValue, index) {
      if (!cellValue) return '';
      const date = new Date(cellValue);
      return date.toLocaleString(); // 或者使用自定义的格式：date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
    },
    /** 查询订单管理列表 */
    getList() {
      this.loading = true;
      listReceivingOrders(this.queryParams).then(response => {
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
      this.form = {
        id: null,
        orderId: null,
        userId: null,
        personnelId: null,
        servicePostId: null,
        serviceName: null,
        imageUrl: null,
        totalPrice: null,
        status: null,
        createTime: null,
        orderPackage: null,
        startTime: null,
        endTime: null,
        updateTime: null,
        location: null,
        serviceId: null
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
      this.title = "添加订单管理";
    },
    handleRowClick(row) {
      this.form.personnelId = row.id;  // 将选中的服务人员ID存储到表单中
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.open = true;
      this.title = "指派服务人员";
      const startTime = new Date(row.startTime);
      const endTime = new Date(row.endTime);

      this.query.startTime = startTime.toLocaleString('sv-SE');  // 使用ISO格式本地时区时间
      this.query.endTime = endTime.toLocaleString('sv-SE');  // 使用ISO格式本地时区时间

      this.query.location = row.location.split(' ')[0];
      this.getAvailablePersonnels();
      // getAvailablePersonnel(this.query).then(response => {
      //   this.personnelList = response.rows; // 保存服务人员列表
      //   this.totaltwo = response.total;
      //   this.form.personnelId = null; // 清空之前的服务人员ID
      //   this.form.id = row.id;
      // });
    },
    getAvailablePersonnels(){
      // const startTime = new Date(row.startTime);
      // const endTime = new Date(row.endTime);
      //
      // this.query.startTime = startTime.toLocaleString('sv-SE');  // 使用ISO格式本地时区时间
      // this.query.endTime = endTime.toLocaleString('sv-SE');  // 使用ISO格式本地时区时间
      //
      // this.query.location = row.location.split(' ')[0];
      getAvailablePersonnel(this.query).then(response => {
        this.personnelList = response.rows; // 保存服务人员列表
        this.totaltwo = response.total;
        this.form.personnelId = null; // 清空之前的服务人员ID
        this.form.id = row.id;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReceivingOrders(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          }
          else {
            addReceivingOrders(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除订单管理编号为"' + ids + '"的数据项？').then(function () {
        return delReceivingOrders(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/receivingOrders/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
