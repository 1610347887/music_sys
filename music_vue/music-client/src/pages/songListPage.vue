<template>
  <div class="song-list">
    <ul class="song-list-header">
      <li v-for="(item,index) in songStyle" :key="index"
          @click="handleChangeView(item.name)"
          :class="{active:item.name == activityName}">
        {{item.name}}
      </li>
    </ul>
    <content-list :content-list="albumDatas"/>
  </div>
</template>
<script>
import ContentList from "../components/contentList";
import {getAllSongList, findSongListByS} from "../api/index"
import {songStyle} from "../assets/data/songList";

export default {
  name: 'song-list',
  components: {
    ContentList
  },
  data(){
    return{
      albumDatas: [],           // 歌单数据
      songStyle: [],            // 歌单风格
      activityName: '全部歌单',  // 当前活跃风格
    }
  },
  mounted() {
    this.songStyle = songStyle
    this.getSongList()
  },
  methods:{
    getSongList(){
      getAllSongList()
        .then(res => {
          if (res.data.length){
            this.albumDatas = res.data
          }else {
            this.$message({
              type: "warning",
              message: "暂无歌单"
            })
          }
        })
    },
    // 根据style显示对应的歌单
    handleChangeView(name){
      this.activityName = name
      if (name == "全部歌单"){
        this.getSongList()
      }else {
        this.getSongListByStyle(name)
      }
    },
    // 根据style查询歌单
    getSongListByStyle(name){
      findSongListByS(name)
        .then(res => {
          this.albumDatas = res.data
        })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/song-list";
</style>
