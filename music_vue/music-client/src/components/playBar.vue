<template>
  <div class="play-bar" :class="{show: !toggle}">
    <div @click="toggle = !toggle" class="item-up" :class="{turn: !toggle}">
      <svg class="icon">
        <use xlink:href="#icon-jiantou-xia-cuxiantiao"/>
      </svg>
    </div>
    <div class="kongjian">
<!--      上一首-->
      <div class="item" @click="prev">
        <svg class="icon">
          <use xlink:href="#icon-ziyuanldpi"/>
        </svg>
      </div>
      <!--      播放-->
      <div class="item" @click="togglePlay">
        <svg class="icon">
          <use :xlink:href="playButtonUrl"/>
        </svg>
      </div>
      <!--      下一首-->
      <div class="item" @click="next">
        <svg class="icon">
          <use xlink:href="#icon-ziyuanldpi1"/>
        </svg>
      </div>
      <!--      歌曲图片-->
      <div class="item-img" @click="toLyric">
        <img :src="picUrl"/>
      </div>
<!--      歌名--歌手-->
      <div>
        <div>{{this.title}}</div>
        <div>{{this.artist}}</div>
      </div>
      <!--      播放进度-->
      <div class="playing-speed">
<!--        播放开始时间-->
        <div class="current-time">{{nowTime}}</div>
        <div class="progress-box">
          <div ref="progress" class="progress" @mousemove="mousemove">
<!--            进度条-->
            <div ref="bg" class="bg" @click="updateLocat">
              <div ref="curProgress" class="cur-progress" :style="{width:curlength+'%'}"></div>
            </div>
<!--            拖动的点-->
            <div ref="idot" class="idot" :style="{left:curlength+'%'}" @mousedown="mousedown" @mouseup="mouseup"></div>
          </div>
        </div>
<!--        播放结束时间-->
        <div class="left-time">{{songTime}}</div>
<!--        音量-->
        <div class="item item-volume">
          <svg v-if="volume != 0" class="icon">
            <use xlink:href="#icon-yinliang1"/>
          </svg>
          <svg v-else class="icon">
            <use xlink:href="#icon-yinliangjingyinheix"/>
          </svg>
          <el-slider class="volume" v-model="volume" :vertical="true"/>
        </div>
<!--        收藏-->
        <div class="item" @click="collection">
          <svg :class="{active:isCollect}" class="icon">
            <use xlink:href="#icon-xihuan-shi"/>
          </svg>
        </div>
<!--        下载-->
        <div class="item" @click="downloadMusic">
          <svg class="icon">
            <use xlink:href="#icon-xiazai"/>
          </svg>
        </div>
<!--        当前播放的歌曲列表-->
        <div class="item">
          <svg class="icon" @click="changeAside">
            <use xlink:href="#icon-liebiao"/>
          </svg>
        </div>
      </div>
    </div>
  </div>

</template>
<script>
import {mapGetters} from 'vuex'
import {mixin} from "../mixins";
import {download, addCollect} from "../api/index";

