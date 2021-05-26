<template>
  <div class="search-song-Lists">
    <content-list :content-list="albumDatas"/>
  </div>
</template>
<script>
import ContentList from "../contentList";
import {findSongListByT} from "../../api/index"
export default {
  name: 'search-songLists',
  components: {
    ContentList
  },
  data(){
    return{
      albumDatas: [],
    }
  },
  mounted() {
    this.getSongList()
  },
  methods:{
    getSongList(){
        findSongListByT(this.$route.query.keywords)
          .then(res => {
            if (res.data.length){
              this.albumDatas = res.data
            }else {
              this.$message({
                type: "warning",
                message: "暂无符合条件的歌单"
              })
            }
        })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../../assets/css/search-song-Lists";
</style>
