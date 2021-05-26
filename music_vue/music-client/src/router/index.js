import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'
import songListPage from "../pages/songListPage";
import singerPage from "../pages/singerPage";
import myMusicPage from "../pages/myMusicPage";
import searchPage from "../pages/searchPage"
import lyric from "../pages/lyric";
import signup from "../pages/signup";
import login from "../pages/login";
import userInfo from "../pages/userInfo"
import singerAlbum from "../pages/singerAlbum"
import songListAlbum from "../pages/songListAlbum"

Vue.use(Router)

export default new Router({
  base: '/user/',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/song-list',
      name: 'song-list',
      component: songListPage
    },
    {
      path: '/singer',
      name: 'singer',
      component: singerPage
    },
    {
      path: '/my-music',
      name: 'my-music',
      component: myMusicPage
    },
    {
      path: '/search',
      name: 'search',
      component: searchPage
    },
    {
      path: '/lyric',
      name: 'lyric',
      component: lyric
    },
    {
      path: '/signup',
      name: 'signup',
      component: signup
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/userInfo',
      name: 'userInfo',
      component: userInfo
    },
    {
      path: '/singer-album/:id',
      name: 'singer-album',
      component: singerAlbum
    },
    {
      path: '/song-list-album/:id',
      name: 'song-list-album',
      component: songListAlbum
    },
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
// 解决重复点击相同路由报错问题
const VueRouterPush = Router.prototype.push
Router.prototype.push = function push(to){
  return VueRouterPush.call(this,to).catch(err => err)
}
