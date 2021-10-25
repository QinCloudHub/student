<template>
    <div id="home">
      <LeftNav id="leftNav"></LeftNav>
      <TopNav id="topNav"></TopNav>
      <div class="container">
        <transition name="el-fade-in-linear">
          <router-view v-show="isRouterAlive" v-if="isRouterAlive"></router-view>
        </transition>
      </div>
    </div>
</template>

<script>
    import LeftNav from "../components/LeftNav";
    import TopNav from "../components/TopNav";
    export default {
      name: "Home",
      components: {TopNav, LeftNav},
      // 提供方法到子组件中
      provide() {
        return {
          reload: this.reload,
        }
      },
      data() {
        return {
          isRouterAlive: true
        }
      },
      methods: {
        // 刷新组件
        reload() {
          this.isRouterAlive = false;
          this.$nextTick(function () {
            // 当数据被修改后使用这个方法会回调获取更新后的dom再渲染出来
            this.isRouterAlive = true;
          });
        }
      },
    }
</script>

<style scoped>
  #home {
    display: flex;
  }
  #leftNav {
    position: fixed;
  }
  #topNav {
    position: fixed;
    padding-left: 12vw;
    z-index: 1;
  }
  .container {
    margin-left: 12vw;
    margin-top: 7vh;
    width: 88vw;
  }
</style>
