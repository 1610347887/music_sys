import login from "../pages/login";

const user = {
  state: {
    userId: '',          // 用户id
    username: '',        // 用户账号
    avator: '',          // 用户头像
    userInfo: {},        // 用户信息
  },
  getters: {
    userInfo: state => {
      let userInfo = state.userInfo
      if (userInfo!=null){
        userInfo = JSON.parse(window.sessionStorage.getItem('userInfo'))
      }
      return userInfo
    },
    userId: state => {
      let userId = state.userId
      if (!userId){
        userId = JSON.parse(window.sessionStorage.getItem('userId'))
      }
      return userId
    },
    username: state => {
      let username = state.username
      if (!username){
        username = JSON.parse(window.sessionStorage.getItem('username'))
      }
      return username
    },
    avator: state => {
      let avator = state.avator
      if (!avator){
        avator = JSON.parse(window.sessionStorage.getItem('avator'))
      }
      return avator
    },
  },
  mutations: {
    setUserInfo: (state, userInfo) => {
      state.userInfo = userInfo
      window.sessionStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    setUserId: (state, userId) => {
      state.userId = userId
      window.sessionStorage.setItem('userId', JSON.stringify(userId))
    },
    setUsername: (state, username) => {
      state.username = username
      window.sessionStorage.setItem('username', JSON.stringify(username))
    },
    setAvator: (state, avator) => {
      state.avator = avator
      window.sessionStorage.setItem('avator', JSON.stringify(avator))
    },
  }
}
export default user
