<template>
  <div>
      <el-form v-bind:rules="rules" ref="loginForm" v-bind:model="loginForm" class="loginContainer">
          <h3 class="loginTitle">系统登录</h3>
          <el-form-item prop="username">
              <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入用户名"></el-input>
          </el-form-item>

          <el-form-item prop="password">
              <el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="请输入密码" @keydown.enter.native="submitLogin"></el-input>
          </el-form-item>

          <el-checkbox class="loginRemember" v-model="checkbox"></el-checkbox>
          <el-button type="primary" style="width:100%;" @click="submitLogin">登录</el-button>
      </el-form>
  </div>
</template>

<script>

export default {
    name: "Login",
    //定义规则
    data() {
        return {
            loginForm:{
                username: 'admin',
                password: '123'
            },
            checkbox:true,
            rules:{
                username:[{required:true,message:'请输入用户名',trigger:'blur'}],
                password:[{required:true,message:'请输入密码',trigger:'blur'}]
            }
        }
    },
    methods:{
        submitLogin(){
            this.$refs.loginForm.validate((valid) => {
                if(valid) {
                    this.postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                        if (resp) {
                            //对象转换为 JSON 字符串
                            window.sessionStorage.setItem("user",JSON.stringify(resp.obj));
                            // 获取router 对象.进行页面跳转
                            this.$router.replace('/home');
                        }
                    })
                }else{
                    this.$message.error('用户名或密码不能为空');
                    return false;
                }
            });
         
        }
    }
}
</script>

<style>
.loginContainer{
    border-radius: 15px;
    background-clip: padding-box;
    margin: 180px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6ca;
}
.loginTitle{
    margin: 0px auto 40px auto;
    text-align: center;
    color:#505458;
}
.loginRemember{
    text-align: left;
    margin: 0px 0px 15px 0px;
}
</style>