<template>
  <div>
      <div>
        <el-input
            size="small"
            class="addPosInput"
            placeholder="添加职位..."
            prefix-icon="el-icon-plus"
            @keydown.enter.native="addPosition"
            v-model="pos.name">
        </el-input>
        <el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition">添加</el-button>
      </div>
      <div class="posManaMain">
        <el-table
            :data="positions"
            border
            @selection-change="handleSelectionChange"
            stripe
            size="small"
            style="width: 70%">
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
            label="职位名称"
            width="180">
            </el-table-column>
            <el-table-column
            prop="createdate"
            width="150"
            label="创建时间">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-button @click="deleteMany" type="danger" size="small" style="margin-top:8px" :disabled="multipSeletection.length==0">批量删除</el-button>
      </div>
    <el-dialog
        title="修改职位名称"
        :visible.sync="dialogVisible"
        width="30%"
        >
        <div>
            <el-tag>职位名称</el-tag>
            <el-input class="updatePoInput" size="small" v-model="updatePos.name" @keydown.enter.native="doUpdate"></el-input>
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
    name:"PosMana",
    data(){
        return {
            pos:{
                name:''
            },
            updatePos:{
                name:''
            },
            dialogVisible:false,
            multipSeletection:[],
            positions: []
        }
    },
    mounted(){
        this.initPositions();
    },
    methods:{
        deleteMany(){
                this.$confirm('确认删除'+this.multipSeletection.length+'条职位吗', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                    let ids = "?";
                    this.multipSeletection.forEach(item=>{
                        ids += 'ids='+item.id+'&';
                    })
                    console.log(ids);
                    this.deleteRequest("/system/basic/pos/"+ids).then(resp=>{
                        if(resp){
                            this.initPositions();
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
            this.multipSeletection=val;
            console.log(val);
            console.log(this.multipSeletection.length)
        },
        doUpdate(){
            this.putRequest("/system/basic/pos/",this.updatePos).then(resp=>{
                if(resp){
                    
                    this.dialogVisible=false;
                    this.initPositions();
                    this.updatePos.name='';
                    
                }
            })
        },
        addPosition(){
            if(this.pos.name){
                this.postRequest("/system/basic/pos/",this.pos).then(resp=>{
                    if(resp){
                        this.initPositions();
                        this.pos.name=null;
                    }
                })
            }else{
                this.$message.error("职位名称不能为空");
            }
        },
        // index 表示第几行,data 表示数据
        handleEdit(index,data){
            // 对象拷贝
            Object.assign(this.updatePos,data);
            this.dialogVisible=true;
        },
        handleDelete(index,data){
        this.$confirm('确认删除['+data.name+']职位吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/pos/"+data.id).then(resp=>{
                        if(resp){
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
                });
        },
        initPositions(){
            this.getRequest("/system/basic/pos/").then(resp=>{
                if(resp){
                    this.positions = resp;
                }
            })
        }
    }
}
</script>

<style>
.updatePoInput{
    width: 200px;
    margin-left: 8px;
}
.addPosInput{
    width:300px;margin-right: 8px;
}
.posManaMain{
    margin-top: 14px;
}
</style>