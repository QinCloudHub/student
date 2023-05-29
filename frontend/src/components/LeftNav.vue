<template>
    <div id="leftNav">
      <div class="container">
        <div class="logo">
          <router-link to="#" tag="div"><img class="logoImg" src="../assets/logo.png" alt="导航主页 - logo"/></router-link>
        </div>
        <button  v-for="item in navData"
                     :to="{name: item.toUrl}"
                     :class="curNav===item.value?'active':''"
                     @click="navClick(item.value, item.toUrl)">
          {{item.value}}
        </button>
      </div>
    </div>
</template>

<script>
    export default {
      name: "LeftNav",
      inject: ['reload'],
      methods: {
        // 点击导航栏后，先转化点击状态
        navClick: function (curNav, toUrl) {
          this.reload();
          this.curNav = curNav;
          this.$router.push(toUrl);
        },
        setCurNav: function (newNav) {
          this.curNav = newNav;
        }
      },
      data() {
        let navMap = {
          '/student': "病人管理",
          '/team': "",
          '/teacher': "医生管理",
          '/course': "专业管理",
        }
        return {
          navData: [
            {
              value: '病人管理',
              toUrl: '/student',
            },
            {
              value: '科室管理',
              toUrl: '/team'
            },
            {
              value: '医生管理',
              toUrl: '/teacher'
            }
          ],
          curNav: navMap[this.$route.path],
        }
      },
      created() {
      }
    }
</script>

<style scoped>
  .container {
    display: flex;
    flex-direction: column;
    height: 100vh;
    width: 12vw;
    background-color: rgb(0, 21, 41);
  }
  .active {
    color: rgb(255, 255, 255);
    background-color: rgb(24, 144, 255);
  }
  button {
    width: 100%;
    height: 6vh;
    color: rgb(166, 161, 135);
    background-color: rgb(0, 21, 41);
    font-size: 17px;
    display: flex;
    justify-content: center;
    align-items: center;
    outline: none;
    border: 0;
    border-bottom: 1vh rgb(0, 21, 41) solid;
    padding-right: 1vw;
  }
  .logoImg {
    height: 14vh;
  }
  .logo {
    height: 20vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>
