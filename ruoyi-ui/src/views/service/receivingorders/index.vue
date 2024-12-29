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
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="服务人员姓名" align="center" prop="personnelName" />
      <el-table-column label="服务名称" align="center" prop="serviceName" />
      <el-table-column label="服务图片" align="center">
        <template slot-scope="scope">
          <el-image
            :src="scope.row.imageUrl"
            style="width: 50px; height: 50px"
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="订单总价" align="center" prop="totalPrice" />
      <el-table-column label="订单状态" align="center" prop="status">
        <template slot-scope="scope">
          <span :class="getStatusClass(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="选择套餐" align="center" prop="orderPackage" />
      <el-table-column label="开始时间" align="center" prop="startTime" :formatter="formatDate"  />
      <el-table-column label="结束时间" align="center" prop="endTime"  :formatter="formatDate" />
      <el-table-column label="服务地点" align="center" prop="location" />
      <el-table-column label="服务名" align="center" prop="serviceName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status !== 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['housekeeping:order:edit']"
          >指派服务人员</el-button>
          <el-button
            v-if="scope.row.status !== 1"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleReject(scope.row)"
            v-hasPermi="['housekeeping:order:edit']"
          >拒绝接单</el-button>
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

    <el-dialog :title="'拒绝接单'" :visible.sync="rejectDialogVisible" width="400px" append-to-body>
      <div style="text-align: center;">
        <p>确认要拒绝该订单吗？</p>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmReject">确 定</el-button>
        <el-button @click="rejectDialogVisible = false">取 消</el-button>
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
import {updatePersonnel} from "@/api/housekeeping/personnel";

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
      updataParams: {

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

        totalPrice: [
          { required: true, message: "订单总价不能为空", trigger: "blur" }
        ],
        location: [
          { required: true, message: "服务地点不能为空", trigger: "blur" }
        ],
      },
      rejectDialogVisible: false,
      rejectForm: {
        id: null
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
    /** 拒绝接单操作 */
    handleReject(row) {
      this.rejectForm.id = row.id;
      this.rejectDialogVisible = true;
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
      this.form.id = row.id;
      this.getAvailablePersonnels();
    },
    getAvailablePersonnels(){
      getAvailablePersonnel(this.query).then(response => {
        this.personnelList = response.rows; // 保存服务人员列表
        this.totaltwo = response.total;
        this.form.personnelId = null; // 清空之前的服务人员ID
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
    },
    // 获取状态样式类名
    getStatusClass(status) {
      const statusMap = {
        0: 'status-waiting',    // 待商家接单
        1: 'status-processing', // 服务进行中
        2: 'status-completed',  // 已完成
        3: 'status-cancelled'   // 已取消
      }
      return statusMap[status] || ''
    },

    // 获取状态显示文本
    getStatusText(status) {
      const statusMap = {
        0: '待接单',
        1: '服务进行中',
        2: '已完成',
        3: '已取消'
      }
      return statusMap[status] || '未知状态'
    },
    /** 确认拒绝接单 */
    confirmReject() {
      const updateObj = {
        id: this.rejectForm.id,
        status: 4  // 将状态改为4（拒绝接单）
      };

      updateReceivingOrders(updateObj).then(response => {
        this.$modal.msgSuccess("已拒绝");
        this.rejectDialogVisible = false;
        this.getList();
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.status-waiting {
  color: #e6a23c;          // 橙色
  background-color: #fdf6ec;
  padding: 2px 6px;
  border-radius: 4px;
}

.status-processing {
  color: #409eff;          // 蓝色
  background-color: #ecf5ff;
  padding: 2px 6px;
  border-radius: 4px;
}

.status-completed {
  color: #67c23a;          // 绿色
  background-color: #f0f9eb;
  padding: 2px 6px;
  border-radius: 4px;
}

.status-cancelled {
  color: #f56c6c;          // 红色
  background-color: #fef0f0;
  padding: 2px 6px;
  border-radius: 4px;
}
</style>
