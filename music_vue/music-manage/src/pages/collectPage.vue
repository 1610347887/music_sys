<template>
  <div class="table">
    <div class="songInfo">
      <i class="el-icon-tickets">收藏列表</i>
    </div>
    <el-table size="mini"
              border style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              :data="songList">
      <el-table-column label="序号" type="index" width="200"/>
      <el-table-column prop="name" label="歌手-歌名"/>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" icon="el-icon-delete" name="删除" @click="deleteSongById(scope.row.id)"/>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import '@/assets/js/iconfont.js'
import {getCollectByConsumerId, deleteCollect, getSongById} from '../api'
import {mixin} from "../mixins";

export default {
  mixins: [mixin],
  data(){
    return{
      consumerId: '',               // 用户id
      songList: [],                 // 歌曲集合
      listId: [],                   // 歌曲id集合
    }
  },
  created() {
    this.consumerId = this.$route.query.id          // 接收用户id
    this.getCollectSong()
  },
  methods:{
    // 查询某个用户的收藏歌曲id
    getCollectSong(){
      let _this = this
      getCollectByConsumerId(this.consumerId)
        .then(res => {
          for (let item of res.data){
            this.listId.push(item.songId);
          }
          _this.getSongList();
        })
    },
    // 获取歌曲集合
    async getSongList(){
      for (let item of this.listId){
        await getSongById(item).then(res => {
          this.songList.push(res.data)
        })
      }
    },
    // 删除收藏的歌曲
    deleteSongById(id){
      this.$confirm('此操作将永久删除该收藏, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        deleteCollect(id,this.consumerId)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.listId = []
              this.songList = []
              this.getCollectSong()
            }else {
              this.$message({
                type: "error",
                message: "删除失败"
              })
            }
          })
      })
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
