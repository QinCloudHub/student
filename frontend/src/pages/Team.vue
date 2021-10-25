<template>
  <div id="team">
    <div class="titleBar"><span>班级管理</span></div>
    <div class="container">
      <div class="body">
        <div class="operationBar">
          <el-button type="primary" @click="dialogVisible=true">添加班级</el-button>
          <el-dialog
            title="班级信息"
            :visible.sync="dialogVisible"
            :destroy-on-close="true"
            @opened="dialogOpen"
            @close="dialogClose"
            width="40%">
            <TeamDialog
              ref="dialog"
              :isCreate="this.isAddButton"
              :oldTeamData="{ ...this.oldTeamData }"
            ></TeamDialog>
          </el-dialog>
          <div class="findBar">
            <input v-model="findKey" type="text" placeholder="根据专业查询" @keyup.enter="findLikeProfessional"></input>
            <el-button type="primary" @click="findLikeProfessional"><i class="el-icon-search"></i></el-button>
            <el-button type="primary" @click="()=>{this.findKey=null;findLikeProfessional();}"><i
              class="el-icon-refresh-right"></i>重置
            </el-button>
          </div>
        </div>
        <!--表格-->
        <el-table
          v-loading.lock="loading"
          :data="teamsData.content"
          @row-dblclick="rowHandler"
          border
          style="width: 96%; margin-left: 1vw; margin-top: 5vh;">
          <el-table-column
            label=" #"
            width="150">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="年级"
            width="160">
            <!--通过 Scoped slot 可以获取到 row, column, $index 和 store（table 内部的状态管理）的数据-->
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ getYear(scope.row.schoolYear) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="专业"
            width="200">
            <template slot-scope="scope">
              <el-tag
                type="info"
                effect="plain">
                {{ scope.row.professional }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="班级"
            width="220">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>{{ getYear(scope.row.schoolYear) }}级 {{ scope.row.professional }}专业 {{scope.row.classNumber}}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag
                    effect="light">
                    {{ scope.row.classNumber }}
                  </el-tag>
                </div>
              </el-popover>
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
            :total="this.teamsData.totalCount">
          </el-pagination>
        </div>
      </div>
      <Footing class="footing"></Footing>
    </div>
  </div>
</template>

<script>
  import Footing from "../components/Footing";
  import TeamDialog from "../components/TeamDialog";

  export default {
    name: "Team",
    components: {TeamDialog, Footing},
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
      handleEdit: function(row) {
        this.dialogVisible = true;
        this.isAddButton = false;
        this.oldTeamData = row;
      },
      dialogOpen: function() {
        if (this.isAddButton) {
          return;
        }
        // 通过ref找到子组件对其直接控制
        let dialog = this.$refs.dialog;
        dialog.teamData = dialog.oldTeamData;
      },
      // 行双击跳转班级的学生处
      rowHandler: function(row) {
        // 直接调用最开始的
        this.$parent.$children[0].setCurNav('学生管理');
        this.$router.push({ path: '/student', query: {
            teamId: row.id
          }});
      },
      handleDelete: function(id) {
        this.axios({
          method: 'delete',
          url: '/api/teams/' + id,
        }).then((data)=>{
          let ret = data.data;
          if (ret.success) {
            this.$message({
              type: 'success',
              message: '删除成功',
            });
            // 当前删除的是否是本页的最后一条数据
            if (this.teamsData.content.length === 1 && this.currentPageNumber !== 1) {
              this.currentPageNumber--;
              this.getTeamsPageDate(this.currentPageNumber - 1, this.currentPageSize);
            } else {
              this.getTeamsPageDate(this.currentPageNumber - 1, this.currentPageSize);
            }
          } else {
            this.$message({
              message: '删除失败',
              type: 'error',
            });
          }
        })
      },
      findLikeProfessional: function() {
        this.handleCurrentChange(1);
      },
      // 当前页码改变
      handleCurrentChange: function(val) {
        this.currentPageNumber = val;
        this.getTeamsPageDate(this.currentPageNumber - 1, this.currentPageSize);
      },
      // 当前页大小改变
      handleSizeChange: function(val) {
        this.currentPageSize = val;
        this.handleCurrentChange(1);
      },
      getYear: function(timestamp) {
        let date = new Date(timestamp);
        return date.getFullYear();
      },
      getTeamsPageDate: function (number, size) {
        this.axios({
          method: 'get',
          url: "/api/teams",
          params: {
            pageNumber: number,
            pageSize: size,
            professional: this.findKey,
          }
        }).then((data)=>{
          let ret = data.data;
          if (ret.success) {
            this.teamsData = ret.data;
          } else {
            this.$message({
              message: '请求数据失败',
              type: 'error',
            });
          }
        })
      }

    },
    data() {
      return {
        findKey: null,
        teamsData: {},
        currentPageNumber: 1,
        currentPageSize: 8,
        // 对话框使用的数据
        dialogVisible: false,
        isAddButton: true,
        oldTeamData: null,
        loading: false,
      }
    },
    created() {
      this.loading = true;
      this.getTeamsPageDate(0, 8);
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
