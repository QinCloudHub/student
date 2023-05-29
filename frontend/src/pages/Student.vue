<template>
  <div id="student">
    <div class="titleBar"><span>病人管理</span></div>
    <div class="container">
      <div class="body">
        <div class="operationBar">
          <el-button type="primary" @click="dialogVisible=true">添加病人</el-button>
          <el-dialog
            top="10vh"
            title="病人信息"
            :visible.sync="dialogVisible"
            :destroy-on-close="true"
            @opened="dialogOpen"
            @close="dialogClose"
            width="40%">
            <StudentDialog
              ref="dialog"
              :teams="this.teams"
              :teamId="this.currentTeamId"
              :initTeamLabel="this.initTeamLabel"
              :isCreate="this.isAddButton"
              :oldStudentData="{ ...this.oldStudentData }"
            ></StudentDialog>
          </el-dialog>
          <div class="selectBar">
            <el-select
              @change="changeLabel"
              @clear="()=>{
                let path = this.$router.history.current.path;
                this.$router.push(path);
              }"
              v-model="initTeamLabel" clearable placeholder="选择科室">
              <el-option
                v-for="item in teams"
                :key="item.id"
                :label="getLabel(item)"
                :value="item.id">
              </el-option>
            </el-select>
          </div>
          <div class="findBar">
            <input v-model="findKey" type="text" placeholder="根据病人姓名查询" @keyup.enter="findLikeName"></input>
            <el-button type="primary" @click="findLikeName"><i class="el-icon-search"></i></el-button>
            <el-button type="primary" @click="()=>{this.findKey=null;findLikeName();}"><i
              class="el-icon-refresh-right"></i>重置
            </el-button>
          </div>
        </div>
        <!--表格-->
        <el-table
          v-loading.lock="loading"
          :data="studentsData.content"
          border
          style="width: 96%; margin-left: 1vw; margin-top: 5vh;">
          <el-table-column
            label=" #"
            width="50">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="病号"
            width="100">
            <!--通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据-->
            <template slot-scope="scope">
              <i class="el-icon-info"></i>
              <span style="margin-left: 10px">{{ scope.row.studentNumber }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="科室"
            width="200">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.teamFullName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="姓名"
            width="100">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium" type="info">{{ scope.row.name }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="性别"
            show-overflow-tooltip
            width="50">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{getGender(scope.row.gender)}}</span>
            </template>
          </el-table-column>

          <el-table-column
            label="出生日期"
            width="120">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ getDate(scope.row.birthDate) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="电话号码"
            width="130">
            <template slot-scope="scope">
              <el-tag
                type="info"
                effect="plain">
                {{ scope.row.phoneNumber }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.row)">编辑
              </el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row.id)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="pageBlock">
          <el-pagination
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            :current-page="currentPageNumber"
            :page-sizes="[8,16,24,40]"
            :page-size="currentPageSize"
            layout="total,sizes, prev, pager, next"
            :total="this.studentsData.totalCount">
          </el-pagination>
        </div>
      </div>
      <Footing class="footing"></Footing>
    </div>
  </div>
</template>

