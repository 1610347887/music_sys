<template>
  <div>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="num-size" style="color: #30a4fc">{{consumerCount}}</div>
              <div>用户数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="num-size" style="color: yellowgreen">{{singerCount}}</div>
              <div>歌手数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="num-size" style="color: green">{{songCount}}</div>
              <div>歌曲总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <div class="grid-content">
            <div class="grid-cont-center">
              <div class="num-size" style="color: #ca1aff">{{songListCount}}</div>
              <div>歌单数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="12">
        <h3 class="mgb20">用户性别比例</h3>
        <div style="background-color: white">
          <ve-pie :data="consumerSex" :theme="options"></ve-pie>
        </div>
      </el-col>
      <el-col :span="12">
        <h3 class="mgb20">歌手类别</h3>
        <div style="background-color: white">
            <ve-histogram :data="singerCategory"></ve-histogram>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getAllConsumer, getSongList, getSongtotal, getAllSinger} from '../api/index'
export default {
  data(){
    return{
      consumerCount: 0,       // 用户总数
      songCount: 0,           // 歌曲总数
      singerCount: 0,         // 歌手数量
      songListCount: 0,       // 歌单数量
      consumer:[],            //用户
      current: 1,
      limit: 1,
      findObj:{},
      consumerSex: {          // 按性别分类
        columns: ['性别', '总数'],
        rows: [
          {'性别':'男','总数':0},
          {'性别':'女','总数':0}
        ]
      },
      options:{
        color: ['#87cefa','#ffc0cb']
      },
      singerCategory: {          // 按性别分类
        columns: ['类别', '总数'],
        rows: [
          {'类别':'男','总数':0},
          {'类别':'女','总数':0},
          {'类别':'组合','总数':0}
        ]
      },
    }
  },
  created() {
    this.getConsumerCount()
    this.getSingerCount()
    this.getSongCount()
    this.getSongListCount()
  },
  methods:{
    // 获取用户数量
    getConsumerCount(){
      getAllConsumer()
        .then(res =>{
          this.consumerCount = res.data.length
          this.consumer = res.data
          this.consumerSex.rows[0]['总数'] = this.setSex(1,this.consumer)
          this.consumerSex.rows[1]['总数'] = this.setSex(0,this.consumer)
        })
    },
    // 根据性别获取用户数
    setSex(sex, consumer){
      let count = 0;
      for (let item of consumer){
        if (sex == item.sex){
          count++
        }
      };
      return count;
    },
    // 获取歌手数量
    getSingerCount(){
      getAllSinger()
        .then(res => {
          this.singerCount = res.data.length
          this.singerCategory.rows[0]['总数'] = this.setSex(1,res.data)
          this.singerCategory.rows[1]['总数'] = this.setSex(0,res.data)
          this.singerCategory.rows[2]['总数'] = this.setSex(2,res.data)
        })
    },
    // 获取歌单数量
    getSongListCount(){
      getSongList(this.current,this.limit,this.findObj)
        .then(res => {
          this.songListCount = res.data.total
        })
    },
    // 获取歌曲数量
    getSongCount(){
      getSongtotal()
        .then(res => {
          this.songCount = res.data
        })
    }
  }
}
</script>

<style scoped>
.grid-content{
  display: flex;
  align-items: center;
  height: 50px;
}
.grid-cont-center{
  flex: 1;
  text-align: center;
  font-size: 14px;
}
.num-size{
  font-size: 40px;
  margin-top: 10px;
}
</style>
