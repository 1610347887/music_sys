import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: resolve => require(['../pages/Login.vue'], resolve)
    },
    {
      path: '/home',
      component: resolve => require(['../components/home'], resolve),
      children:[
        {
          path: '/info',
          component: resolve => require(['../pages/infoPage'], resolve)
        },
        {
          path: '/consumer',
          component: resolve => require(['../pages/consumerPage'], resolve)
        },
        {
          path: '/singer',
          component: resolve => require(['../pages/singerPage'], resolve)
        },
        {
          path: '/songList',
          component: resolve => require(['../pages/songListPage'], resolve)
        },
        {
          path: '/song',
          component: resolve => require(['../pages/songPage'], resolve)
        },
        {
          path: '/listSong',
          component: resolve => require(['../pages/listSongPage'],resolve)
        },
        {
          path: '/collect',
          component: resolve => require(['../pages/collectPage'],resolve)
        },
        {
          path: '/comment',
          component: resolve => require(['../pages/commentPage'],resolve)
        }
      ]
    },
  ]
})
