<template>
  <div class="table">
    <div class="container">
      <div class="handle-box">
        <span>
          <el-button type="primary" size="mini" @click="getDilogTitle()">添加歌单</el-button>
          <!--条件查询-->
          <span style="text-align: center">
            <el-input v-model="findObj.title" size="mini" placeholder="歌单标题" style="width: 200px"></el-input>
            <el-button type="primary" size="mini" icon="el-icon-search" @click="findSongList()">查询</el-button>
          </span>
        </span>
      </div>
    </div>
    <el-table size="mini"
              border style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              :data="songListList">
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column label="歌单图片" width="100">
        <template slot-scope="scope">
          <el-upload :action="uploadUrl(scope.row.id)"
                     :before-upload="beforeAvatarUpload"
                     :on-success="handleAvatarSuccess"
                     :show-file-list="false">
            <div class="songList-img">
              <img v-if="scope.row.pic != null" :src="getUrl(scope.row.pic)" style="width: 100%" title="点击更换图片">
              <i v-else class="el-icon-plus" style="padding: 20px; font-size: 40px" title="点击上传图片"/>
            </div>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="歌单标题" width="110"/>
      <el-table-column label="简介" style="height: 104px">
        <template slot-scope="scope">
          <p>{{scope.row.introduction}}</p>
        </template>
      </el-table-column>
      <el-table-column prop="style" label="风格" width="110"/>
      <el-table-column label="歌曲管理" width="110">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="songManage(scope.row.id, scope.row.title)">歌曲管理</el-button>
        </template>
      </el-table-column>
      <el-table-column label="评论" width="80">
        <template slot-scope="scope">
          <el-button size="mini" @click="getComment(1,scope.row.id)">评论</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="125">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" name="修改" @click="getDilogTitle(scope.row.id)"/>
          <el-button type="danger" size="mini" icon="el-icon-delete" name="删除" @click="deleteSongList(scope.row.id)"/>
        </template>
      </el-table-column>
    </el-table>
    <!--    添加歌单-->
    <el-dialog :visible.sync="centerDialogVisible" :title="titleMap[dialogTitle]" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="70px" >
        <el-form-item prop="title" label="歌单标题" size="mini">
          <el-input v-model="registerForm.title" placeholder="歌单名"/>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction" type="textarea" placeholder="简介"/>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="registerForm.style" placeholder="风格"/>
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
      @current-change="findSongList"/>
  </div>
</template>

<script>
import {getSongList, addSongList, getSongListById, updateSongList, deleteSongListById} from '../api/index'
import {mixin} from "../mixins/index";

export default {
  mixins: [mixin],
  data(){
    return{
      centerDialogVisible: false,   // 是否显示dialog弹出
      registerForm:{},              // 添加歌单对象绑定
      total: 0,                     // 总记录数
      current: 1,                   // 当前页
      limit: 4,                     // 每页记录数
      findObj: {},                  // 条件查找对象
      songListList: [],               // 歌单集合
      titleMap: {                   // dialog标题信息
        add: '添加歌单',
        update: '修改歌单信息'
      },
      dialogTitle: '',
    }
  },
  created() {
    this.findSongList()
  },
  methods:{
    // 查询所有歌单
    findSongList(page = 1){
      this.current = page
      getSongList(this.current, this.limit, this.findObj)
        .then(res =>{
          this.songListList = res.data.records
          this.total = res.data.total
        })
    },
    // 判断是添加还是修改
    saveOrUpdate(id){
      if (!id){
        this.saveSongList()
      }else{
        this.updateSongList()
      }
    },
    // 添加歌单
    saveSongList(){
      addSongList(this.registerForm)
        .then(res => {
          if (res.code == 200){
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.centerDialogVisible = false
            this.findSongList(this.current)
          }else {
            this.$message({
              type: 'error',
              message: '添加失败'
            })
          }
        })
    },
    // 修改歌单信息
    updateSongList(){
      updateSongList(this.registerForm)
        .then(res => {
          this.$message({
            type: "success",
            message: "修改成功"
          })
          this.centerDialogVisible = false
          this.findSongList(this.current)
        })
    },
    // 通过id获取歌单信息
    getSongListInfoById(id){
      getSongListById(id)
        .then(res => {
          this.registerForm = res.data
        })
    },
    // 删除歌单
    deleteSongList(id){
      this.$confirm('此操作将永久删除该歌单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        deleteSongListById(id)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.findSongList(this.current)
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
        this.getSongListInfoById(id)
      }
    },
    // 歌曲管理
    songManage(id,title){
      // console.log(id)
      // console.log(name)
      this.$router.push({path: `/listSong`,query:{id, title}})
    },
    // 更改每页记录数
    handleSizeChange(size){
      this.limit = size
      this.findSongList()
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
      return `${this.$store.state.HOST}/music_sys/songList/updatesongListPic/${id}`
    },
    // 上传之后
    handleAvatarSuccess(res, file) {
      if (res.code == 200){
        this.$message({
          type: 'success',
          message: '上传成功!'
        })
        this.findSongList(this.current)
      }else {
        this.$message({
          type: 'error',
          message: '上传失败!'
        })
      }
    },
    // 获取歌单评论对象
    getComment(type,id){
      this.$router.push({path: '/comment',query: {type,id}})
    }
  }
}
</script>
<style scoped>
.handle-box{
  /*margin-bottom: 10px;*/
}
.songList-img{
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
</style>
