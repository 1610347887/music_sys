<template>
  <div>
    <the-header/>
    <the-aside/>
    <div class="content-box" :class="{'content-collapse':collapse}">
      <router-view/>
    </div>
  </div>
</template>

<script>
import theHeader from './theHeader'
import theAside from './theAside'
import bus from "../assets/js/bus";
export default {
  components:{
    theHeader,
    theAside
  },
  data(){
    return{
      collapse: false
    }
  },
  created() {
    // 通过bus进行组件间的通信来折叠侧边栏
    bus.$on('collapse',msg=>{
      this.collapse = msg
    })
  }
}
</script>
