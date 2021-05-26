<template>
  <div class="signUp">
    <div class="signUp-head">
      <span>用户注册</span>
    </div>
    <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
      <el-form-item prop="username" label="用户名">
        <el-input v-model="registerForm.username" placeholder="用户名"/>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input type="password" v-model="registerForm.password" placeholder="密码"/>
      </el-form-item>
      <el-form-item prop="sex" label="性别">
        <el-radio-group v-model="registerForm.sex">
          <el-radio :label="0">女</el-radio>
          <el-radio :label="1">男</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="phoneNum" label="手机号">
        <el-input v-model="registerForm.phoneNum" placeholder="手机号"/>
      </el-form-item>
      <el-form-item prop="email" label="邮箱">
        <el-input v-model="registerForm.email" placeholder="邮箱"/>
      </el-form-item>
      <el-form-item prop="birth" label="生日">
        <el-date-picker v-model="registerForm.birth" placeholder="选择日期" style="width: 100%"/>
      </el-form-item>
      <el-form-item prop="introduction" label="个性签名">
        <el-input v-model="registerForm.introduction" placeholder="个性签名"/>
      </el-form-item>
      <el-form-item prop="location" label="地区">
        <el-select v-model="registerForm.location" placeholder="选择地区" style="width: 100%">
          <el-option v-for="item in cities" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>
      <div class="login-btn">
        <el-button type="primary" @click="SignUp">注册</el-button>
        <el-button @click="goBack(-1)">取消</el-button>
      </div>
    </el-form>
  </div>
</template>
<script>
import {rules, cities} from "../assets/data/form";
import {signUp} from "../api/index";

export default {
  name: 'signup',
  data(){
    return{
      registerForm: {},     // 用户对象
      cities: [],           // 地区集合--省
      rules: {}             // 表单提交规则
    }
  },
  created() {
    this.rules = rules
    this.cities = cities
  },
  methods:{
    SignUp(){
      let _this = this
      signUp(this.registerForm).then(res => {
        if (res.code == 200){
          this.$message({
            type: "success",
            message: '注册成功'
          })
          _this.$store.commit('setActiveName','首页')
          setTimeout(function (){
            _this.$router.push({path: '/'})
          },2000)
        }else {
          this.$message({
            type: "error",
            message: '用户已存在'
          })
        }
      }).catch(err => {
        this.$message({
          type: "error",
          message: '注册失败'
        })
      })
    },
    goBack(index){
      this.$store.commit('setActiveName','登录')
      this.$router.go(index)
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/sign-up";
</style>
