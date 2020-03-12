import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css';

import {postKeyValueRequest} from "./utils/api";
import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {getRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
import {initMenu} from './utils/menus';
Vue.prototype.postKeyValueRequest=postKeyValueRequest;
Vue.prototype.postRequest=postRequest;
Vue.prototype.putRequest=putRequest;
Vue.prototype.getRequest=getRequest;
Vue.prototype.deleteRequest=deleteRequest;

Vue.config.productionTip = false
Vue.use(ElementUI);

// 监听页面跳转；加载菜单
router.beforeEach((to,from,next)=>{
  // 如果去 / 不加载菜单
  if(to.path == '/'){
    next();
  } else{
    initMenu(router,store);
    next();
  }
})

new Vue({
  router,
  store,
   // 项目一打开main.js 要渲染的是 #app
  render: h => h(App)
}).$mount('#app')
  