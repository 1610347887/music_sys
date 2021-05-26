<template>
  <div class="setting">
    <div class="userInfo-head">
      <span>个人信息</span>
    </div>
    <span>头像</span>
    <el-upload :action="uploadUrl(userInfo.id)"
               :before-upload="beforeAvatarUpload"
               :on-success="handleAvatarSuccess"
               :show-file-list="false">
      <div class="avator-img">
        <img :src="attachImageUrl(avator)" style="width: 100%" title="点击更换头像">
      </div>
    </el-upload>
    <el-form :model="userForm" ref="userInfo" label-width="70px" :rules="rules" style="margin-left: -30px">
      <el-form-item prop="username" label="昵称">
        <el-input v-model="userForm.username" placeholder="昵称"/>
      </el-form-item>
      <el-form-item prop="sex" label="性别">
        <el-radio-group v-model="userForm.sex">
          <el-radio :label="0">女</el-radio>
          <el-radio :label="1">男</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="phoneNum" label="手机号">
        <el-input v-model="userForm.phoneNum" placeholder="手机号"/>
      </el-form-item>
      <el-form-item prop="email" label="邮箱">
        <el-input v-model="userForm.email" placeholder="邮箱"/>
      </el-form-item>
      <el-form-item prop="birth" label="生日">
        <el-date-picker v-model="userForm.birth" placeholder="选择日期" style="width: 100%"/>
      </el-form-item>
      <el-form-item prop="introduction" label="个性签名">
        <el-input v-model="userForm.introduction" placeholder="个性签名"/>
      </el-form-item>
      <el-form-item prop="location" label="地区">
        <el-select v-model="userForm.location" placeholder="选择地区" style="width: 100%">
          <el-option v-for="item in cities" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <div class="login-btn">
        <el-button type="primary" @click="saveUserInfo">保存</el-button>
      </div>
    </el-form>
  </div>
</template>
<script>
import {mapGetters} from 'vuex';
import {mixin} from "../mixins";
import {getConsumerInfo, updateConsumer} from "../api";
import {rules, cities} from "../assets/data/form";

export default {
  name: 'user-info',
  mixins: [mixin],
  computed:{
    ...mapGetters([
      'userInfo',
      'avator',            // 用户头像单独处理
    ])
  },
  created() {
    this.userForm = this.userInfo
    this.rules = rules
    this.cities = cities
    this.userAvator = this.avator
  },
  data(){
    return{
      userForm: {},         // 用户信息对象
      cities: [],           // 地区集合--省
      rules: {},            // 表单提交规则
    }
  },
  methods:{
    saveUserInfo(){
      updateConsumer(this.userForm)
        .then(res => {
          if (res.code==200){
            this.$message({
              type: 'success',
              message: '保存成功'
            })
            console.log(this.userForm)
            this.$store.commit('setUserInfo',this.userForm)
          }
        })
    },
    // 上传图片之前
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 jpg 或 png 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    // 上传图片
    uploadUrl(id){
      return `${this.$store.state.configure.HOST}/music_sys/consumer/updateConsumerAvator/${id}`
    },
    // 上传之后
    handleAvatarSuccess(res, file) {
      if (res.code == 200){
        this.$message({
          type: 'success',
          message: '更换成功!'
        })
        this.getUserInfo()
        // this.$store.commit('setUserInfo',this.userForm)
      }else {
        this.$message({
          type: 'error',
          message: '更换失败!'
        })
      }
    },
    // 根据用户id获取用户头像
    getUserInfo(){
      getConsumerInfo(this.userInfo.id)
        .then(res => {
          this.$store.commit('setAvator',res.data.avator)
        })
    },
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/setting";
</style>
