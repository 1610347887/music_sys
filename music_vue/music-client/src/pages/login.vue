<template>
  <div class="login">
    <div class="login-head">
      <span>账号登录</span>
    </div>
    <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules"  @keyup.enter.native="login()">
      <el-form-item prop="username" label="用户名">
        <el-input v-model="registerForm.username" placeholder="用户名"/>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input type="password" v-model="registerForm.password" placeholder="密码" />
      </el-form-item>
      <div class="login-btn">
        <el-button type="primary" @click="login">登录</el-button>
      </div><br/>
      <div>
        <a class="register" @click="goSignUp">还没账号？点击注册</a>
      </div>
    </el-form>
  </div>
</template>
<script>
import {mixin} from "../mixins";
import {rules} from "../assets/data/form";
import {loginIn} from "../api/index";

export default {
  name: 'login',
  mixins: [mixin],
  data(){
    return{
      registerForm: {},     // 用户对象
      rules: {}             // 表单提交规则
    }
  },
  created() {
    this.rules = rules
  },
  methods:{
    login(){
      let _this = this
      loginIn(this.registerForm).then(res => {
        if (res.code == 200){
          this.$message({
            type: "success",
            message: '登录成功'
          })
          _this.$store.commit('setLoginIn', true)
          _this.$store.commit('setUserInfo',res.data);
          _this.$store.commit('setUserId',res.data.id)
          _this.$store.commit('setAvator',res.data.avator)
          // _this.$store.commit('setUsername',res.data.username)
          _this.$store.commit('setActiveName','首页')
          setTimeout(function (){
            _this.$router.push({path: '/'})
          },2000)
        }else {
          this.$message({
            type: "error",
            message: '用户名或密码错误'
          })
        }
      }).catch(err => {
        this.$message({
          type: "error",
          message: '登录失败'
        })
      })
    },
    goSignUp(){
      this.changeIndex('注册')
      this.$router.push({path: '/signup'})
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/login-in";
.register{
  float: right;
  color: blue;
}
</style>
