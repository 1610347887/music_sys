<template>
  <div class="home">
    <swipper/>
    <div class="section" v-for="(item, index) in songsList" :key="index">
      <div class="section-title">{{item.name}}</div>
      <content-list :contentList="item.list"/>
    </div>
    <the-footer/>
  </div>
</template>

<script>
import Swipper from "../components/swipper";
import contentList from "../components/contentList";
import {getAllSinger, getAllSongList} from '../api/index'
import TheFooter from "../components/theFooter";
export default {
  name: 'home',
  components: {
    TheFooter,
    Swipper,
    contentList
  },
  data () {
    return {
      songsList: [
        {name: '歌单', list: []},
        {name: '歌手', list: []},
      ]
    }
  },
  created () {
    this.getSongList()
    this.getSinger()
    console.log(this.$store.state.user.loginIn)
  },
  methods: {
    // 获取歌单
    getSongList(){
      getAllSongList().then(res => {             // 获取前十条歌单
        this.songsList[0].list = res.data.splice(0,10);
      }).catch(err => {
        console.log(err)
      })
    },
    // 获取歌手
    getSinger(){
      getAllSinger().then(res => {
        this.songsList[1].list = res.data.splice(0,10);
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>
