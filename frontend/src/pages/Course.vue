<template>
  <div id="course">
    <div class="titleBar"><span>课程管理</span></div>
    <div class="container">
      <div class="body">
        <div class="operationBar">
          <el-button type="primary" @click="dialogVisible=true">新建课程</el-button>
          <el-dialog
            top="10vh"
            title="课程信息"
            :visible.sync="dialogVisible"
            :destroy-on-close="true"
            @opened="dialogOpen"
            @close="dialogClose"
            width="40%">
            <CourseDialog
              ref="dialog"
              :semesters="this.semesters"
              :teams="{ ...this.teamsData }"
              :teachers="{ ...this.teachersData }"
              :isCreate="this.isAddButton"
              :oldCourseData="{ ...this.oldCourseData }"
            ></CourseDialog>
          </el-dialog>
          <div class="selectBar">
            <el-select
              size="mini"
              @change="changeSemesterLabel"
              @clear="()=>{
                let path = this.$router.history.current.path;
                let query = this.$route.query;
                let newTeamId = query.teamId === undefined ? null : query.teamId;
                let newTeacherId = query.teacherId === undefined ? null : query.teacherId;
                this.$router.push({path, query: {teamId: newTeamId, teacherId: newTeacherId}});
              }"
              v-model="this.currentSemester" clearable placeholder="选择学期">
              <el-option
                v-for="item in semesters"
                :key="item"
                :label="item"
                :value="item">
              </el-option>
            </el-select>
            <el-select
              size="mini"
              @change="changeTeamLabel"
              @clear="()=>{
                let path = this.$router.history.current.path;
                let query = this.$route.query;
                let newSemester = query.semester === undefined ? null : query.semester;
                let newTeacherId = query.teacherId === undefined ? null : query.teacherId;
                this.$router.push({path, query: {semester: newSemester, teacherId: newTeacherId}});
              }"
              v-model="this.initTeamLabel" clearable placeholder="选择班级">
              <el-option
                v-for="item in teamsData"
                :key="item.id"
                :label="getLabel(item)"
                :value="item.id">
              </el-option>
            </el-select>
            <el-select
              size="mini"
              @change="changeTeacherLabel"
              @clear="()=>{
                let path = this.$router.history.current.path;
                let query = this.$route.query;
                let newSemester = query.semester === undefined ? null : query.semester;
                let newTeamId = query.teamId === undefined ? null : query.teamId;
                this.$router.push({path, query: {semester: newSemester, teamId: newTeamId}});
              }"
              v-model="this.initTeacherLabel" clearable placeholder="选择教师">
              <el-option
                v-for="item in teachersData"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </div>
          <div class="findBar">
            <input v-model="findKey" type="text" placeholder="根据课程名查询" @keyup.enter="findLikeName"></input>
            <el-button type="primary" @click="findLikeName"><i class="el-icon-search"></i></el-button>
            <el-button type="primary" @click="()=>{this.findKey=null;findLikeName();}"><i
              class="el-icon-refresh-right"></i>重置
            </el-button>
          </div>
        </div>
        <!--表格-->
        <el-table
          v-loading.lock="loading"
          :data="coursesData.content"
          @cell-dblclick="semesterDoubleClick"
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
            label="学期"
            width="150">
            <!--通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据-->
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.semester }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="课程名"
            width="150">
            <template slot-scope="scope">
              <span style="margin-left: 10px;font-weight: 600;">{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="描述"
            show-overflow-tooltip
            width="200">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                {{ scope.row.description }}
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="教师"
            width="100">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ scope.row.teacher.name }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="班级"
            width="200">
            <template slot-scope="scope">
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">{{ getLabel(scope.row.team) }}</el-tag>
              </div>
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
            :total="this.coursesData.totalCount">
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
  import CourseDialog from "../components/CourseDialog";

  export default {
    name: "Course",
    components: {CourseDialog, StudentDialog, Footing},
    inject: ['reload'],
    provide() {
      return {
        reload: this.reload,
      }
    },
    methods: {
      // 双击学期单元格
      semesterDoubleClick: function(row, column) {
        switch (column.label) {
          case "学期":
            this.changeSemesterLabel(row.semester);
            break;
          case "教师":
            this.changeTeacherLabel(row.teacher.id);
            break;
          case "班级":
            this.changeTeamLabel(row.team.id);
            break;
          default:
            break;
        }
      },
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
        dialog.courseData = dialog.oldCourseData;
        dialog.initTeamLabel = this.getLabel(dialog.oldCourseData.team);
        dialog.initTeacherLabel = this.oldCourseData.teacher.name;
      },
      handleEdit: function(row) {
        this.dialogVisible = true;
        this.isAddButton = false;
        this.oldCourseData = row;
      },
      // 改变教师option
      changeTeacherLabel: function (value) {
        this.setTeacherId(value);
        this.initTeacherLabel = value;
        this.getCoursePageData(0, this.currentPageSize);
      },
      // 改变班级option
      changeTeamLabel: function (value) {
        this.setTeamId(value);
        this.initTeamLabel = value;
        this.getCoursePageData(0, this.currentPageSize);
      },
      // 改变学期option
      changeSemesterLabel: function (value) {
        this.setSemester(value);
        this.getCoursePageData(0, this.currentPageSize);
      },
      setTeacherId: function(newTeacherId) {
        let path = this.$router.history.current.path;
        // 这里刷新了url没有刷新页面
        this.$router.push({path, query: { ...this.getQueryParam(),teacherId: newTeacherId }});
        this.currentTeacherId = newTeacherId;
      },
      setTeamId: function (newTeamId) {
        let path = this.$router.history.current.path;
        // 这里刷新了url没有刷新页面
        this.$router.push({path, query: { ...this.getQueryParam(),teamId: newTeamId }});
        this.currentTeamId = newTeamId;
      },
      // 设置url的query参数
      setSemester: function (newSemester) {
        let path = this.$router.history.current.path;
        // 这里刷新了url没有刷新页面
        this.$router.push({path, query: { ...this.getQueryParam(), semester: newSemester }});
        this.currentSemester = newSemester;
      },
      getQueryParam: function() {
        let query = this.$route.query;
        let newSemester = query.semester === undefined ? null : query.semester;
        let newTeamId = query.teamId === undefined ? null : query.teamId;
        let newTeacherId = query.teacherId === undefined ? null : query.teacherId;
        return {
          semester: newSemester,
          teamId: newTeamId,
          teacherId: newTeacherId,
        }
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
          url: '/api/courses/' + id,
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.$message({
              type: 'success',
              message: '删除成功',
            });
            // 当前删除的是否是本页的最后一条数据
            if (this.coursesData.content.length === 1 && this.currentPageNumber !== 1) {
              this.currentPageNumber--;
              this.getCoursePageData(this.currentPageNumber - 1, this.currentPageSize);
            } else {
              this.getCoursePageData(this.currentPageNumber - 1, this.currentPageSize);
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
        this.getCoursePageData(this.currentPageNumber - 1, this.currentPageSize);
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
      getCoursePageData: function (number, size) {
        this.axios({
          method: 'get',
          url: '/api/courses',
          params: {
            pageNumber: number,
            pageSize: size,
            name: this.findKey,
            semester: this.currentSemester,
            teamId: this.currentTeamId,
            teacherId: this.currentTeacherId,
          }
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.coursesData = ret.data;
          } else {
            this.$message({
              message: '请求数据失败',
              type: 'error',
            });
          }
        });
      },
      // 查询所有学期信息
      getSemestersData: function () {
        this.axios({
          method: 'get',
          url: "/api/courses/semesters",
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.semesters = ret.data;
          } else {
            this.$message({
              message: '请求数据失败',
              type: 'error',
            });
          }
        })
      },
      // 查询所有班级信息
      getTeamAllData: function () {
        this.axios({
          method: 'get',
          url: "/api/teams/all",
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.teamsData = ret.data;
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
      // 查询所有班级信息
      getTeacherAllData: function () {
        this.axios({
          method: 'get',
          url: "/api/teachers/all",
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.teachersData = ret.data;
            // 如果初始化时url上有teamId
            if (this.currentTeacherId != null) {
              let i;
              let len = ret.data.length;
              for (i = 0; i < len; i++) {
                if (ret.data[i].id == this.currentTeacherId) {
                  this.initTeacherLabel = ret.data[i].name;
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
      let semester = this.$route.query.semester === undefined ? null : this.$route.query.semester;
      let teamId = this.$route.query.teamId === undefined ? null : this.$route.query.teamId;
      let teacherId = this.$route.query.teacherId === undefined ? null : this.$route.query.teacherId;
      return {
        // 姓名查询关键词
        findKey: null,
        // 学生分页数据
        coursesData: {},
        // 所有班级信息
        teamsData: {},
        // 所有老师信息
        teachersData: {},
        // 当前页码
        currentPageNumber: 1,
        // 当前页面数据数量
        currentPageSize: 8,
        // 所有班级信息用于select
        semesters: null,
        // 当前学期，用于url上刷新时
        currentSemester: semester,
        // 当前班级号， url上
        currentTeamId: teamId,
        // 如果url上有，在刚进入的时候显示
        initTeamLabel: null,
        // 当前教师号 url上
        currentTeacherId: teacherId,
        // 如果url上有teacherId，则在刚进入的时候显示
        initTeacherLabel: null,
        // 对话框是否显示
        dialogVisible: false,
        // 是否是添加按钮
        isAddButton: true,
        // 旧课程数据 编辑使用
        oldCourseData: null,
        // 是否显示加载
        loading: false,
      }
    },
    created() {
      this.loading = true;
      // 获取课程分页信息
      this.getCoursePageData(0, this.currentPageSize);
      // 获取所有课程的学期列表
      this.getSemestersData();
      // 获取所有班级信息
      this.getTeamAllData();
      // 获取所有老师信息
      this.getTeacherAllData();
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
    padding-right: 8vw;
    display: flex;
    align-items: center;
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
