<template>
  <div class="table">
    <div class="songInfo">
      <i class="el-icon-tickets">{{songListTitle}}</i>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click=" getDilog">添加歌曲</el-button>
      </div>
    </div>
    <el-table size="mini"
              border style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              :data="songList">
      <el-table-column label="序号" type="index" width="200"/>
      <el-table-column prop="name" label="歌手-歌名"/>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" icon="el-icon-delete" name="删除" @click="deleteSongById(scope.row.id)"/>
        </template>
      </el-table-column>
    </el-table>
    <!--    添加歌曲-->
    <el-dialog :visible.sync="centerDialogVisible" title="添加歌曲" width="500px" center>
      <el-form :model="registerForm" ref="registerForm" action="" id="ap" label-width="70px" >
        <el-form-item prop="singerName" label="歌手" size="mini">
          <el-input v-model="registerForm.singerName"/>
        </el-form-item>
        <el-form-item prop="songName" label="歌名" size="mini">
          <el-input v-model="registerForm.songName"/>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="getSongId()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import '@/assets/js/iconfont.js'
import {getListSongById, addListSong, deleteListSong, getSongByName} from '../api'
import {mixin} from "../mixins";

export default {
  mixins: [mixin],
  data(){
    return{
      songListId: '',               // 歌单id
      songId: '',                   // 歌曲id
      songListTitle: '',            // 歌手名
      centerDialogVisible: false,   // 是否显示dialog弹出
      registerForm:{                // 添加歌曲对象绑定
        singerName: '',
        songName: ''
      },
      singerSongName: '',
      songList: [],                 // 歌曲集合
    }
  },
  created() {
    this.songListId = this.$route.query.id    // 接收歌单id
    this.songListTitle = this.$route.query.title    // 接收歌单标题
    this.getSongBySongListId()
  },
  methods:{
    // 获取dialog弹框
    getDilog(){
      this.registerForm = {}
      this.centerDialogVisible = true
    },
    // 添加歌曲前先获取歌曲id
    getSongId(){
      this.singerSongName = this.registerForm.singerName + '-' + this.registerForm.songName
      getSongByName(this.singerSongName).then(res => {
        this.songId = res.data.id
        console.log(this.songId)
        this.saveSong()
      }).catch(err => {
        this.$message({
          type: "warning",
          message: "暂无该歌曲,请添加已有的歌曲!"
        })
      })
    },
    // 添加歌曲
    saveSong(){
      console.log(this.songId)
      addListSong(this.songId, this.songListId)
        .then(res => {
          if (res.code == 200){
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.centerDialogVisible = false
            this.getSongBySongListId()
          }else {
            this.$message({
              type: 'error',
              message: '添加失败'
            })
          }
        })
    },
    // 通过歌单id获取歌曲
    getSongBySongListId(){
      getListSongById(this.songListId)
        .then(res => {
          this.songList = res.data
        })
    },
    // 删除歌手
    deleteSongById(id){
      this.$confirm('此操作将永久删除该歌曲, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        deleteListSong(id)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.getSongBySongListId()
            }else {
              this.$message({
                type: "error",
                message: "删除失败"
              })
            }
          })
      })
    }
  }
}
</script>
<style scoped>
.handle-box{
  /*margin-bottom: 10px;*/
}
.song-img{
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
.songInfo{
  text-align: center;
  color: #ffffff;
  background-color: #334256;
}
.play{
  position: absolute;
  z-index: 100;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  top: 18px;
  left: 10px;
}
.icon{
  width: 2em;
  color: 2em;
  color: white;
  fill: currentColor;
  overflow: hidden;
}
</style>
