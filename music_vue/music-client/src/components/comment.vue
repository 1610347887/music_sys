<template>
  <div>
    <div class="comment">
      <h2>评论</h2>
      <div class="comment-msg">
        <div class="comment-img">
          <img :src="attachImageUrl(avator)"/>
        </div>
        <el-input class="comment-input" type="textarea" :rows="3" placeholder="评论一下吧" v-model="textarea"/>
      </div>
      <el-button type="primary" class="sub-btn" @click="postComment">评论</el-button>
    </div>
    <p>共{{commentList.length}}条评论</p><hr/>
    <ul class="popular" v-for="(item,index) in commentList" :key="index">
      <li>
        <div class="popular-img">
          <img :src="attachImageUrl(userPic[index])"/>
        </div>
        <div class="popular-msg">
          <ul>
            <li class="name">{{userName[index]}}</li>
            <li class="time">{{item.createTime}}</li>
            <li class="content">{{item.content}}</li>
          </ul>
        </div>
        <div class="up" ref="up" @click="postUp(item.id, item.up,index)">
          <svg class="icon">
            <use xlink:href="#icon-zan"/>
          </svg>
          {{item.up}}
        </div>
      </li>
    </ul>
  </div>
</template>
<script>
import {mixin} from "../mixins";
import {mapGetters} from "vuex"
import {submitComment, getCommentList, getConsumerInfo,likeComment} from "../api/index"
export default {
  name: 'comment',
  mixins: [mixin],
  props: [
    'playId',              // 歌手或歌单id
    'type',                // 0歌手，1歌单
  ],
  computed:{
    ...mapGetters([
      'loginIn',           // 用户是否已登录
      'userInfo',            // 用户id
      'avator',            // 用户头像
    ])
  },
  data(){
    return{
      textarea: '',        // 存放输入的评论内容
      commentInfo: {},     // 评论对象
      commentList: [],     // 存放评论列表
      userPic: [],         // 评论者的头像
      userName: [],        // 评论者的昵称
      consumerIdList: [],  // 用户id列表
    }
  },
  mounted() {
    this.getCommentLists()
    this.getCommentInfoById(1)
  },
  methods:{
    // 提交评论
    postComment(){
      this.commentInfo.consumerId = this.userInfo.id;
      this.commentInfo.type = this.type;
      if (this.type == 1){
        this.commentInfo.songListId = this.playId;
      }else {
        this.commentInfo.singerId = this.playId;
      }
      this.commentInfo.content = this.textarea;
      if (this.loginIn){
        submitComment(this.commentInfo)
          .then(res => {
            if (res.code == 200){
              this.$message({
                type: "success",
                message: "评论成功"
              })
              this.textarea = ''
              this.getCommentLists()
            }else {
              this.$message({
                type: "error",
                message: "评论失败"
              })
            }
          })
      }else {
        this.$message({
          type: "warning",
          message: "请先登录"
        })
      }
    },
    // 评论列表
    getCommentLists(){
     getCommentList(this.type,this.playId)
        .then(res => {
          this.commentList = res.data
          for (let item of res.data){
            this.consumerIdList.push(item.consumerId)
          }
          let _this = this
          _this.getCommentInfoById(1)
        })
    },
    //获取用户的头像和昵称
    async getCommentInfoById(id){                 // 这里使用到async+await，实现数据同步。
      for (let item of this.consumerIdList){
       await getConsumerInfo(item)
          .then(res => {
            this.userPic.push(res.data.avator)
            this.userName.push(res.data.username)
          })
      }

    },
    // 给评论点赞
    postUp(id,up,index){
      if (this.loginIn){
        this.commentInfo.id = id;
        this.commentInfo.up = up+1;
        likeComment(this.commentInfo)
          .then(res => {
            if (res.code == 200){
              this.$refs.up[index].children[0].style.color = '#2796cd';
              this.getCommentLists()
            }
          })
      }else {
        this.$message({
          type: "warning",
          message: "请先登录"
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import "../assets/css/comment";
</style>
