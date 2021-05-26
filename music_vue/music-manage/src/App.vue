<template>
  <div id="app">
    <song-audio/>
    <router-view></router-view>
  </div>
</template>

<script>
import songAudio from "./components/songAudio";
export default {
  name: 'App',
  components:{
    songAudio
  }
}
</script>

<style>
</style>
