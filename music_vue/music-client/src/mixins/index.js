import {mapGetters} from "vuex";
import {getSongByN, getCollectByConsumerId} from "../api/index";
import fa from "element-ui/src/locale/lang/fa";
import img from '../assets/img/user.jpg'

export const mixin = {
  computed:{
    ...mapGetters([
      'userId',               // 用户id
      'id',                   // 歌曲id
    ])
  },
  methods: {
    // 获取图片地址
    attachImageUrl(srcUrl){
      return srcUrl? this.$store.state.configure.HOST+srcUrl : img
    },
    // 获取性别
    attachSex(value){
      if (value == 0){
        return "女"
      }else {
        return "男"
      }
    },
    // 根据歌曲名模糊查询
    getSong(){
      getSongByN(this.$route.query.keywords).then(res =>{
        if (res.data.length){
          this.$store.commit('setListOfSongs',res.data)
        }else {
          this.$message({
            type: "warning",
            message: "暂无歌曲"
          })
        }
      }).catch(err =>{
        console.log(err)
      })
    },
    // 改变菜单栏名称
    changeIndex(value){
      this.$store.commit('setActiveName',value)
    },
    // 获取名字前半部分--歌手名
    replaceLName(str){
      if (str){
        let arr = str.split('-')
        return arr[0]
      }
    },
    // 获取名字后半部分--歌名
    replaceRName(str){
      if (str){
        let arr = str.split('-')
        return arr[1]
      }
    },
    // 播放
    toplay: function (id,url,pic,index,name,lyric){
      console.log(111111111111111111111111)
      if (id != this.$store.state.song.id){
        this.$store.commit('setIsPlay',false)
      }
      this.$store.commit('setIsPlay',!this.$store.state.song.isPlay)
      this.$store.commit('setId',id);
      this.$store.commit('setUrl',this.$store.state.configure.HOST+url)
      this.$store.commit('setPicUrl',this.$store.state.configure.HOST+pic)
      this.$store.commit('setListIndex',index)
      this.$store.commit('setTitle',this.replaceRName(name))
      this.$store.commit('setArtist',this.replaceLName(name))
      this.$store.commit('setLyric',this.parseLyric(lyric))
      if (this.$store.state.song.isPlay){
        this.$store.commit('setPlayButtonUrl','#icon-zanting')
      }else {
        this.$store.commit('setPlayButtonUrl','#icon-bofang')
      }
      this.$store.commit('setIsCollect',false)
      // 查询某个用户的收藏列表
      getCollectByConsumerId(this.userId)
        .then(res => {
          for (let item of res.data){
            if (item.songId == this.id){
              this.$store.commit('setIsCollect',true);
              break;
            }
          }
        })
    },
    // 解析歌词
    parseLyric(text){
      let lines = text.split("\n");
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result = [];
      for (let item of lines){
        let time = item.match(pattern);       // 存前面的时间段
        let value = item.replace(pattern,''); // 存后面的歌词
        for (let item1 of time){
          let t = item1.slice(1,-1).split(':');    // 取出时间，换算成数组
          if (value!=''){
            result.push([parseInt(t[0],10) * 60 + parseInt(t[1]),value]);
          }
        }
      };
      // 按照第一个元素--时间--排序
      result.sort(function (a,b){
        return a[0] - b[0];
      });
      return result;
    },
    // 解析时间
    formatSeconds(value){
      let theTime = parseInt(value)
      let result = ''                               // 返回值
      let minute = parseInt((theTime / 60) % 60) // 分钟
      let second = parseInt(theTime % 60)        // 秒
      if(minute > 0){
        if (minute < 10){
          result += "0" + minute + ":"
        }else {
          result += minute + ":"
        }
      }else {
        result += "00:"
      }
      if(second > 0){
        if (second < 10){
          result += "0" + second
        }else {
          result += second
        }
      }else {
        result += "00"
      }
      return result
    },
  }
}
