const song = {
  state: {
    listOfSongs: [],    // 当前歌曲列表
    isPlay: false,         // 是否播放中
    url: '',               // 歌曲地址
    id: '',                // 歌曲id
    duration: 0,           // 音乐时长
    curTime: 0,            // 当前音乐的播放位置
    changeTime: 0,         // 指定播放时刻
    title: '',             // 歌名
    artist: '',            // 歌手名
    picUrl: '',            // 歌曲图片
    autoNext: true,        // 是否触发自动播放下一首
    lyric: [],             // 未处理的歌词数据
    tempList: {},          // 单个歌单信息或歌手中的歌单信息
    listIndex: 0,          // 当前歌曲在歌单中的位置
    volume: 50,            // 音量
    playButtonUrl: '#icon-bofang'      // 播放状态的图标
  },
  getters: {
    listOfSongs: state => {
      let listOfSongs = state.listOfSongs
      if (!listOfSongs.length) {
        listOfSongs = JSON.parse(window.sessionStorage.getItem('listOfSongs'))
      }
      return listOfSongs
    },
    isPlay: state => state.isPlay,
    url: state => state.url,
    id: state => state.id,
    duration: state => state.duration,
    curTime: state => state.curTime,
    changeTime: state => state.changeTime,
    title: state => state.title,
    artist: state => state.artist,
    picUrl: state => state.picUrl,
    autoNext: state => state.autoNext,
    lyric: state => state.lyric,
    tempList: state => state.tempList,
    listIndex: state => state.listIndex,
    volume: state => state.volume,
    playButtonUrl: state => state.playButtonUrl,
  },
  mutations: {
    setListOfSongs: (state, listOfSongs) => {
      state.listOfSongs = listOfSongs
      window.sessionStorage.setItem('listOfSongs', JSON.stringify(listOfSongs))
    },
    setIsPlay: (state,isPlay) => {state.isPlay = isPlay},
    setUrl: (state,url) => {state.url = url},
    setId: (state,id) => {state.id = id},
    setDuration: (state,duration) => {state.duration = duration},
    setCurTime: (state,curTime) => {state.curTime = curTime},
    setChangeTime: (state,changeTime) => {state.changeTime = changeTime},
    setTitle: (state,title) => {state.title = title},
    setArtist: (state,artist) => {state.artist = artist},
    setPicUrl: (state,picUrl) => {state.picUrl = picUrl},
    setAutoNext: (state,autoNext) => {state.autoNext = autoNext},
    setLyric: (state,lyric) => {state.lyric = lyric},
    setTempList: (state,tempList) => {state.tempList = tempList},
    setListIndex: (state,listIndex) => {state.listIndex = listIndex},
    setVolume: (state,volume) => {state.volume = volume},
    setPlayButtonUrl: (state,playButtonUrl) => {state.playButtonUrl = playButtonUrl},
  }
}
export default song
