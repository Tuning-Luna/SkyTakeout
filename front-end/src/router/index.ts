import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

import Login from '../pages/Login.vue'
import Layout from '../pages/layout/index.vue'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/',
    name: 'Home',
    component: Layout,
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
