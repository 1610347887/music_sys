<template>
  <div class="table">
    <div class="songInfo">
      <i class="el-icon-tickets">歌曲信息--{{this.singerName}}</i>
    </div>
    <div class="container">
      <div class="handle-box">
        <span>
          <el-button type="primary" size="mini" @click="getDilogTitle()">添加歌曲</el-button>
          <!--条件查询-->
          <span style="text-align: center">
            <el-input v-model="findObj.name" size="mini" placeholder="歌曲名" style="width: 200px"></el-input>
            <el-button type="primary" size="mini" icon="el-icon-search" @click="getSong()">查询</el-button>
          </span>
        </span>
      </div>
    </div>
    <el-table size="mini"
              border style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              :data="songList">
      <el-table-column label="序号" type="index" width="50"/>
      <el-table-column label="歌曲图片" width="100">
        <template slot-scope="scope">
          <div class="song-img">
            <img :src="getUrl(scope.row.pic)" style="width: 100%">
          </div>
          <div class="play" @click="setSongStatus(scope.row.url,scope.row.id)">
            <div v-if="toggle == true && toggleId == scope.row.id">
              <svg class="icon">
                <use xlink:href="#icon-zanting"/>
              </svg>
            </div>
            <div v-else>
              <svg class="icon">
                <use xlink:href="#icon-bofanganniu"/>
              </svg>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="歌手-歌名" width="110"/>
      <el-table-column prop="introduction" label="专辑" width="120"/>
      <el-table-column label="歌词">
        <template slot-scope="scope">
          <ul style="height: 100px;overflow: auto">
            <li v-for="(item,index) in parseLyric(scope.row.lyric)" :key="index">
              {{item}}
            </li>
          </ul>
        </template>
      </el-table-column>
      <el-table-column label="资源更新" width="100">
        <template slot-scope="scope">
          <el-upload :action="uploadUrl(scope.row.id)"
                     :before-upload="beforeAvatarUpload"
                     :on-success="handleAvatarSuccess"
                     :show-file-list="false">
            <el-button type="primary" size="mini">更新图片</el-button>
          </el-upload>
          <br/>
          <el-upload :action="uploadSong(scope.row.id)"
                     :before-upload="beforeSongUpload"
                     :on-success="handleAvatarSuccess"
                     :show-file-list="false">
            <el-button type="primary" size="mini">更新歌曲</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" name="修改" @click="getDilogTitle(scope.row.id)"/>
          <el-button type="danger" size="mini" icon="el-icon-delete" name="删除" @click="deleteSongById(scope.row.id)"/>
        </template>
      </el-table-column>
    </el-table>
    <!--    添加歌手-->
    <el-dialog :visible.sync="centerDialogVisible" :title="titleMap[dialogTitle]" width="500px" center>
      <el-form :model="registerForm" ref="registerForm" action="" id="ap" label-width="70px" >
        <el-form-item prop="name" label="歌名" size="mini">
          <el-input v-model="registerForm.name"/>
        </el-form-item>
        <el-form-item prop="introduction" label="专辑" size="mini">
          <el-input v-model="registerForm.introduction" type="text"/>
        </el-form-item>
        <el-form-item prop="lyric" label="歌词" size="mini">
          <el-input v-model="registerForm.lyric" type="textarea"/>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button size="mini" @click="centerDialogVisible = false">取消</el-button>
        <el-button size="mini" @click="saveOrUpdate(registerForm.id)">确定</el-button>
      </span>
    </el-dialog>

<!--    分页-->
    <el-pagination
      :total="total"
      :current-page="current"
      :page-size="limit"
      :page-sizes="[4, 10, 15, 20]"
      background
      style="padding:30px 0; text-align:center;"
      layout="total,sizes, prev, pager, next,jumper"
      @size-change="handleSizeChange"
      @current-change="getSong"/>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import '@/assets/js/iconfont.js'
import {getSongBySingerId, addSong, getSongById, updateSong, deleteSong, findSong} from '../api'
import {mixin} from "../mixins";

