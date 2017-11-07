import Vue from 'vue'
import Router from 'vue-router'

import login from '@/view/login'

import UserCenter from '@/view/UserCenter'
import MemoryMonitor from '@/view/MemoryMonitor'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path: '/UserCenter',
      name: 'UserCenter',
      component: UserCenter
    },
    {
      path: '/MemoryMonitor',
      name: 'MemoryMonitor',
      component: MemoryMonitor
    }
  ]
})
