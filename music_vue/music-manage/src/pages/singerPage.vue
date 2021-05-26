<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <span>
          <el-button type="primary" size="mini" @click="getDilogTitle()">添加歌手</el-button>
          <!--条件查询-->
          <span style="text-align: center">
            <el-input v-model="findObj.name" size="mini" placeholder="歌手名" style="width: 200px"></el-input>
            <el-select v-model="findObj.sex" size="mini"  placeholder="性别" style="width: 80px">
              <el-option
                v-for="item in options"
                :value="item.value"
                :key="item.value"
                :label="item.label">
              </el-option>
            </el-select>
            <el-button type="primary" size="mini" icon="el-icon-search" @click="getAllSinger()">查询</el-button>
          </span>
        </span>
      </div>
    </div>
    <el-table size="mini"
              border style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              :data="singerList">
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column label="歌手图片" width="100">
        <template slot-scope="scope">
          <el-upload :action="uploadUrl(scope.row.id)"
                     :before-upload="beforeAvatarUpload"
                     :on-success="handleAvatarSuccess"
                     :show-file-list="false">
            <div class="singer-img">
              <img v-if="scope.row.pic != null" :src="getUrl(scope.row.pic)" style="width: 100%" title="点击更换图片">
              <i v-else class="el-icon-plus" style="padding: 20px; font-size: 40px" title="点击上传图片"/>
            </div>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="歌手" width="110"/>
      <el-table-column label="性别" prop="sex" width="50">
        <template slot-scope="scope">
          {{changeSex(scope.row.sex)}}
        </template>
      </el-table-column>
      <el-table-column label="生日" prop="birth" width="85"/>
      <el-table-column label="地区" prop="location" width="90"/>
      <el-table-column label="简介" style="height: 104px">
        <template slot-scope="scope">
          <p>{{scope.row.introduction}}</p>
        </template>
      </el-table-column>
      <el-table-column label="歌曲管理" width="110">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="songManage(scope.row.id, scope.row.name)">歌曲管理</el-button>
        </template>
      </el-table-column>
      <el-table-column label="评论" width="80">
        <template slot-scope="scope">
          <el-button size="mini" @click="getComment(0,scope.row.id)">评论</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="125">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" name="修改" @click="getDilogTitle(scope.row.id)"/>
          <el-button type="danger" size="mini" icon="el-icon-delete" name="删除" @click="deleteSinger(scope.row.id)"/>
        </template>
      </el-table-column>
    </el-table>
<!--    添加歌手-->
    <el-dialog title="添加歌手" :visible.sync="centerDialogVisible" :title="titleMap[dialogTitle]" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="60px" >
        <el-form-item prop="name" label="歌手" size="mini">
          <el-input v-model="registerForm.name" placeholder="歌手名"/>
        </el-form-item>
        <el-form-item label="性别" size="mini">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker type="date"
                          v-model="registerForm.birth"
                          value-format="yyyy-MM-dd"
                          format="yyyy-MM-dd"
                          placeholder="选择日期"
                          style="width: 100%"/>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="registerForm.location" placeholder="地区"/>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction" type="textarea" placeholder="简介"/>
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
      @current-change="getAllSinger"/>
  </div>
</template>

<script>
import {addSinger, getSingerList, deleteSingerById, getSingerInfo, updateSingerInfo} from '../api/index'
import {mixin} from "../mixins/index";

export default {
  mixins: [mixin],
  data(){
    return{
      centerDialogVisible: false,   // 是否显示dialog弹出
      registerForm:{},              // 添加歌手对象绑定
      total: 0,                     // 总记录数
      current: 1,                   // 当前页
      limit: 4,                     // 每页记录数
      findObj: {},                  // 条件查找对象
      singerList: [],               // 歌手集合
      titleMap: {                   // dialog标题信息
        add: '添加歌手',
        update: '修改歌手信息'
      },
      dialogTitle: '',
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
          value: '2',
          label: '组合'
        },
        {
          value: '',
          label: ''
        }
      ]
    }
  },
  created() {
    this.getAllSinger(this.current)
  },
  methods:{
    // 查询所有歌手
    getAllSinger(page = 1){
      this.current = page
      getSingerList(this.current, this.limit, this.findObj)
        .then(res =>{
          this.singerList = res.data.records
          this.total = res.data.total
        })
    },
    // 判断是添加还是修改
    saveOrUpdate(id){
      if (!id){
        this.saveSinger()
      }else{
        this.updateSinger()
      }
    },
    // 添加歌手
    saveSinger(){
      addSinger(this.registerForm)
        .then(res => {
          if (res.code == 200){
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.centerDialogVisible = false
            this.getAllSinger(this.current)
          }else {
            this.$message({
              type: 'error',
              message: '添加失败'
            })
          }
        })
    },
    // 修改歌手信息
    updateSinger(){
      updateSingerInfo(this.registerForm)
        .then(res => {
          this.$message({
            type: "success",
            message: "修改成功"
          })
          this.centerDialogVisible = false
          this.getAllSinger(this.current)
        })
    },
    // 通过id获取歌手信息
    getSingerInfoById(id){
      getSingerInfo(id)
        .then(res => {
          this.registerForm = res.data
        })
    },
    // 删除歌手
    deleteSinger(id){
      this.$confirm('此操作将永久删除该歌手, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        deleteSingerById(id)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.getAllSinger(this.current)
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
      this.centerDialogVisible = true
      if (!id){
        this.dialogTitle = 'add'
      }else {
        this.dialogTitle = 'update'
        this.getSingerInfoById(id)
      }
    },
    // 歌曲管理
    songManage(id,name){
      // console.log(id)
      // console.log(name)
      this.$router.push({path: `/song`,query:{id,name}})
    },
    // 更改每页记录数
    handleSizeChange(size){
      this.limit = size
      this.getAllSinger()
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
      return `${this.$store.state.HOST}/music_sys/singer/updateSingerPic/${id}`
    },
    // 上传之后
    handleAvatarSuccess(res, file) {
      if (res.code == 200){
        this.$message({
          type: 'success',
          message: '上传成功!'
        })
        this.getAllSinger(this.current)
      }else {
        this.$message({
          type: 'error',
          message: '上传失败!'
        })
      }
    },
    // 转向评论页面
    getComment(type,id){
      this.$router.push({path: '/comment',query:{type,id}})
    }
  }
}
</script>
<style scoped>
  .handle-box{
    /*margin-bottom: 10px;*/
  }
  .singer-img{
    width: 100%;
    height: 80px;
    border-radius: 5px;
    margin-bottom: 5px;
    overflow: hidden;
  }
</style>
