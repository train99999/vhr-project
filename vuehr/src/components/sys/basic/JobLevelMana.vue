<template>
  <div>
      <div>
        <el-input size="small" v-model="jl.name" prefix-icon="el-icon-plus" style="width:300px" placeholder="添加职称..."></el-input>
          <el-select v-model="jl.titlelevel" placeholder="职称等级" size="small" style="margin-left:5px;margin-right: 5px">
            <el-option
              v-for="item in titlelevels"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
        </el-select>
        <el-button icon="el-icon-plus" type="primary" size="small" @click="addJobLevel">添加</el-button>
      </div>
      <div style="margin-top:10px">
          <el-table
          :data="jls"
          border
          size="small"
          @selection-change="handleSelectionChange"
          style="width: 80%">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>          
          <el-table-column
            prop="id"
            label="编号"
            width="55">
          </el-table-column>
          <el-table-column
            prop="name"
            label="职称名称"
            width="150">
          </el-table-column>
          <el-table-column
            prop="titlelevel"
            label="职称级别">
          </el-table-column>
            <el-table-column
            prop="createdate"
            label="创建时间">
          </el-table-column>
          <el-table-column label="是否启用">
              <template slot-scope="scope">
                  <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
                  <el-tag type="danger" v-else>未启用</el-tag>
              </template>            
          </el-table-column>
          <el-table-column label="操作">
              <template slot-scope="scope">
                  <el-button size="mini" @click="showEditView(scope.row)">编辑</el-button>
                  <!-- scope.row 把当前行得记录数据给我 -->
                  <el-button size="mini" type="danger" @click="deleteHandler(scope.row)">删除</el-button>
              </template>
          </el-table-column>
        </el-table>
        <el-button type="danger" size="small" style="margin-top:10px" :disabled="multipleSelection.length==0" @click="deleteMany">批量删除</el-button>
      </div>

      <el-dialog
        title="修改职称"
        :visible.sync="dialogVisible"
        width="30%">
        <div>
          <table>
            <tr>
              <td><el-tag>职称名</el-tag></td>
              <td><el-input size="small" v-model="updateJl.name"></el-input></td>
            </tr>
            <tr>
              <td><el-tag>职称级别</el-tag></td>
              <td>
              <el-select v-model="updateJl.titlelevel" placeholder="职称等级" size="small" style="margin-left:5px;margin-right: 5px">
                <el-option
                  v-for="item in titlelevels"
                  :key="item"
                  :label="item"
                  :value="item">
                </el-option>
            </el-select>                
              </td>
            </tr>
            <tr>
              <td><el-tag>是否启用</el-tag></td>
              <td><el-switch v-model="updateJl.enabled" active-text="启用" inactive-text="禁用"></el-switch></td>
            </tr>            
          </table>
        </div>

        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="doUpdate" size="small">确 定</el-button>
        </span>
      </el-dialog>
      
  </div>
</template>

<script>
export default {
    name:"JobLevelMana",
    data(){
      return{
        multipleSelection:[],
        updateJl:{
          name:'',
          titlelevel:'',
          enabled:false
        },
        dialogVisible:false,
        jl:{
          name:'',
          titlelevel:''
        },
        titlelevels:[
          '正高级',
          '副高级',
          '中级',
          '初级',
          '员级'
        ],
        jls:[]
      }
    },
    mounted(){
      this.initJls();
    },
    methods:{
      deleteMany(){
         this.$confirm('此操作将永久删除['+this.multipleSelection.length+']条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = '?';
          this.multipleSelection.forEach(item=>{
            ids+='ids='+item.id+'&';
          })
          this.deleteRequest("/system/basic/joblevel/"+ids).then(resp=>{
            if(resp){
              this.initJls();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });       
      },
      handleSelectionChange(val){
        console.log(val)
        this.multipleSelection = val;
      },
      doUpdate(){
        this.putRequest("/system/basic/joblevel/",this.updateJl).then(resp=>{
          if(resp){
            this.initJls();
            this.dialogVisible=false;
          }
        })
      },
      showEditView(data){
        this.dialogVisible=true;
        // 赋值
        this.updateJl=data;
      },
      deleteHandler(data){
        this.$confirm('此操作将删除['+data.name+']职称, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/joblevel/"+data.id).then(resp=>{
            if(resp){
              this.initJls();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });          
      },
      addJobLevel(){

        if(this.jl.name && this.jl.titlelevel){
          this.postRequest("/system/basic/joblevel/",this.jl).then(resp=>{
            if(resp){
              this.jl.name = '';
              this.jl.titlelevel = '';
              this.initJls();
            }
          })
        }else{
          this.$message.error("添加字段不能为空");
          
        }
        
      },
      initJls(){
        this.getRequest("/system/basic/joblevel/").then(resp=>{
          if(resp){
            this.jls=resp;
          }
        })
      }
    }
}
</script>

<style>

</style>