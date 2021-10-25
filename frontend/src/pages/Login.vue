<template>
  <div id="login">
    <div class="backgroundImg"></div>
    <div class="container">
      <div class="body">
        <div class="title">
          学生信息管理系统
        </div>
        <el-form :model="formData" status-icon :rules="rules" ref="ruleForm" label-width="50px" class="form">
          <el-form-item label="账号" prop="username" class="form-item">
            <el-input v-model="formData.username" placeholder="输入账号"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" class="form-item">
            <el-input type="password" placeholder="输入密码" v-model="formData.password" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item class="login-bar">
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      var validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入账号'));
        } else {
          callback();
        }
      }
      var validatePassword = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          callback();
        }
      };
      return {
        formData: {
          username: '',
          password: '',
        },
        rules: {
          username: [
            {validator: validateUsername, trigger: 'blur'}
          ],
          password: [
            {validator: validatePassword, trigger: 'blur'}
          ],
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.axios({
              method: 'post',
              url: '/api/login',
              data: {
                username: this.formData.username,
                password: this.formData.password,
              },
            }).then((data) => {
              let ret = data.data;
              if (ret.success) {
                this.$message({
                  type: 'success',
                  message: '登录成功',
                });
                sessionStorage.setItem("auth", ret.data);
                this.$router.push("/")
              } else {
                this.$message({
                  message: ret.data,
                  type: 'error',
                });
              }
            })
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>
  #login {
    width: 100vw;
    height: 100vh;
  }
  .backgroundImg {
    position: fixed;
    width: 104vw;
    height: 104vh;
    top: -2vh;
    left: -2vw;
    background-image: url("../assets/login.png");
    background-size: cover;
    z-index: -1;
    /*是否模糊处理*/
    filter: blur(3px);
    overflow: hidden;
  }

  .container {
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .body {
    border-radius: 10px;
    width: 35vw;
    height: 50vh;
    background-color: rgba(239, 239, 239, 0.69);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .title {
    font-size: 30px;
    font-weight: 300;
  }

  .form {
    width: 100%;
    height: 60%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding-top: 10vh;
    padding-right: 2vw;
  }

  .form-item {
    font-weight: 600;
    width: 200px;
  }

</style>
