<template>
  <div>
    <div style="margin-top: 10px;display:flex;justify-content:center">
      <el-input v-model="keywords" placeholder="通过用户名搜索用户..." prefix-icon="el-icon-search"
      style="width:400px;margin-right:10px" ></el-input>
      <el-button icon="el-icon-search" type="primary" @click="doSearch">搜索</el-button>
    </div>
    <div class="hr-container">
      <el-card class="box-card" v-for="(hr,index) in hrs" :key="index">
        <div slot="header" class="clearfix">
          <span>{{hr.name}}</span>
          <el-button style="float: right; padding: 3px 0;color:#e30007" type="text" icon="el-icon-delete" @click="deleteHr(hr)"></el-button>
        </div>
        <div class="img-container">
          <div><img :src="hr.userface" :alt="hr.name" :title="hr.name" class="userface-img"></div>
        </div>
        <div class="userinfo-container">
          <div>用户名：{{hr.name}}</div>
          <div>手机号码：{{hr.phone}}</div>
          <div>电话号码：{{hr.telephone}}</div>
          <div>地址：{{hr.address}}</div>
          <div>用户状态：
              <el-switch
              @change="enabledChange(hr)"
              v-model="hr.enabled"
                active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="启用"
              inactive-text="禁用">
            </el-switch>
          </div>
          <div>
            用户角色：<el-tag type="success" style="margin-right:6px" v-for="(role,indexj) in hr.roles" :key="indexj">{{role.namezh}}</el-tag>
              <el-popover
              @show="showPop(hr)"
              @hide="hidePop(hr)"
            placement="right"
            title="角色列表"
            width="200"
            trigger="click"
            >
          <el-select v-model="selectedRoles" multiple placeholder="请选择">
            <el-option
              v-for="(r,indexk) in allroles"
              :key="indexk"
              :label="r.namezh"
              :value="r.id">
            </el-option>
          </el-select>            
            <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
          </el-popover>
          </div>
          <div>备注：{{hr.remark}}</div>
        </div>
      </el-card>

    </div>
  </div>
</template>

<script>
export default {
    name:"SysHr",
    data(){
      return{
        keywords:'',
        hrs:[],
        allroles:[],
        selectedRoles:[]
      }
    },
    mounted(){
      this.initHrs();
    },
    methods:{
      deleteHr(hr){
        this.$confirm('此操作将永久删除['+hr.name+']用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.deleteRequest("/system/hr/"+hr.id).then(resp=>{
              if(resp){
                this.initHrs();
              }
            })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });        
      },
      doSearch(){
        this.initHrs();
      },
      hidePop(hr){
        let url = '/system/hr/role?hrid='+hr.id;
        this.selectedRoles.forEach(sr=>{
          url+='&rids='+sr;
        })
        console.log(url);
        this.putRequest(url).then(resp=>{
            if(resp){
              this.initHrs();
            }
        })
      },
      showPop(hr){
        this.initAllRoles();
        let roles=hr.roles;
        this.selectedRoles=[];
        roles.forEach(r=>{
          this.selectedRoles.push(r.id);
        })
      },
      initAllRoles(){
        this.getRequest("/system/hr/roles").then(resp=>{
          if(resp){
            this.allroles=resp;
          }
        })
      },
      enabledChange(hr){
        this.putRequest("/system/hr/",hr).then(resp=>{
          if(resp){
            this.initHrs();
          }
        })
      },
      // 查询所有Hr
      initHrs(){
        this.getRequest("/system/hr/?keywords="+this.keywords).then(resp=>{
          if(resp){
            this.hrs=resp;
          }
        })
      }
    }
}
</script>

<style>
.img-container{
  width: 100%;
  display: flex;
  justify-content: center;
}
.hr-container {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
.box-card{
  width:350px;
  margin-bottom: 20px;
}
.userface-img{
  width:72px;
  height: 72px;
  border-radius: 72px;

}
.userinfo-container{
  margin-top: 20px;
}
.userinfo-container div{
  font-size:12px;
  color:#409eff;
}
</style>