export default {
  name: 'play-bar',
  mixins: [mixin],
  data(){
    return{
      nowTime: '00:00',        // 当前播放进度的时间
      songTime: '00:00',       // 歌曲总时间
      curlength: 0,            // 进度条的位置
      progressLength: 0,       // 进度条的总长度
      mouseStartX: 0,          // 拖拽开始位置
      tag: false,              // 拖拽标志
      volume: 100,             // 音量
      toggle: true,            // 是否显示播放器
      collectInfo: {},         // 收藏歌曲对象
    }
  },
  computed: {
    ...mapGetters([
      'id',             // 歌曲的id
      'url',            // 歌曲的路径
      'isPlay',         // 是否正在播放
      'playButtonUrl',  // 播放状态图标
      'picUrl',         // 正在播放的歌曲图片
      'title',          // 歌名
      'artist',         // 歌手
      'duration',       // 音乐时长
      'curTime',        // 当前音乐的播放位置
      'showAside',      // 是否显示待播放的歌曲列表
      'listIndex',      // 当前歌曲在歌单中的位置
      'listOfSongs',    // 当前歌曲列表的歌曲
      'autoNext',       // 自动播放下一首
      'loginIn',        // 用户是否已登录
      'userId',         // 当前用户id
      'isCollect',      // 收藏状态
    ])
  },
  mounted() {
    this.progressLength = this.$refs.progress.getBoundingClientRect().width
    document.querySelector('.item-volume').addEventListener('click',function (e){
      document.querySelector('.volume').classList.add('show-volume')
      e.stopPropagation()
    },false)
    document.querySelector('.item-volume').addEventListener('click',function (e){
      e.stopPropagation()
    },false)
    document.addEventListener('click',function (){
      document.querySelector('.volume').classList.remove('show-volume')
    },false)

  },
  watch:{
    // 音乐播放时间变化
    curTime(){
      this.nowTime = this.formatSeconds(this.curTime)
      this.songTime = this.formatSeconds(this.duration)
      this.curlength = (this.curTime / this.duration) * 100
    },
    // 音量变化
    volume(){
      this.$store.commit('setVolume',this.volume / 100)
    },
    // 自动播放下一首
    autoNext(){
      this.next()
    }
  },
  methods:{
    // 控制音乐播放和暂停
    togglePlay(){
      if (this.isPlay){
        this.$store.commit('setIsPlay',false)
        this.$store.commit('setPlayButtonUrl','#icon-bofang')
      }else{
        this.$store.commit('setIsPlay',true)
        this.$store.commit('setPlayButtonUrl','#icon-zanting')
      }
    },
    // 拖拽开始
    mousedown(e){
      this.mouseStartX = e.clientX
      this.tag = true
    },
    // 拖拽结束
    mouseup(){
      this.tag = false
    },
    // 拖拽中
    mousemove(e){
      if (!this.duration){
        return false
      }
      if (this.tag){
        let movementX = e.clientX - this.mouseStartX         // 滑块拖动距离
        let curLength = this.$refs.curProgress.getBoundingClientRect().width
        let newPercent = ((movementX + curLength) / this.progressLength) * 100
        if (newPercent > 100){
          newPercent = 100
        }
        this.curlength = newPercent
        this.mouseStartX = e.clientX
        this.changeTime(this.curlength)
      }
    },
    // 更改歌曲进度
    changeTime(percent){
      if (!this.isPlay){
        this.$store.commit('setPlayButtonUrl','#icon-zanting')
      }
      let newCurTime = percent*0.01*this.duration
      this.$store.commit('setChangeTime',newCurTime)
    },
    // 点击播放条切换播放位置
    updateLocat(e){
      if (!this.tag){
        // 进度条的左侧坐标
        let curLength = this.$refs.bg.offsetLeft
        let newPrecent = ((e.clientX - curLength) / this.progressLength) * 100
        if (newPrecent > 100){
          newPrecent = 100
        }else if (newPrecent < 0){
          newPrecent = 0
        }
        this.curlength = newPrecent
        this.changeTime(this.curlength)
      }
    },
    changeAside(){
      this.$store.commit('setShowAside', !this.showAside)
    },
    // 上一首
    prev(){
      if (this.listIndex != -1 && this.listOfSongs.length > 1){
        if (this.listIndex > 0){
          this.$store.commit('setListIndex',this.listIndex - 1)           // 上一首
        }else {
          this.$store.commit('setListIndex',this.listOfSongs.length - 1)  // 如果已经处在第一首，则播放最后一首
        }
        this.toplay(this.listOfSongs[this.listIndex].id,
                     this.listOfSongs[this.listIndex].url,
                     this.listOfSongs[this.listIndex].pic,
                     this.listIndex,
                     this.listOfSongs[this.listIndex].name,
                     this.listOfSongs[this.listIndex].lyric)
      }
    },
    // 下一首
    next(){
      if (this.listIndex != -1 && this.listOfSongs.length > 1){
        if (this.listIndex < this.listOfSongs.length - 1){
          this.$store.commit('setListIndex',this.listIndex + 1)           // 上一首
        }else {
          this.$store.commit('setListIndex',0)  // 如果已经处在第一首，则播放最后一首
        }
        this.toplay(this.listOfSongs[this.listIndex].id,
          this.listOfSongs[this.listIndex].url,
          this.listOfSongs[this.listIndex].pic,
          this.listIndex,
          this.listOfSongs[this.listIndex].name,
          this.listOfSongs[this.listIndex].lyric)
      }
    },
    // 转向歌词页面
    toLyric(){
      this.$router.push({path:'/lyric'})
    },
    // 下载音乐
    downloadMusic(){
      download(this.url).then(res => {
        let content = res.data;
        let eleLink = document.createElement('a');
        // 下载名称
        eleLink.download = `${this.artist}--${this.title}.mp3`;
        eleLink.style.display = 'none';
        // 把字符内容转换成blob地址
        let blob = new Blob([content]);
        eleLink.href = URL.createObjectURL(blob);
        // 把链接地址加到document里
        document.body.appendChild(eleLink);
        // 触发点击
        eleLink.click();
        // 移除这个新加的控件
        document.body.removeChild(eleLink);
      }).catch(err => {
        console.log(err)
      })
    },
    // 收藏音乐
    collection(){
      if (this.loginIn){
        this.collectInfo.consumerId = this.userId;
        this.collectInfo.type = 0;
        this.collectInfo.songId = this.id;
        addCollect(this.collectInfo)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: 'success',
                message: '收藏成功'
              })
              this.$store.commit('setIsCollect',true)
            }else if (res.code == 202) {
              this.$message({
                type: 'warning',
                message: res.message
              })
            }else if (res.code == 203){
              this.$message({
                type: 'warning',
                message: res.message
              })
            }else {
              this.$message({
                type: 'error',
                message: '收藏失败'
              })
            }
          })
      }else {
        this.$message({
          type: 'warning',
          message: '请先登录'
        })
      }
    },
  }
}
</script>

<style  lang="scss" scoped>
@import '../assets/css/play-bar';
</style>

