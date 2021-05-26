<template>
  <div class="my-music">
    <div class="album-slide">
      <div class="album-img">
        <img :src="attachImageUrl(userInfo.avator)"/>
      </div>
      <ul class="album-info">
        <li>昵称：{{userInfo.username}}</li>
        <li v-if="userInfo.sex == 0 || userInfo.sex == 1">性别：{{attachSex(userInfo.sex)}}</li>
        <li>生日：{{userInfo.birth}}</li>
        <li>故乡：{{userInfo.location}}</li>
      </ul>
    </div>
    <div class="album-content">
      <div class="album-title">
        个性签名：{{userInfo.introduction}}
      </div>
      <div class="song-body">
        <album-content :song-list="collectSongList">
          <template slot="title">我的收藏</template>
        </album-content>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters} from "vuex"
import {mixin} from "../mixins";
import {getCollectByConsumerId, getSongById} from "../api";
import AlbumContent from "../components/AlbumContent";

export default {
  name: 'my-music',
  components: {AlbumContent},
  mixins: [mixin],
  data(){
    return{
      songIdList: [],          // 歌曲id列表
      collectSongList: [],     // 用户收藏的歌曲列表
    }
  },
  computed:{
    ...mapGetters([
      'userId',         // 用户id
      'userInfo',       // 当前用户信息
    ])
  },
  mounted() {
    this.getCollectionId()
  },
  methods:{
    // 获取用户收藏的歌曲id
    getCollectionId(){
      getCollectByConsumerId(this.userId)
        .then(res => {
          for (let item of res.data){
            this.songIdList.push(item.songId)
          }
          let _this = this
          _this.getCollectSongList()
        })
    },
    // 通过歌曲id获取歌曲信息
    async getCollectSongList(){
      for (let item of this.songIdList){
        await getSongById(item).then(res => {
          this.collectSongList.push(res.data)
        })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/my-music";
</style>
