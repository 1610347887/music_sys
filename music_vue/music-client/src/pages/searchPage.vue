<template>
  <div class="search">
    <nav class="searchList-nav" ref="change">
      <span :class="{isActive: toggle=='Songs'}" @click="handleChangeView('Songs')">歌曲</span>
      <span :class="{isActive: toggle=='SongLists'}" @click="handleChangeView('SongLists')">歌单</span>
    </nav>
    <component :is="currentView"/>
  </div>
</template>
<script>
import searchSongs from "../components/search/searchSongs";
import searchSongLists from "../components/search/searchSongLists";
export default {
  name: 'search',
  components:{
    searchSongs,
    searchSongLists
  },
  data(){
    return{
      toggle: 'Songs',
      currentView: 'searchSongs'
    }
  },
  methods:{
    handleChangeView(componet){
      this.currentView = 'search' + componet
      this.toggle = componet
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/search";
</style>