<script>
  import Footing from "../components/Footing";
  import StudentPopover from "../components/StudentDialog";
  import StudentDialog from "../components/StudentDialog";
  import { Loading } from "element-ui";

  export default {
    name: "Student",
    components: {StudentDialog, Footing},
    inject: ['reload'],
    provide() {
      return {
        reload: this.reload,
      }
    },
    methods: {
      // 对话框关闭时的回调函数
      dialogClose: function() {
        // 默认为添加
        this.isAddButton = true;
      },
      // 对话框打开时，判断是添加还是更新
      dialogOpen: function() {
        if (this.isAddButton) {
          return;
        }
        // 通过ref找到子组件对其直接控制
        let dialog = this.$refs.dialog;
        dialog.studentData = dialog.oldStudentData;
        dialog.initTeamLabel = dialog.oldStudentData.teamFullName;
      },
      handleEdit: function(row) {
        this.dialogVisible = true;
        this.isAddButton = false;
        this.oldStudentData = row;
      },
      // 改变班级option
      changeLabel: function (value) {
        this.setTeamId(value);
        this.getStudentPageData(0, this.currentPageSize);
      },
      // 设置url的query参数
      setTeamId: function (newTeamId) {
        let path = this.$router.history.current.path;
        // 这里刷新了一次页面
        this.$router.push({path, query: {teamId: newTeamId}});
        this.currentTeamId = newTeamId;
      },
      // 设置option的Label
      getLabel: function (team) {
        let year = this.getYear(team.schoolYear);
        return `${year}级${team.professional}专业${team.classNumber}`
      },
      getYear: function (timestamp) {
        let date = new Date(timestamp);
        return date.getFullYear();
      },
      // 处理删除按钮
      handleDelete: function (id) {
        this.axios({
          method: 'delete',
          url: '/api/students/' + id,
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.$message({
              type: 'success',
              message: '删除成功',
            });
            // 当前删除的是否是本页的最后一条数据
            if (this.studentsData.content.length === 1 && this.currentPageNumber !== 1) {
              this.currentPageNumber--;
              this.getStudentPageData(this.currentPageNumber - 1, this.currentPageSize);
            } else {
              this.getStudentPageData(this.currentPageNumber - 1, this.currentPageSize);
            }
          } else {
            this.$message({
              message: '删除失败',
              type: 'error',
            });
          }
        })
      },
      // 处理分页改变
      handleCurrentChange: function (val) {
        this.currentPageNumber = val;
        this.getStudentPageData(this.currentPageNumber - 1, this.currentPageSize);
      },
      handleSizeChange: function(val) {
        // 设置新的页面大小后
        this.currentPageSize = val;
        // 回到第一页
        this.handleCurrentChange(1);
      },
      // 根据名称查询
      findLikeName: function () {
        this.handleCurrentChange(1);
      },
      // 封装axios请求
      getStudentPageData: function (number, size) {
        this.axios({
          method: 'get',
          url: '/api/students',
          params: {
            pageNumber: number,
            pageSize: size,
            name: this.findKey,
            teamId: this.currentTeamId,
          }
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.studentsData = ret.data;
          } else {
            this.$message({
              message: '请求数据失败',
              type: 'error',
            });
          }
        });
      },
      // 查询所有班级信息
      getTeamAllData: function () {
        this.axios({
          method: 'get',
          url: "/api/teams/all",
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.teams = ret.data;
            // 如果初始化时url上有teamId
            if (this.currentTeamId != null) {
              let i;
              let len = ret.data.length;
              for (i = 0; i < len; i++) {
                if (ret.data[i].id == this.currentTeamId) {
                  this.initTeamLabel = this.getLabel(ret.data[i]);
                  break;
                }
              }
              if (i == len) {
                this.$message({
                  message: '不存在指定的班级',
                  type: 'error',
                });
              }
            }
          } else {
            this.$message({
              message: '请求数据失败',
              type: 'error',
            });
          }
        })
      },
      getGender: function (gender) {
        if (gender === null) {
          return '';
        }
        if (gender) {
          return '男';
        } else {
          return '女';
        }
      },
      getDate: function (timestamp) {
        let dateTime = new Date(timestamp);
        let year = dateTime.getFullYear();
        let month = (dateTime.getMonth() + 1).toString().padStart(2, '0');
        let day = dateTime.getDate().toString().padStart(2, '0');
        return `${year}-${month}-${day}`
      }
    },
    data() {
      // 刚进入页面时从url上获取teamId的初始值
      let teamId = this.$route.query.teamId === undefined ? null : this.$route.query.teamId;
      return {
        // 姓名查询关键词
        findKey: null,
        // 学生分页数据
        studentsData: {},
        // 当前页码
        currentPageNumber: 1,
        // 当前页面数据数量
        currentPageSize: 8,
        // 所有班级信息用于select
        teams: null,
        // 当前班级id，用于url上刷新
        currentTeamId: teamId,
        // 如果url上有班级id，select上显示的是该班级的label
        initTeamLabel: null,
        // 对话框是否显示
        dialogVisible: false,
        // 是否是添加按钮
        isAddButton: true,
        // 旧学生数据 编辑使用
        oldStudentData: null,
        // 是否显示加载
        loading: false,
      }
    },
    created() {
      this.loading = true;
      this.getStudentPageData(0, this.currentPageSize);
      this.getTeamAllData();
      this.loading = false;
    }
  }
</script>

<style scoped>
  .container {
    width: 100%;
    background-color: rgb(240, 242, 245);
    display: flex;
    /*height: 108vh;*/
    min-height: 88vh;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    padding-top: 3vh;
    padding-bottom: 3vh;
  }

  .titleBar {
    height: 8vh;
    width: 100%;
    background-color: white;
    font-size: 20px;
    font-weight: 600;
    padding-top: 2vh;
    padding-left: 2vw;
  }

  .body {
    width: 96%;
    /*height: 96vh;*/
    background-color: white;
    padding-bottom: 3vh;
  }

  .footing {
    margin-top: 4vh;
  }

  .operationBar {
    width: 100%;
    display: flex;
    justify-content: space-between;
    padding-left: 1vw;
    padding-right: 2vw;
    padding-top: 2vh;
  }

  .selectBar {
    padding-right: 22vw;
  }

  .findBar input {
    padding-left: 8px;
    text-decoration: none;
    outline: none;
    height: 41px;
  }

  .pageBlock {
    padding-top: 2vh;
    padding-right: 2%;
    display: flex;
    justify-content: flex-end;
  }
</style>
