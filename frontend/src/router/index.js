import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import Student from "../pages/Student";
import Teacher from "../pages/Teacher";
import Team from "../pages/Team";
import Course from "../pages/Course"
import Login from "../pages/Login";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      redirect: '/student',
      children: [
        {
          path:'/student',
          name: 'Student',
          component: Student
        },
        {
          path: '/teacher',
          name: 'Teacher',
          component: Teacher
        },
        {
          path: '/team',
          name: 'Team',
          component: Team
        },
        {
          path: '/course',
          name: 'Course',
          component: Course
        }
      ]
    }, {
      path: '/login',
      name: 'Login',
      component: Login,
    }
  ]
})