export default {
  mixins: [mixin],
  data(){
    return{
      singerId: '',                 // 歌手id
      singerName: '',               // 歌手名
      centerDialogVisible: false,   // 是否显示dialog弹出
      registerForm:{                // 添加歌曲对象绑定
        singerId: '',
        singerName: ''
      },
      total: 0,                     // 总记录数
      current: 1,                   // 当前页
      limit: 4,                     // 每页记录数
      findObj: {},                  // 条件查找对象
      songList: [],                 // 歌曲集合
      titleMap: {                   // dialog标题信息
        add: '添加歌曲',
        update: '修改歌曲'
      },
      dialogTitle: '',
      multipleSelection: [],        // 已选中项
      toggle: false,                 //播放器图标状态
      toggleId: ''
    }
  },
  created() {
    this.singerId = this.$route.query.id
    this.singerName = this.$route.query.name
    this.findObj.singerId = this.$route.query.id
    console.log(this.findObj)
    this.getSong()
  },
  destroyed() {            // 离开页面就暂停播放
    this.$store.commit('setIsPlay', false)
  },
  computed:{
    ...mapGetters([
      'isPlay'
    ])
  },
  methods:{
    // 条件查询（带分页）
    getSong(page = 1){
      this.current = page
      findSong(this.current,this.limit,this.findObj)
        .then(res => {
          this.songList = res.data.records
          this.total = res.data.total
          console.log(res)
        })
    },
    // 查询歌手的所有歌曲
    // getSongById(){
    //   getSongBySingerId(this.singerId)
    //     .then(res =>{
    //       this.songList = res.data
    //       // console.log(res)
    //     })
    // },
    // 判断是添加还是修改
    saveOrUpdate(id){
      if (!id){
        this.saveSong()
      }else{
        this.updateSong()
      }
    },
    // 添加歌曲
    saveSong(){
      this.registerForm.singerId = this.singerId;
      this.registerForm.singerName = this.singerName;
      addSong(this.registerForm)
        .then(res => {
          if (res.code == 200){
            this.$message({
              type: 'success',
              message: '添加成功'
            })
            this.centerDialogVisible = false
            this.getSong(this.current)
          }else {
            this.$message({
              type: 'error',
              message: '添加失败'
            })
          }
        })
    },
    // 修改歌曲信息
    updateSong(){
      updateSong(this.registerForm)
        .then(res => {
          this.$message({
            type: "success",
            message: "修改成功"
          })
          this.centerDialogVisible = false
          this.getSong(this.current)
        })
    },
    // 通过id获取歌手信息
    getSongInfoById(id){
      getSongById(id)
        .then(res => {
          this.registerForm = res.data
        })
    },
    // 删除歌手
    deleteSongById(id){
      this.$confirm('此操作将永久删除该歌曲, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        deleteSong(id)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.getSong(this.current)
            }else {
              this.$message({
                type: "error",
                message: "删除失败"
              })
            }
          })
      })
    },
    // 解析歌词
    parseLyric(text){
      let lines = (text||"").split("\n");
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result = [];
      for (let item of lines){
        let value = item.replace(pattern,'');
        result.push(value);
      };
      return result;
    },
    // 获取dialog标题
    getDilogTitle(id){
      this.registerForm = {}
      this.centerDialogVisible = true
      if (!id){
        this.dialogTitle = 'add'
      }else {
        this.dialogTitle = 'update'
        this.getSongInfoById(id)
      }
    },
    // 更改每页记录数
    handleSizeChange(size){
      this.limit = size
      this.getSong()
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
      return `${this.$store.state.HOST}/music_sys/song/updateSongPic/${id}`
    },
    beforeSongUpload(file){
      var testMsg = file.name.substring(file.name.lastIndexOf('.')+1);
      if (testMsg != "mp3"){
        this.$message({
          type: "error",
          message: "只能上传mp3格式的文件"
        });
        return false;
      }
      return true;
    },
    // 上传歌曲
    uploadSong(id){
      return `${this.$store.state.HOST}/music_sys/song/updateSong/${id}`
    },
    // 上传之后
    handleAvatarSuccess(res, file) {
      if (res.code == 200){
        this.$message({
          type: 'success',
          message: '上传成功!'
        })
        this.getSong(this.current)
      }else {
        this.$message({
          type: 'error',
          message: '上传失败!'
        })
      }
    },
    // 设置歌曲播放状态
    setSongStatus(url,id){
      this.toggle = !this.toggle;
      this.toggleId = id
      // console.log(url)
      this.$store.commit('setUrl',this.$store.state.HOST + url);
      if (this.isPlay){
        this.$store.commit('setIsPlay',false);
      }else {
        this.$store.commit('setIsPlay',true);
      }
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
