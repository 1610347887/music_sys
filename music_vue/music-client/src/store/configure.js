const configure = {
  state: {
    // HOST: 'http://127.0.0.1:8888',          // 本地地址
    HOST: 'http://192.168.18.22:8888',         // 服务器地址
    activeName: '',          // 当前选中的菜单名
    showAside: false,        // 是否显示待播放歌曲列表
    loginIn: false,          // 用户是否已登录
    isCollect: false,        // 收藏状态
  },
  getters: {
    activeName: state => {
      let activeName = state.activeName
      if (!activeName) {
        activeName = JSON.parse(window.sessionStorage.getItem('activeName'))
      }
      return activeName
    },
    showAside: state => {
      let showAside = state.showAside
      if (!showAside) {
        showAside = JSON.parse(window.sessionStorage.getItem('showAside'))
      }
      return showAside
    },
    loginIn: state => {
      let loginIn = state.loginIn
      if (!loginIn){
        loginIn = JSON.parse(window.sessionStorage.getItem('loginIn'))
      }
      return loginIn
    },
    isCollect: state => {
      let isCollect = state.isCollect
      if (!isCollect){
        isCollect = JSON.parse(window.sessionStorage.getItem('isCollect'))
      }
      return isCollect
    }

  },
  mutations: {
    setActiveName: (state, activeName) => {
      state.activeName = activeName
      window.sessionStorage.setItem('activeName', JSON.stringify(activeName))
    },
    setShowAside: (state, showAside) => {
      state.showAside = showAside
      window.sessionStorage.setItem('showAside', JSON.stringify(showAside))
    },
    setLoginIn: (state,loginIn) => {
      state.loginIn = loginIn
      window.sessionStorage.setItem('loginIn',JSON.stringify(loginIn))
    },
    setIsCollect: (state,isCollect) => {
      state.isCollect = isCollect
      window.sessionStorage.setItem('isCollect',JSON.stringify(isCollect))
    }
  }
}
export default configure
