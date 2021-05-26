<template>
  <div class="header">
<!--    折叠图片-->
    <div class="collapse-btn" @click="collapseChange">
      <i class="el-icon-menu"/>
    </div>
    <div class="logo">music后台管理</div>
    <div class="header-right">
      <div class="btn-fullscreen" @click="handleFullscreen">
        <el-tooltip :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
          <i class="el-icon-rank"/>
        </el-tooltip>
      </div>
      <div class="user-avator">
        <img src="../assets/img/user.jpg"/>
      </div>
      <el-dropdown class="user-name" trigger="click" @command="hadleCommand">
        <span class="el-dropdown-link">
          {{username}}
          <i class="el-icon-caret-bottom"/>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

    </div>
  </div>
</template>
<script>
import bus from '../assets/js/bus'
import SingerPage from "../pages/singerPage";
export default {
  components: {SingerPage},
  data(){
    return{
      collapse: false,
      fullscreen: false
    }
  },
  computed: {
    username(){
      return localStorage.getItem('username')
    }
  },
  methods:{
    // 侧边栏折叠
    collapseChange(){
      this.collapse = !this.collapse
      bus.$emit('collapse',this.collapse)
    },
    // 全屏
    handleFullscreen(){
      if (this.fullscreen){
        if (document.exitFullscreen){
          document.exitFullscreen()
        }else if (document.webkitCancelFullScreen){       // safari  chrome
          document.webkitCancelFullScreen()
        }else if (document.mozCancelFullScreen){          // firefox
          document.mozCancelFullScreen()
        }else if (document.msExitFullScreen){             // ie
          document.msExitFullScreen()
        }
      }else {
        let element = document.documentElement
        if (element.requestFullscreen){
          element.requestFullscreen()
        }else if (element.webkitRequestFullScreen){
          element.webkitRequestFullScreen()
        }else if (element.mozRequestFullScreen){
          element.mozRequestFullScreen()
        }else if (element.msRequestFullScreen){
          element.msRequestFullScreen()
        }
      }
      this.fullscreen = !this.fullscreen
    },
    hadleCommand(command){
      if (command == 'logout'){
        localStorage.removeItem('username')
        this.$router.push('/')
      }
    }
  }
}
</script>
<style scoped>
.header{
  position: relative;
  /*background-color: #253041;*/
  background-color: #334256;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: #ffffff;
}
.collapse-btn{
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 70px;
}
.header .logo{
  float: left;
  line-height: 70px;
}
.header-right{
  float: right;
  padding-right: 50px;
  display: flex;
  height: 70px;
  align-items: center;
}
.btn-fullscreen{
  transform: rotate(45deg);
  margin-right: 5px;
  font-size: 24px;
}
.user-avator{
  margin-left: 20px;
}
.user-avator img{
  display: block;
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.user-name{
  margin-left: 10px;
}
.el-dropdown-link{
  color: #ffffff;
  cursor: pointer;
}
</style>
