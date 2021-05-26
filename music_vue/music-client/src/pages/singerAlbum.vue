<template>
  <div class="singer-album">
    <div class="album-slide">
      <div class="singer-img">
        <img :src="attachImageUrl(singer.pic)"/>
      </div>
      <ul class="info">
        <li v-if="singer.sex == 0 || singer.sex == 1">{{attachSex(singer.sex)}}</li>
        <li>生日：{{singer.birth}}</li>
        <li>故乡：{{singer.location}}</li>
      </ul>
    </div>
    <div class="album-content">
      <div class="intro">
        <h2>{{singer.name}}</h2>
        <span>{{singer.introduction}}</span>
      </div>
      <div class="content">
        <album-content :songList="listOfSongs">
          <template slot="title">歌单</template>
        </album-content>
        <comment :playId="singerId" :type="0"></comment>
      </div>
    </div>
  </div>
</template>
<script>
import {mixin} from "../mixins";
import {mapGetters} from "vuex"
import AlbumContent from "../components/AlbumContent";
import Comment from "../components/comment";
import {getSongBySingerId,getSingerInfo} from "../api/index";

export default {
  name: 'singer-album',
  mixins: [mixin],
  components:{
    AlbumContent,
    Comment
  },
  data(){
    return{
      singerId: '',            // 当前歌手id
      singer: {},              // 歌手对象
    }
  },
  computed:{
    ...mapGetters([
      'listOfSongs',           // 当前播放列表
      'tempList',              // 当前歌手对象
      'loginIn',               // 用户是否已登录
      'userId',                // 当前用户id
    ])
  },
  created() {
    this.singerId = this.$route.params.id
    this.getSingerInfoById()
    this.getSong()
  },
  methods:{
    // 获取歌手信息
    getSingerInfoById(){
      getSingerInfo(this.singerId)
        .then(res => {
          this.singer = res.data
        })
    },
    // 根据歌手id查询歌曲
    getSong(){
      getSongBySingerId(this.singerId)
        .then(res => {
          console.log(res.data)
          this.$store.commit('setListOfSongs', res.data)
        })
    },
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/singer-album";
</style>
