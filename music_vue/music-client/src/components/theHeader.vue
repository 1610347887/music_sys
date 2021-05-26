<template>
  <div class="the-header">
    <div class="header-logo" @click="goHome">
      <svg class="icon">
        <use xlink:href="#icon-erji"/>
      </svg>
      <span>Music</span>
    </div>
    <ul class="navbar">
      <li :class="{active: item.name == activeName}" v-for="item in navMsg" :key="item.path" @click="goPage(item.path,item.name)">
        {{item.name}}
      </li>
      <li>
        <div class="header-search">
          <input type="text" placeholder="搜索" @keyup.enter="goSearch()" v-model.trim="keywords"/>
          <div class="search-btn" @click="goSearch()" v-model.trim="keywords">
            <svg class="icon">
              <use xlink:href="#icon-sousuo"/>
            </svg>
          </div>
        </div>
      </li>
      <li v-show="!loginIn" :class="{active: item.name == activeName}" v-for="item in loginMsg" :key="item.path" @click="goPage(item.path,item.name)">
        {{item.name}}
      </li>
    </ul>
    <div class="header-right" v-show="loginIn">
      <div id="user">
        <img :src="attachImageUrl(avator)"/>
      </div>
      <ul class="menu">
        <li v-for="(item,index) in munuList" :key="index" @click="goMunuList(item.path)">{{item.name}}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import {mixin} from "../mixins";
import {navMsg, loginMsg, munuList} from '../assets/data/header'
export default {
  name: 'the-header',
  mixins: [mixin],
  data(){
    return{
      navMsg: [],   // 左侧导航栏
      keywords: '', // 搜索关键字
      loginMsg: [], // 右侧导航栏
      munuList: [], // 用户下拉菜单
    }
  },
  computed:{
    ...mapGetters([
      'activeName',
      'loginIn',
      'avator'
    ])
  },
  created() {
    this.navMsg = navMsg
    this.loginMsg = loginMsg
    this.munuList = munuList
    this.keywords = this.$route.query.keywords
  },
  mounted() {
    document.querySelector('#user').addEventListener('click',function (e){
      document.querySelector('.menu').classList.add('show')
      e.stopPropagation();            // 阻止时间冒泡
    },false);
    // 点击菜单内部时，阻止时间冒泡，这样，点击菜单内部时不会关闭菜单
    document.querySelector('.menu').addEventListener('click',function (e){
      e.stopPropagation();
    },false);
    document.addEventListener('click',function (){
      document.querySelector('.menu').classList.remove('show')
    },false)
  },
  methods:{
    goHome(){
      this.$router.push({path:'/'});
    },
    goPage(path, name){
      if (!this.loginIn && name == "我的音乐"){
        this.$message({
          type: 'warning',
          message: '请先登录'
        })
        this.$store.commit('setActiveName','登录')
        this.$router.push(({path: "/login"}))
      }else {
        this.$store.commit('setActiveName',name)
        this.$router.push(({path: path}));
      }
    },
    goSearch(){
      if (this.keywords!=''){
        this.$router.push({path: '/search', query: {keywords: this.keywords}})
        window.location.reload()
      }
    },
    goMunuList(path){
      if (path == 0){
        this.$store.commit('setIsCollect',false)
        this.$store.commit('setLoginIn',false)
        this.$router.push({path: '/'})
      }else {
        this.$router.push({path: path})
      }
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/the-header.scss";
</style>
