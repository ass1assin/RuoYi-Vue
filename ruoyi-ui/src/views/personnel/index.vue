<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="关联的用户ID" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入关联的用户ID"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="服务人员姓名" prop="name" label-width="110px">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入服务人员姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务人员电话" prop="phone" label-width="110px">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入服务人员电话"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务人员邮箱" prop="email" label-width="110px">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入服务人员邮箱"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务人员所在城市" prop="location" label-width="140px">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入服务人员所在城市"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="服务人员工作周期" prop="workDay" label-width="180px">
        <el-input
          v-model="queryParams.workDay"
          placeholder="请输入服务人员的工作周期"
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
          v-hasPermi="['housekeeping:personnel:add']"
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
          v-hasPermi="['housekeeping:personnel:edit']"
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
          v-hasPermi="['housekeeping:personnel:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="personnelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="服务人员ID" align="center" prop="id" />
<!--      <el-table-column label="关联的用户ID" align="center" prop="userId" />-->
      <el-table-column label="服务人员姓名" align="center" prop="name" />
      <el-table-column label="服务人员电话" align="center" prop="phone" />
<!--      <el-table-column label="服务人员状态" align="center" prop="status" />-->
      <el-table-column label="服务人员邮箱" align="center" prop="email" />
      <el-table-column label="服务经验" align="center" prop="experience" />
      <el-table-column label="服务人员所在城市" align="center" prop="location" />
