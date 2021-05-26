<template>
  <div class="song-list-album">
    <div class="album-slide">
      <div class="album-img">
        <img :src="attachImageUrl(tempList.pic)"/>
      </div>
      <div class="album-info">
        <h3>简介：</h3>
        <span>{{tempList.introduction}}</span>
      </div>
    </div>
    <div class="album-content">
      <div class="album-title">
        <p>{{tempList.title}}</p>
      </div>
      <div class="album-score">
        <div>
          <h3>歌单评分: </h3>
          <div>
            <el-rate v-model="score" disabled/>
          </div>
        </div>
        <span>{{score*2}}</span>
        <div style="margin-left: 100px" @click="sumbitScore">
          <h3>评价: </h3>
          <div>
            <el-rate v-model="evaluate" allow-half show-text/>
          </div>
        </div>
      </div>
      <div class="song-body">
        <album-content :songList="songLists">
          <template slot="title">歌单</template>
        </album-content>
        <comment :playId="songListsId" :type="1"></comment>
      </div>
    </div>
  </div>
</template>
<script>
import {mapGetters} from 'vuex'
import {mixin} from "../mixins";
import {getSongByListSongId, submitEvaluate, getSongListScore, getSongListById, getUserEvaluate} from "../api/index"
import AlbumContent from "../components/AlbumContent";
import Comment from "../components/comment";

export default {
  name: 'song-list-album',
  mixins: [mixin],
  components:{
    Comment,
    AlbumContent
  },
  data(){
    return{
      songLists: [],         // 当前歌单中的歌曲列表
      songListsId: '',       // 当前歌单的id
      score: 0,              // 歌单评分
      evaluate: 0,           // 评价的分数
      evaluateInfo: {},      // 评价的对象
    }
  },
  computed:{
    ...mapGetters([
      'listOfSongs',         // 当前播放列表
      'tempList',            // 当前的歌单对象
      'loginIn',             // 用户是否已登录
      'userId',              // 当前用户id
    ])
  },
  created() {
    this.songListsId = this.$route.params.id;
    this.getSongById()
    this.getScore()
    this.getSongListInfo()
    this.getUserEvaluateScore()
  },
  methods:{
    // 获取歌单信息
    getSongListInfo(){
      getSongListById(this.songListsId)
        .then(res => {
          this.$store.commit('setTempList', res.data)
        })
    },
    // 获取歌单中的歌曲列表
    getSongById(){
      getSongByListSongId(this.songListsId)
        .then(res => {
          this.songLists = res.data
          this.$store.commit('setListOfSongs', this.songLists)
        })
    },
    // 获取歌单评分
    getScore(){
      getSongListScore(this.songListsId)
        .then(res => {
          this.score = res.data / 2
        })
    },
    // 提交评分
    sumbitScore(){
      if (this.loginIn){
        this.evaluateInfo.songListId = this.songListsId
        this.evaluateInfo.consumerId = this.userId
        this.evaluateInfo.score = this.evaluate*2
        submitEvaluate(this.evaluateInfo)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "评价成功"
              })
              this.getScore()
            }else {
              this.$message({
                type: "error",
                message: "评价失败"
              })
            }
          }).catch(err => {
          this.$message({
            type: "error",
            message: "你已经评价过了"
          })
          this.getUserEvaluateScore()
        })
      }else {
        this.evaluate = null;
        this.$message({
          type: "warning",
          message: "请先登录"
        })
      }
    },
    // 获取用户评价分
    getUserEvaluateScore(){
      this.evaluateInfo.songListId = this.songListsId
      this.evaluateInfo.consumerId = this.userId
      getUserEvaluate(this.evaluateInfo)
        .then(res => {
          if (res.code == 200){
            this.evaluate = res.data.score / 2
          }
        }).catch(err => {
          console.log(err)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/song-list-album";
</style>
