<template>
  <div id="studentDialog">
    <div class="container">
      <div class="properties">
        <div class="property">
          <span class="propertyTitle">学号:</span>
          <el-input class="propertyInput" v-model="studentData.studentNumber" placeholder="请输入学号"></el-input>
        </div>
        <div class="property">
          <span class="propertyTitle">班级:</span>
          <el-select class="propertyInput"
                     @change="labelChange"
                     v-model="this.initTeamLabel" clearable placeholder="选择班级">
            <el-option
              v-for="item in teams"
              :key="item.id"
              :label="getLabel(item)"
              :value="item.id">
            </el-option>
          </el-select>
        </div>
        <div class="property">
          <span class="propertyTitle">姓名:</span>
          <el-input class="propertyInput" v-model="studentData.name" placeholder="请输入姓名"></el-input>
        </div>
        <div class="property">
          <span class="propertyTitle">性别:</span>
          <div class="propertyInput">
            <el-radio-group v-model="studentData.gender">
              <el-radio :label="true">男</el-radio>
              <el-radio :label="false">女</el-radio>
            </el-radio-group>
          </div>
        </div>
        <div class="property">
          <span class="propertyTitle">民族:</span>
          <el-select class="propertyInput" v-model="studentData.national" clearable placeholder="选择民族">
            <el-option
              v-for="item in nationals"
              :key="item"
              :value="item">
            </el-option>
          </el-select>
        </div>
        <div class="property">
          <span class="propertyTitle">出生日期:</span>
          <el-date-picker
            class="propertyInput"
            value-format="timestamp"
            v-model="studentData.birthDate"
            type="date"
            placeholder="选择出生日期">
          </el-date-picker>
        </div>
        <div class="property">
          <span class="propertyTitle">籍贯:</span>
          <el-input class="propertyInput" v-model="studentData.nativePlace" placeholder="请输入籍贯"></el-input>
        </div>
        <div class="property">
          <span class="propertyTitle">电话号码:</span>
          <el-input class="propertyInput"
                    maxlength="11"
                    show-word-limit
                    suffix-icon="el-icon-phone"
                    v-model="studentData.phoneNumber"
                    placeholder="请输入电话号码"></el-input>
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
    name: "StudentDialog",
    props: ['teams', 'isCreate', 'oldStudentData', 'teamId', 'initTeamLabel'],
    inject: ['reload'],
    methods: {
      updateStudent: function() {
        this.axios({
          method: 'put',
          url: '/api/students/' + this.studentData.id,
          data: this.studentData,
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
      createStudent: function() {
        this.axios({
          method: 'post',
          url: '/api/students',
          data: this.studentData,
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
      labelChange: function(value) {
        this.initTeamLabel = value;
        this.studentData.teamId = value;
      },
      // 确定按钮
      solveClick: function() {
        if (this.isCreate) {
          this.createStudent();
        } else {
          this.updateStudent();
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
        this.studentData = data;
      }
    },
    data() {
      return {
        studentData: {
          teamId: this.teamId,
        },
        nationals: [
          "汉族", "壮族", "满族", "回族", "苗族", "维吾尔族", "土家族", "彝族", "蒙古族", "藏族", "布依族", "侗族", "瑶族", "朝鲜族", "白族", "哈尼族",
          "哈萨克族", "黎族", "傣族", "畲族", "傈僳族", "仡佬族", "东乡族", "高山族", "拉祜族", "水族", "佤族", "纳西族", "羌族", "土族", "仫佬族", "锡伯族",
          "柯尔克孜族", "达斡尔族", "景颇族", "毛南族", "撒拉族", "布朗族", "塔吉克族", "阿昌族", "普米族", "鄂温克族", "怒族", "京族", "基诺族", "德昂族", "保安族",
          "俄罗斯族", "裕固族", "乌孜别克族", "门巴族", "鄂伦春族", "独龙族", "塔塔尔族", "赫哲族", "珞巴族"
        ],
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
