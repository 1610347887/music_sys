<template>
  <div class="singer">
    <ul class="singer-header">
      <li v-for="(item,index) in singerType" :key="index"
          @click="handleChangeView(item)"
          :class="{active:item.name == activityName}">
        {{item.name}}
      </li>
      <li>
        <el-input type="text" placeholder="搜索歌手"
                  v-model.trim="keywords"
                  @keyup.enter.native="getSingerListByN(keywords)"/>
      </li>
      <li style="margin-left: -30px">
        <el-button type="primary" icon="el-icon-search"
                   @click="getSingerListByN(keywords)">搜索</el-button>
      </li>
    </ul>
    <content-list :content-list="albumDatas"/>
  </div>
</template>
<script>
import ContentList from "../components/contentList";
import {getAllSinger, getSingerBySex, getSingerByName} from "../api/index"
import {singerType} from "../assets/data/singerType";

export default {
  name: 'singer',
  components: {
    ContentList
  },
  data(){
    return{
      albumDatas: [],           // 歌单数据
      singerType: [],           // 歌手类型
      activityName: '全部歌手',  // 当前活跃风格
      keywords: '',             // 模糊查询歌手关键字
    }
  },
  watch:{
    keywords(){
      this.getSingerListByN(this.keywords)
    }
  },
  mounted() {
    this.singerType = singerType
    this.getSinger()
  },
  methods:{
    getSinger(){
      getAllSinger()
        .then(res => {
          if (res.data.length){
            this.albumDatas = res.data
          }else {
            this.$message({
              type: "warning",
              message: "暂无歌手"
            })
          }
        })
    },
    // 根据歌手名字模糊查询歌手列表
    getSingerListByN(name){
      if (name != ''){
        getSingerByName(name)
          .then(res => {
            this.albumDatas = res.data
          })
      }else {
        this.getSinger()
      }
    },
    // 根据style显示对应的歌单
    handleChangeView(item){
      this.activityName = item.name
      if (item.type == -1){
        this.getSinger()
      }else {
        this.getSingerByType(item.type)
      }
    },
    // 根据style查询歌单
    getSingerByType(type){
      getSingerBySex(type)
        .then(res => {
          this.albumDatas = res.data
        })
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/singer";
</style>
