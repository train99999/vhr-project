<template>
  <div>
    <!-- 修改了文件 -->
    <el-container>
      <el-header class="homeHeader">
        <div class="title">微人事</div>
          <el-dropdown class="userInfo" @command="commandHandler">
            <span class="el-dropdown-link">
              {{user.name}}<i><img :src="user.userface"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
              <el-dropdown-item command="setting">设置</el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu router v-for="(item,index) in routes" :key="index">
            <!-- // 获取router 数组，动态渲染菜单 -->
            <!-- item.hidden为false，就渲染这个菜单 -->
            <el-submenu index="1"  v-if="!item.hidden">
              <template slot="title"> 
                <i style="color:#409eff;margin-right:5px" :class="item.iconCls"></i>
                <span>{{item.name}}</span>
              </template>
                <el-menu-item :index="child.path" v-for="(child,index) in item.children" :key="index">{{child.name}}</el-menu-item>
            </el-submenu>
          </el-menu> 
        </el-aside>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
          </el-breadcrumb>
          <div class="homeWelcome" v-if="this.$router.currentRoute.path =='/home'">
              欢迎来到微人事
          </div>          
          <router-view class="homeRouterView"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name:"Home",
  data(){
    return{
      // user 对象，在登录时就已经存储在 sessionStorge
      // 把 user 字符串 转成json 对象
      user:JSON.parse(window.sessionStorage.getItem("user"))
    }
  }, 
  computed:{
    routes(){
      return this.$store.state.routes;
    }
  },
  methods:{
    commandHandler(cmd){
      if(cmd=='logout'){
        this.$confirm('此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.getRequest("/logout");
          window.sessionStorage.removeItem("user");
          this.$store.commit('initRoutes',[]);
          this.$router.replace("/");
          
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          });          
        });        
      }
    }
  }
}
</script>

<style>
.homeWelcome{
  text-align: center;
  font-size: 30px;
  color:#409eff;
  font-family: 华文行楷;
  padding-top: 50px;
}
.homeHeader{
  background-color: #409eff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding:0px 15px;
  box-sizing: border-box;
}
.title{
  font-size:35px;
  color:#ffffff
}
.userInfo{
  cursor:pointer;
}
.el-dropdown-link img{
 height: 48px;
 width: 48px;
 border-radius: 24px;
 margin-left: 8px; 
}
.el-dropdown-link{
  display: flex;
  align-items: center;
}
.homeRouterView{
  margin-top: 10px;
}
</style>