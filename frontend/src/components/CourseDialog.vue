<template>
  <div id="courseDialog">
    <div class="container">
      <div class="properties">
        <div class="property">
          <span class="propertyTitle">课程开始于:</span>
          <el-date-picker
            class="propertyInput"
            format="yyyy[年]MM月"
            value-format="timestamp"
            v-model="courseData.startAt"
            type="month"
            placeholder="选择开始时间">
          </el-date-picker>
        </div>
        <div class="property">
          <span class="propertyTitle">名称:</span>
          <el-input class="propertyInput" v-model="courseData.name" placeholder="请输入名称"></el-input>
        </div>
        <div class="property">
          <span class="propertyTitle">描述:</span>
          <el-input
            class="propertyInput"
            style="height: auto;"
            type="textarea"
            placeholder="请输入描述"
            v-model="courseData.description"
            maxlength="30"
            show-word-limit
          >
          </el-input>
        </div>
        <div class="property">
          <span class="propertyTitle">教师:</span>
          <el-select class="propertyInput"
                     @change="teacherLabelChange"
                     v-model="this.initTeacherLabel" clearable filterable placeholder="选择教师">
            <el-option
              v-for="item in teachers"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </div>
        <div class="property">
          <span class="propertyTitle">班级:</span>
          <el-select class="propertyInput"
                     @change="teamLabelChange"
                     v-model="this.initTeamLabel" clearable filterable placeholder="选择班级">
            <el-option
              v-for="item in teams"
              :key="item.id"
              :label="getLabel(item)"
              :value="item.id">
            </el-option>
          </el-select>
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
    name: "CourseDialog",
    props: ['teams', 'teachers', 'isCreate', 'oldCourseData', 'initTeamLabel', 'initTeacherLabel'],
    inject: ['reload'],
    methods: {
      updateCourse: function() {
        this.axios({
          method: 'put',
          url: '/api/courses/' + this.courseData.id,
          data: this.courseData,
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
      createCourse: function() {
        this.axios({
          method: 'post',
          url: '/api/courses',
          data: this.courseData,
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
      // 班级下拉框改变时
      teamLabelChange: function(value) {
        this.initTeamLabel = value;
        this.courseData.team.id = value;
      },
      // 班级下拉框改变时
      teacherLabelChange: function(value) {
        this.initTeacherLabel = value;
        this.courseData.teacher.id = value;
      },
      // 确定按钮
      solveClick: function() {
        this.courseData.teamId = this.courseData.team.id;
        this.courseData.teacherId = this.courseData.teacher.id;
        if (this.isCreate) {
          this.createCourse();
        } else {
          this.updateCourse();
        }
      },
      // 取消处理
      closeClick: function() {
        // 关闭对话框
        this.$parent.$parent.dialogVisible = false;
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
      setStudentData: function (data) {
        this.courseData = data;
      }
    },
    data() {
      return {
        courseData: {
          team: {},
          teacher: {},
        },
      }
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
