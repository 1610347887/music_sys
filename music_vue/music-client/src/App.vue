<template>
  <div id="app">
    <the-header/>
    <router-view class="music-content"/>
    <song-audio/>
    <the-aside/>
    <play-bar/>
    <scroll-top/>
  </div>
</template>

<script>

import TheHeader from "./components/theHeader";
import ScrollTop from "./components/scrollTop";
import SongAudio from './components/songAudio'
import PlayBar from "./components/playBar";
import TheAside from "./components/theAside";
export default {
  name: 'App',
  components: {
    TheAside,
    PlayBar,
    ScrollTop,
    TheHeader,
    SongAudio
  }
}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>
