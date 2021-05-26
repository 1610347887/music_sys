<template>
  <div class="table">
    <div class="songInfo">
      <i class="el-icon-tickets">评论列表</i>
    </div>
    <el-table size="mini"
              border style="width: 100%"
              :header-cell-style="{'text-align':'center'}"
              :cell-style="{'text-align':'center'}"
              :data="commentObj">
      <el-table-column label="序号" type="index" width="200"/>
      <el-table-column prop="name" label="用户名"/>
      <el-table-column prop="content" label="评论内容"/>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" icon="el-icon-delete" name="删除" @click="deleteCommentById(scope.row.id)"/>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import '@/assets/js/iconfont.js'
import {getCommentList, getConsumerInfo, deleteComment} from '../api'
import {mixin} from "../mixins";

export default {
  mixins: [mixin],
  data(){
    return{
      songListId: '',               // 用户id
      type: '',                     // 歌单或歌手,0歌手，1歌单
      commentObj: [],               // 评论对象
      userIdList: [],           // 用户id集合
      consumerList: [],             // 用户集合
    }
  },
  created() {
    this.songListId = this.$route.query.id          // 接收歌单id
    this.type = this.$route.query.type                      // 接收类型
    this.getSongListComment()
  },
  methods:{
    // 查询某个歌单的评论列表
    getSongListComment(){
      getCommentList(this.type,this.songListId)
        .then(res => {
          for (let item of res.data){
            this.userIdList.push(item.consumerId)
          }
          this.getConsumerName(res.data)
        })
    },
    // 获取用户名字
    async getConsumerName(comment){
      for (let item of this.userIdList){
        await getConsumerInfo(item).then(res => {
          this.consumerList.push(res.data.username)
        })
      }
      let i = 0
      for (let item of comment){
        item.name = this.consumerList[i++]
      }
      this.commentObj = comment
    },
    // 删除歌单评论
    deleteCommentById(id){
      this.$confirm('此操作将永久删除该收藏, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(()=>{
        deleteComment(id)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "删除成功"
              })
              this.getSongListComment()
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
