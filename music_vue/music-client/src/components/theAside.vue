<template>
  <transition name="slide-fade">
    <div class="the-aside" v-if="showAside">
      <h3 class="title">播放列表</h3>
      <ul class="menus">
        <li v-for="(item,index) in listOfSongs" :key="index"
            :class="{'is-play': id == item.id}"
            @click="toplay(item.id,item.url,item.pic,index,item.name,item.lyric)">
          {{item.name}}
        </li>
      </ul>
    </div>
  </transition>
</template>

<script>
import {mapGetters} from 'vuex'
import {mixin} from "../mixins";

export default {
  name: 'the-aside',
  mixins: [mixin],
  computed:{
    ...mapGetters([
      'showAside',     // 是否显示待播放歌曲列表
      'listOfSongs',   // 当前播放列表歌单
      'id',            // 播放中的音乐id
    ])
  },
}
</script>

<style lang="scss" scoped>
@import "../assets/css/the-aside";
</style>
