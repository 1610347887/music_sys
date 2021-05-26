import img from '../assets/img/def.jpg'
export const mixin = {
  methods: {
    // 提示信息
    notify (title, type) {
      this.$notify({
        title: title,
        type: type
      })
    },
    // 根据相对地址获取绝对地址
    getUrl(url){
      return url? `${this.$store.state.HOST}/${url}`:img
    },
    // 性别转换
    changeSex(value){
      if (value == 0){
        return '女'
      }
      if (value == 1){
        return '男'
      }
      if (value == 2){
        return '组合'
      }
      return value;
    }
  }
}
