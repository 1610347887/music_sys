<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <span>
          <el-button type="primary" size="mini" @click="getDilogTitle()">添加用户</el-button>
          <!--条件查询-->
          <span style="text-align: center">
            <el-input v-model="findObj.username" size="mini" placeholder="用户名" style="width: 200px"></el-input>
            <el-select v-model="findObj.sex" size="mini"  placeholder="性别" style="width: 80px">
              <el-option
                v-for="item in options"
                :value="item.value"
                :key="item.value"
                :label="item.label">
              </el-option>
            </el-select>
            <el-button type="primary" size="mini" icon="el-icon-search" @click="getAllConsumer()">查询</el-button>
          </span>
        </span>
      </div>
    </div>
    <el-table size="mini"
              border style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              :data="consumerList">
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column label="用户头像" width="100">
        <template slot-scope="scope">
          <el-upload :action="uploadUrl(scope.row.id)"
                     :before-upload="beforeAvatarUpload"
                     :on-success="handleAvatarSuccess"
                     :show-file-list="false">
            <div class="consumer-img">
              <img v-if="scope.row. avator != null" :src="getUrl(scope.row.avator)" style="width: 100%" title="点击更换头像">
              <i v-else class="el-icon-plus" style="padding: 20px; font-size: 40px" title="点击上传头像"/>
            </div>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名"/>
      <el-table-column label="性别" prop="sex">
        <template slot-scope="scope">
          {{changeSex(scope.row.sex)}}
        </template>
      </el-table-column>
      <el-table-column prop="phoneNum" label="手机号"/>
      <el-table-column prop="email" label="邮箱"/>
      <el-table-column label="生日" prop="birth"/>
      <el-table-column label="签名" prop="introduction"/>
      <el-table-column label="地区" prop="location"/>
      <el-table-column label="收藏" width="80">
        <template slot-scope="scope">
          <el-button size="mini" @click="getCollect(scope.row.id)">收藏</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" name="修改" @click="getDilogTitle(scope.row.id)"/>
          <el-button type="danger" size="mini" icon="el-icon-delete" name="删除" @click="deleteConsumer(scope.row.id)"/>
        </template>
      </el-table-column>
    </el-table>
    <!--    添加用户-->
    <el-dialog :visible.sync="centerDialogVisible" :title="titleMap[dialogTitle]" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="70px" :rules="rules">
        <el-form-item prop="username" label="用户名" size="mini">
          <el-input v-model="registerForm.username" placeholder="用户名"/>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
          <el-input type="password" v-model="registerForm.password" placeholder="密码"/>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <input type="radio" name="sex" value="1" v-model="registerForm.sex">&nbsp;男&nbsp;&nbsp;
          <input type="radio" name="sex" value="0" v-model="registerForm.sex">&nbsp;女
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input v-model="registerForm.phoneNum" placeholder="手机号"/>
        </el-form-item>
        <el-form-item prop="email" label="邮箱" size="mini">
          <el-input v-model="registerForm.email" placeholder="邮箱"/>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker type="date"
                          v-model="registerForm.birth"
                          value-format="yyyy-MM-dd"
                          format="yyyy-MM-dd"
                          placeholder="选择日期"
                          style="width: 100%"/>
        </el-form-item>
        <el-form-item prop="introduction" label="签名" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="签名"/>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="registerForm.location" placeholder="地区"/>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="saveOrUpdate(registerForm.id)">确定</el-button>
      </span>
    </el-dialog>

    <!--分页-->
    <el-pagination
      :total="total"
      :current-page="current"
      :page-size="limit"
      :page-sizes="[4, 10, 15, 20]"
      background
      style="padding:30px 0; text-align:center;"
      layout="total,sizes, prev, pager, next,jumper"
      @size-change="handleSizeChange"
      @current-change="getAllConsumer"/>
  </div>
</template>

<script>
import {addConsumer, getConsumerInfo, updateConsumerInfo, deleteConsumerById, findConsumer} from '../api/index'
import {mixin} from "../mixins/index";

