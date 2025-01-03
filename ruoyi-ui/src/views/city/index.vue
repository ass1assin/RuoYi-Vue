<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="城市名称" prop="cityName">
        <el-input
          v-model="queryParams.cityName"
          placeholder="请输入城市名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="城市基础服务费用" prop="baseServiceFee" label-width="180px">
        <el-input
          v-model="queryParams.baseServiceFee"
          placeholder="请输入城市基础服务费用"
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
          v-hasPermi="['system:city:add']"
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
          v-hasPermi="['system:city:edit']"
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
          v-hasPermi="['system:city:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="城市ID" align="center" prop="id" />
      <el-table-column label="城市名称" align="center" prop="cityName" />
      <el-table-column label="开通状态" align="center">
        <template slot-scope="scope">
            <span :class="getStatusClass(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </span>
        </template>
      </el-table-column>
      <el-table-column label="城市基础服务费用" align="center" prop="baseServiceFee" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:city:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:city:remove']"
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

    <!-- 添加或修改城市对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="城市名称" prop="cityName">
          <el-input v-model="form.cityName" placeholder="请输入城市名称" />
        </el-form-item>
        <el-form-item label="开通状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">已开通</el-radio>
            <el-radio :label="0">未开通</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="城市基础服务费用" prop="baseServiceFee">
          <el-input v-model="form.baseServiceFee" placeholder="请输入城市基础服务费用" />
        </el-form-item>
        <!-- 新增服务多选项 -->
        <el-form-item label="开通服务" prop="services">
          <el-select
            v-model="selectedServiceIds"
            multiple
            collapse-tags
            placeholder="请选择开通服务"
            style="width: 100%"
          >
            <el-option
              v-for="service in form.services"
              :key="service.id"
              :label="service.name"
              :value="service.id"
            />
          </el-select>
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
import { listCity, getCity, delCity, addCity, updateCity } from "@/api/system/city";

export default {
  name: "City",
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
      // 城市表格数据
      cityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        cityName: null,
        status: null,
        baseServiceFee: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        cityName: [
          { required: true, message: "城市名称不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "请选择开通状态", trigger: "change" }
        ]
      },
      selectedServiceIds: [], // 已选择的服务ID数组
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getStatusClass(status) {
      if (status === 1) {
        return 'status-open'; // 已开通
      } else if (status === 0) {
        return 'status-closed'; // 未开通
      }
      return '';
    },
    getStatusText(status) {
      if (status === 1) {
        return '已开通';
      } else if (status === 0) {
        return '未开通';
      }
      return '未知状态';
    },

    /** 查询城市列表 */
    getList() {
      this.loading = true;
      listCity(this.queryParams).then(response => {
        this.cityList = response.rows;
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
        cityName: null,
        status: 0,
        baseServiceFee: null,
        services: []
      };
      this.selectedServiceIds = []; // 重置选中的服务ID
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
      this.title = "添加城市";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCity(id).then(response => {
        this.form = response.data;
        // 初始化选中的服务ID（筛选出 selected 为 true 的服务）
        this.selectedServiceIds = this.form.services
          .filter(service => service.selected)
          .map(service => service.id);
        this.open = true;
        this.title = "修改城市";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 更新服务的选中状态
          if (this.form.services) {
            this.form.services = this.form.services.map(service => ({
              ...service,
              selected: this.selectedServiceIds.includes(service.id)
            }));
          }

          if (this.form.id != null) {
            updateCity(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCity(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除城市编号为"' + ids + '"的数据项？').then(function() {
        return delCity(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/city/export', {
        ...this.queryParams
      }, `city_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style lang="scss" scoped>
.status-open {
  color: #3e8e41; /* 淡绿色 */
  background-color: #eaffea; /* 淡绿色背景 */
  padding: 2px 6px;
  border-radius: 4px;
}

.status-closed {
  color: #8a5c3e; /* 淡棕色 */
  background-color: #f9e8e3; /* 淡棕色背景 */
  padding: 2px 6px;
  border-radius: 4px;
}

// 添加样式使复选框组更美观
.el-checkbox-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  
  .el-checkbox {
    margin-right: 20px;
    margin-bottom: 10px;
  }
}

// 可以添加一些自定义样式
.el-select {
  width: 100%;
}

</style>
