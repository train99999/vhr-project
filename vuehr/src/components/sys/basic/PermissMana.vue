<template>
  <div>
    <div class="permissManaTool">
    <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
      <template slot="prepend">ROLE_</template>
    </el-input>
    <el-input size="small" placeholder="请输入角色中文名" v-model="role.namezh"></el-input>
    <el-button type="primary" size="small" icon="el-icon-plus" @click="doAddRole">添加角色</el-button>
  </div>

  <div class="permissManaMain">
    <el-collapse accordion v-model="activeName" @change="change">
      <!-- 遍历 角色 数组 -->
      <el-collapse-item :title="r.namezh" :name="r.id" v-for="(r,index) in roles" :key="index">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>可访问的资源</span>
            <el-button style="float: right; padding: 3px 0;color:#ff0000;" icon="el-icon-delete" type="text" @click="deleteRole(r)"></el-button>
          </div>
          <div>
            <!-- default-checked-keys 默认选中的东东，是一个数组 
            tree 给该元素取个名字，可以通过该名字查找到该元素
            -->
            <el-tree 
            node-key="id" 
            ref="tree"
            show-checkbox  
            :default-checked-keys="selectedMenus" 
            :key="index" 
            :data="allmenus" 
            :props="defaultProps">
            </el-tree>
            <div style="display:flex;justify-content:flex-end">
              <el-button size="mini" @click="cancelUpdate">取消修改</el-button>
              <el-button size="mini" type="primary" @click="doUpdate(r.id,index)">确认修改</el-button>
            </div>
          </div>
        </el-card>
      </el-collapse-item>
    </el-collapse>
  </div>

  </div>
</template>

<script>
export default {
    name:'PermissMana',
    data(){
      return{
        role:{
          name:'',
          namezh:''
        },
        allmenus:[],
        roles:[],
        activeName:-1,
        selectedMenus:[],
        defaultProps: {
          children: 'children',
          label: 'name'
        }

      }
    },
    mounted(){
      this.initRoles();
    },
    methods:{
      deleteRole(role){
        this.$confirm('此操作将永久删除['+role.namezh+']角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/permiss/role/"+role.id).then(resp=>{
            if(resp){
              this.initRoles();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });        
      },
      doAddRole(){
        if(this.role.name && this.role.namezh){
          this.postRequest("/system/basic/permiss/role",this.role).then(resp=>{
              if(resp){
                this.role.name='';
                this.role.namezh='';
                this.initRoles();
              }
          })
        }else{
          this.$message.error('数据不可以为空');
        }
      },
      cancelUpdate(){
        this.activeName = -1;
      },
      doUpdate(rid,index){
        console.log(rid);
        // this.$refs.tree[index]
        // this.$refs == 获取当前页面的所有引用

        let tree = this.$refs.tree[index];
        var selectedKeys = tree.getCheckedKeys(true);
        let url="/system/basic/permiss/?rid="+rid;
        selectedKeys.forEach(key=>{
          // 多个相同的 mids 在 url 中表示一个数组
          // /system/basic/permiss/?rid=1&mids=6&mids=
          url+="&mids="+key;
        })
        console.log(url)
        this.putRequest(url).then(resp=>{
          if(resp){
            this.activeName = -1;
          }
        })

      },
      // 查询菜单权限，返回值是数组
      initSelectedMenus(rid){
        this.selectedMenus=[];
        this.getRequest("/system/basic/permiss/mids/"+rid).then(resp=>{
          this.selectedMenus = resp;
        })
      },
      change(rid){
        if(rid){
          this.initAllMenus();
          this.initSelectedMenus(rid);
        }
      },
      // 获取所有菜单，返回值是 Menu 集合
      initAllMenus(){
        this.getRequest("/system/basic/permiss/menus").then(resp=>{
          if(resp){
            this.allmenus = resp;
          }
        })
      },
      initRoles(){
        this.getRequest("/system/basic/permiss/").then(resp=>{
          if(resp){
            this.roles = resp;
          }
        })
      }
    }
}
</script>

<style>
/* 给 el-input 标签设置 class */
 .permissManaTool .el-input{
   width: 300px;
   margin-right: 8px;
 }
 .permissManaTool {
   display: flex;
   justify-content: flex-start;
 }
 .permissManaMain{
   margin-top:10px;width:710px
 }
</style>