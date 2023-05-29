<template>
  <div id="teacher">
    <div class="titleBar"><span>医生管理</span></div>
    <div class="container">
      <div class="body">
        <div class="operationBar">
          <el-button type="primary" @click="dialogVisible=true">添加医生</el-button>
          <el-dialog
            top="10vh"
            title="医生信息"
            :visible.sync="dialogVisible"
            :destroy-on-close="true"
            @opened="dialogOpen"
            @close="dialogClose"
            width="40%">
            <TeacherDialog
              ref="dialog"
              :isCreate="this.isAddButton"
              :oldTeacherData="{ ...this.oldTeacherData }"
            ></TeacherDialog>
          </el-dialog>
          <div class="findBar">
            <input v-model="findKey" type="text" placeholder="根据姓名查询" @keyup.enter="findLikeName"></input>
            <el-button type="primary" @click="findLikeName"><i class="el-icon-search"></i></el-button>
            <el-button type="primary" @click="()=>{this.findKey=null;findLikeName();}"><i
              class="el-icon-refresh-right"></i>重置
            </el-button>
          </div>
        </div>
        <!--表格-->
        <el-table
          v-loading.lock="loading"
          :data="teachersData.content"
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
            label="医生号"
            width="100">
            <!--通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据-->
            <template slot-scope="scope">
              <i class="el-icon-info"></i>
              <span style="margin-left: 10px">{{ scope.row.teacherNumber }}</span>
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
            label="民族"
            width="90">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{scope.row.national}}</span>
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
            label="籍贯"
            show-overflow-tooltip
            width="300">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.nativePlace }}</span>
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
            :total="this.teachersData.totalCount">
          </el-pagination>
        </div>
      </div>
      <Footing class="footing"></Footing>
    </div>
  </div>
</template>

<script>
  import Footing from "../components/Footing";
  import TeacherDialog from "../components/TeacherDialog";

  export default {
    name: "Teacher",
    components: {TeacherDialog, Footing},
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
      dialogOpen: function () {
        if (this.isAddButton) {
          return;
        }
        // 通过ref找到子组件对其直接控制
        let dialog = this.$refs.dialog;
        dialog.teacherData = dialog.oldTeacherData;
      },
      handleEdit: function (row) {
        this.dialogVisible = true;
        this.isAddButton = false;
        this.oldTeacherData = row;
      },
      // 处理删除按钮
      handleDelete: function (id) {
        this.axios({
          method: 'delete',
          url: '/api/teachers/' + id,
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.$message({
              type: 'success',
              message: '删除成功',
            });
            // 当前删除的是否是本页的最后一条数据
            if (this.teachersData.content.length === 1 && this.currentPageNumber !== 1) {
              this.currentPageNumber--;
              this.getTeacherPageData(this.currentPageNumber - 1, this.currentPageSize);
            } else {
              this.getTeacherPageData(this.currentPageNumber - 1, this.currentPageSize);
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
        this.getTeacherPageData(this.currentPageNumber - 1, this.currentPageSize);
      },
      handleSizeChange: function (val) {
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
      getTeacherPageData: function (number, size) {
        this.axios({
          method: 'get',
          url: '/api/teachers',
          params: {
            pageNumber: number,
            pageSize: size,
            name: this.findKey,
          }
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.teachersData = ret.data;
          } else {
            this.$message({
              message: '请求数据失败',
              type: 'error',
            });
          }
        });
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
      return {
        // 姓名查询关键词
        findKey: null,
        // 学生分页数据
        teachersData: {},
        // 当前页码
        currentPageNumber: 1,
        // 当前页面数据数量
        currentPageSize: 8,
        // 对话框是否显示
        dialogVisible: false,
        // 是否是添加按钮
        isAddButton: true,
        // 旧教师数据 编辑使用
        oldTeacherData: null,
        // 是否显示加载
        loading: false,
      }
    },
    created() {
      this.loading = true;
      this.getTeacherPageData(0, this.currentPageSize);
      this.loading = false;
    }
  }
</script>

<style scoped>

  .titleBar {
    height: 8vh;
    width: 100%;
    background-color: white;
    font-size: 20px;
    font-weight: 600;
    padding-top: 2vh;
    padding-left: 2vw;
  }

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
