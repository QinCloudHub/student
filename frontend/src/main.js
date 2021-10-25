// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from "axios";
import VueAxios from "vue-axios";

Vue.use(ElementUI)
Vue.use(VueAxios, axios)
Vue.config.productionTip = false

// axios请求拦截器
axios.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么，例如加入token
  if (sessionStorage.getItem("auth") !== null) {
    config.headers.auth = sessionStorage.getItem("auth");
  }
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// axios响应拦截器
axios.interceptors.response.use(function (response) {
  // 在接收响应做些什么，例如跳转到登录页
  if (response.data.data === "没有登录") {
    response.data.success = true;
    router.push("/login");
  }
  return response;
}, function (error) {
  // 对响应错误做点什么
  return Promise.reject(error);
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