<!--      <el-table-column label="所在城市id" align="center" prop="cityId" />-->
      <el-table-column label="资质认证" align="center" prop="qualification" />
      <el-table-column label="擅长的服务类型" align="center" prop="serviceType" />
      <el-table-column label="服务人员的工作周期" align="center" prop="workDay" />
      <el-table-column label="服务人员的工作时间" align="center" width="300">
        <template slot-scope="scope">
          <div v-if="scope.row.workTimes">
            <div>{{ getTimeRangeText(scope.row.workTimes, 0) }}</div>
          </div>
          <span v-else>暂未设置</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['housekeeping:personnel:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['housekeeping:personnel:remove']"
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

    <!-- 添加或修改服务人员管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
<!--        <el-form-item label="关联的用户ID" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入关联的用户ID" />-->
<!--        </el-form-item>-->
        <el-form-item label="服务人员姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入服务人员姓名" />
        </el-form-item>
        <el-form-item label="服务人员电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入服务人员电话" />
        </el-form-item>
        <el-form-item label="服务人员邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入服务人员邮箱" />
        </el-form-item>
        <el-form-item label="服务经验" prop="experience">
          <el-input v-model="form.experience" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="服务人员所在城市" prop="location">
          <el-input v-model="form.location" placeholder="请输入服务人员所在城市" />
        </el-form-item>
<!--        <el-form-item label="所在城市id" prop="cityId">-->
<!--          <el-input v-model="form.cityId" placeholder="请输入所在城市id" />-->
<!--        </el-form-item>-->
        <el-form-item label="资质认证" prop="qualification">
          <el-input v-model="form.qualification" placeholder="请输入资质认证" />
        </el-form-item>
        <el-form-item label="服务人员的工作周期" prop="workDay">
          <el-input v-model="form.workDay" placeholder="请输入服务人员的工作周期" />
        </el-form-item>
        <el-form-item label="上午工作时间" prop="morningTime">
          <el-time-picker
            v-model="workTimeMorningStart"
            format="HH:mm"
            placeholder="起始时间"
            style="width: 180px;"
            @change="handleTimeChange"
          />
          <span style="margin: 0 10px;">至</span>
          <el-time-picker
            v-model="workTimeMorningEnd"
            format="HH:mm"
            placeholder="结束时间"
            style="width: 180px;"
            @change="handleTimeChange"
          />
        </el-form-item>
        <el-form-item label="下午工作时间" prop="afternoonTime">
          <el-time-picker
            v-model="workTimeAfternoonStart"
            format="HH:mm"
            placeholder="起始时间"
            style="width: 180px;"
            @change="handleTimeChange"
          />
          <span style="margin: 0 10px;">至</span>
          <el-time-picker
            v-model="workTimeAfternoonEnd"
            format="HH:mm"
            placeholder="结束时间"
            style="width: 180px;"
            @change="handleTimeChange"
          />
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
import { listPersonnel, getPersonnel, delPersonnel, addPersonnel, updatePersonnel } from "@/api/housekeeping/personnel";

export default {
  name: "Personnel",
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
      // 服务人员管理表格数据
      personnelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        name: null,
        phone: null,
        // status: null,
        email: null,
        experience: null,
        location: null,
        cityId: null,
        qualification: null,
        serviceType: null,
        workDay: null
      },
      // 表单参数
      form: {
        id: null,
        userId: null,
        name: null,
        phone: null,
        // status: null,
        email: null,
        experience: null,
        createTime: null,
        updateTime: null,
        location: null,
        cityId: null,
        qualification: null,
        serviceType: null,
        workDay: null,
        workTimes: null
      },
      // 表单校验
      rules: {
        // userId: [
        //   { required: true, message: "关联的用户ID不能为空", trigger: "blur" }
        // ],
        name: [
          { required: true, message: "服务人员姓名不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "服务人员电话不能为空", trigger: "blur" }
        ],
        location: [
          { required: true, message: "服务人员所在城市不能为空", trigger: "blur" }
        ],
        morningTime: [
          { required: true, message: "请设置上午工作时间", trigger: "change" }
        ],
        afternoonTime: [
          { required: true, message: "请设置下午工作时间", trigger: "change" }
        ]
      },
      workTimeMorningStart: null,
      workTimeMorningEnd: null,
      workTimeAfternoonStart: null,
      workTimeAfternoonEnd: null
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询服务人员管理列表 */
    getList() {
      this.loading = true;
      listPersonnel(this.queryParams).then(response => {
        this.personnelList = response.rows;
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
        name: null,
        phone: null,
        // status: null,
        email: null,
        experience: null,
        createTime: null,
        updateTime: null,
        location: null,
        cityId: null,
        qualification: null,
        serviceType: null,
        workDay: null,
        workTimes: null
      };
      this.workTimeMorningStart = null;
      this.workTimeMorningEnd = null;
      this.workTimeAfternoonStart = null;
      this.workTimeAfternoonEnd = null;
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
      this.title = "添加服务人员管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPersonnel(id).then(response => {
        this.form = response.data;
        // 解析已有的工作时间
        if (this.form.workTimes) {
          const [morningTime, afternoonTime] = this.form.workTimes.split('|');
          if (morningTime) {
            const [mStart, mEnd] = morningTime.split('-');
            this.workTimeMorningStart = this.$moment(`2000-01-01 ${mStart}`).toDate();
            this.workTimeMorningEnd = this.$moment(`2000-01-01 ${mEnd}`).toDate();
          }
          if (afternoonTime) {
            const [aStart, aEnd] = afternoonTime.split('-');
            this.workTimeAfternoonStart = this.$moment(`2000-01-01 ${aStart}`).toDate();
            this.workTimeAfternoonEnd = this.$moment(`2000-01-01 ${aEnd}`).toDate();
          }
        }
        this.open = true;
        this.title = "修改服务人员管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePersonnel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPersonnel(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除服务人员管理编号为"' + ids + '"的数据项？').then(function() {
        return delPersonnel(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('housekeeping/personnel/export', {
        ...this.queryParams
      }, `personnel_${new Date().getTime()}.xlsx`)
    },
    // 处理时间变化
    handleTimeChange() {
      if (this.workTimeMorningStart && this.workTimeMorningEnd &&
          this.workTimeAfternoonStart && this.workTimeAfternoonEnd) {
        const formatTime = (time) => {
          return this.$moment(time).format('HH:mm');
        };
        const morningTime = `${formatTime(this.workTimeMorningStart)}-${formatTime(this.workTimeMorningEnd)}`;
        const afternoonTime = `${formatTime(this.workTimeAfternoonStart)}-${formatTime(this.workTimeAfternoonEnd)}`;
        this.form.workTimes = `${morningTime}|${afternoonTime}`;
      }
    },

    // 获取显示的时间段文本
    getTimeRangeText(workTimes, index) {
      if (!workTimes) return '暂未设置';
      const timeRanges = workTimes.split('|');
      return timeRanges[index] || '暂未设置';
    }
  }
};
</script>

<style lang="scss" scoped>
.el-time-picker {
  margin-right: 10px;
}
</style>