export default {
  mixins: [mixin],
  data(){
    return{
      centerDialogVisible: false,   // 是否显示dialog弹出
      registerForm:{
        sex: '1'
      },              // 添加用户对象绑定
      total: 0,                     // 总记录数
      current: 1,                   // 当前页
      limit: 4,                     // 每页记录数
      findObj: {},                  // 条件查找对象
      consumerList: [],               // 用户集合
      titleMap: {                   // dialog标题信息
        add: '添加用户',
        update: '修改用户信息'
      },
      dialogTitle: '',
      rules: {
        username:[
          {required:true,message:'请输入用户名',trigger:'blur'}
        ],
        password:[
          {required:true,message:'请输入密码',trigger:'blur'}
        ],
        phoneNum:[
          {required:true,message:'请输入手机号',trigger:'blur'},
          {min:11,max:11,message: '请输入正确的手机号',trigger: ['blur','change']}
        ],
        email:[
          {required:true,message:'请输入邮箱',trigger:'blur'},
          {type: 'email',message: '请输入正确的邮箱地址',trigger: ['blur','change']}
        ],
        location:[
          {required:true,message:'请输入地区',trigger:'blur'}
        ],
      },
      options: [
        {
          value: '0',
          label: '女'
        },
        {
          value: '1',
          label: '男'
        },
        {
          value: '',
          label: ''
        }
      ]
    }
  },
  created() {
    this.getAllConsumer(this.current)
  },
  methods:{
    // 查询所有用户
    getAllConsumer(page = 1){
      this.current = page
      findConsumer(this.current, this.limit, this.findObj)
        .then(res =>{
          this.consumerList = res.data.records
          this.total = res.data.total
        })
    },
    // 判断是添加还是修改
    saveOrUpdate(id){
      if (!id){
        this.saveConsumer()
      }else{
        this.updateConsumer()
      }
    },
    // 添加用户
    saveConsumer(){
      this.$refs['registerForm'].validate(valid =>{
        if (valid){
          addConsumer(this.registerForm)
            .then(res => {
              if (res.code == 200){
                this.$message({
                  type: 'success',
                  message: '添加成功'
                })
                this.centerDialogVisible = false
                this.getAllConsumer(this.current)
              }else {
                this.$message({
                  type: 'error',
                  message: '添加失败'
                })
              }
            })
        }
      })
    },
    // 修改用户信息
    updateConsumer(){
      this.$refs['registerForm'].validate(valid =>{
        if (valid){
          updateConsumerInfo(this.registerForm)
            .then(res => {
              this.$message({
                type: "success",
                message: "修改成功"
              })
              this.centerDialogVisible = false
              this.getAllConsumer(this.current)
            })
        }
      })
    },
    // 通过id获取用户信息
    getConsumerInfoById(id){
      getConsumerInfo(id)
        .then(res => {
          this.registerForm = res.data
        })
    },
    // 删除用户
    deleteConsumer(id){
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        deleteConsumerById(id)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.getAllConsumer(this.current)
            }else {
              this.$message({
                type: "error",
                message: "删除失败"
              })
            }
          })
      })
    },
    // 获取dialog标题
    getDilogTitle(id){
      this.registerForm = {}
      this.registerForm.sex = '1'
      this.centerDialogVisible = true
      if (!id){
        this.dialogTitle = 'add'
      }else {
        this.dialogTitle = 'update'
        this.getConsumerInfoById(id)
      }
    },
    // 更改每页记录数
    handleSizeChange(size){
      this.limit = size
      this.getAllConsumer()
    },
    // 上传图片之前
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 jpg 或 png 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    // 上传图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/music_sys/consumer/updateConsumerAvator/${id}`
    },
    // 上传之后
    handleAvatarSuccess(res, file) {
      if (res.code == 200){
        this.$message({
          type: 'success',
          message: '上传成功!'
        })
        this.getAllConsumer(this.current)
      }else {
        this.$message({
          type: 'error',
          message: '上传失败!'
        })
      }
    },
    // 转向收藏页面
    getCollect(id){
      this.$router.push({path: '/collect', query: {id}})
    }
  }
}
</script>
<style scoped>
.handle-box{
  /*margin-bottom: 10px;*/
}
.consumer-img{
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
</style>
