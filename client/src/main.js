// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import Http from './utils/Http'

import API from './utils/API';

Vue.prototype.$ajax = Http;
Vue.prototype.$apilist = API;

Vue.use(ElementUI)

Vue.config.productionTip = false

/* eslint-disable no-new */
let point = new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})



