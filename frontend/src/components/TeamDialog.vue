<template>
  <div id="teamDialog">
    <div class="container">
      <div class="properties">
        <div class="property">
          <span class="propertyTitle">创立年份:</span>
          <el-date-picker
            class="propertyInput"
            format="yyyy 年"
            value-format="timestamp"
            v-model="teamData.schoolYear"
            type="year"
            placeholder="选择科室创立年份">
          </el-date-picker>
        </div>

        <div class="property">
          <span class="propertyTitle">班级:</span>
          <el-input class="propertyInput" v-model="teamData.classNumber" placeholder="请输入科室名"></el-input>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closeClick">取 消</el-button>
          <el-button type="primary" @click="solveClick">确 定</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "TeamDialog",
    props: ['isCreate', 'oldTeamData'],
    inject: ['reload'],
    data() {
      return {
        teamData: {},
        teams: {},
        professionalList: {},
      }
    },
    methods: {
      updateTeam: function() {
        this.axios({
          method: 'put',
          url: '/api/teams/' + this.teamData.id,
          data: this.teamData,
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.$message({
              type: 'success',
              message: '更新成功',
            });
            // 刷新页面
            this.reload();
          } else {
            this.$message({
              message: '更新失败' + ret.data,
              type: 'error',
            });
          }
        })
      },
      createTeam: function() {
        this.axios({
          method: 'post',
          url: '/api/teams',
          data: this.teamData,
        }).then((data) => {
          let ret = data.data;
          if (ret.success) {
            this.$message({
              type: 'success',
              message: '添加成功',
            });
            // 刷新页面
            this.reload();
          } else {
            this.$message({
              message: '添加失败' + ret.data,
              type: 'error',
            });
          }
        })
      },
      // 确定按钮
      solveClick: function() {
        if (this.isCreate) {
          this.createTeam();
        } else {
          this.updateTeam();
        }
      },
      // 取消处理
      closeClick: function() {
        // 关闭对话框
        this.$parent.$parent.dialogVisible = false;
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
            let set = new Set();
            // 去重专业
            this.teams.forEach((item) => {
              set.add(item.professional);
            });
            this.professionalList = set;
          } else {
            this.$message({
              message: '请求数据失败',
              type: 'error',
            });
          }
        })
      },
    },
    created() {
      this.getTeamAllData();
    },
  }
</script>

<style scoped>
  .container {
    display: flex;
    justify-content: center;
  }

  .properties {
    width: 100%;
    display: flex;
    flex-direction: column;
  }

  .property {
    width: 80%;
    display: flex;
    justify-content: space-between;
    padding-bottom: 1vh;
  }

  .propertyTitle {
    font-weight: 600;
    width: 40%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    padding-right: 1vw;
  }

  .propertyTitle:before {
    content: "*";
    color: rgba(241,8,8,0.88);
    padding-right: 2px;
  }

  .propertyInput {
    height: 40px;
    width: 60%;
    display: flex;
    align-items: center;
  }

  .dialog-footer {
    display: flex;
    justify-content: flex-end;
    padding-right: 20%;
    padding-top: 1vh;
  }
</style>
