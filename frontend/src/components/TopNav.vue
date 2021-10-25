<template>
    <div id="topNav">
      <div class="container">
        <el-button class="back-btn" type="warning" size="mini" round @click="clickBack">退出</el-button>
        <i class="el-icon-s-custom"></i>
        <span>Admin</span>
      </div>
    </div>
</template>

<script>
    export default {
        name: "TopNav",
        methods: {
          clickBack: function () {
            sessionStorage.clear();
            this.$router.push("/login");
          }
        }
    }
</script>

<style scoped>
  .container {
    width: 88vw;
    height: 7vh;
    display: flex;
    justify-content: flex-end;
    background-color: #ffffff;
    align-items: center;
    border-bottom: rgb(240,242,245) 2px solid;
  }
  .back-btn {
    margin-right: 2vw;
  }
  i {
    font-size: 30px;
    padding-right: 1vw;
  }
  span {
    padding-right: 2vw;
  }
</style>
