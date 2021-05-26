<template>
  <div class="search-songs">
    <album-content :songList="albumDatas"/>
  </div>
</template>
<script>
import AlbumContent from "../AlbumContent";
import {getSongByN} from "../../api";
export default {
  name: 'search-songs',
  components: {
    AlbumContent
  },
  data(){
    return{
      albumDatas: []
    }
  },
  mounted() {
    this.getSong()
  },
  methods:{
    // 根据歌曲名模糊查询
    getSong(){
      getSongByN(this.$route.query.keywords).then(res =>{
        if (res.data.length){
          this.albumDatas = res.data
          this.$store.commit("setListOfSongs",res.data)
        }else {
          this.$message({
            type: "warning",
            message: "暂无符合条件的歌曲"
          })
        }
      }).catch(err =>{
        console.log(err)
      })
    },
  }
}
</script>
<style lang="scss" scoped>
@import "../../assets/css/search-songs";
</style>
