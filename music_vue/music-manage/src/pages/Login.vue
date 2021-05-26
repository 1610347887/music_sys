<template>
  <div class="login-wrap">
    <div class="ms-title">music 后台管理登录</div>
    <div class="ms-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" @keyup.enter.native="submitForm">
        <el-form-item prop="name">
          <el-input v-model="ruleForm.name" placeholder="用户名"/>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="ruleForm.password" placeholder="密码"/>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {getLoginStatus} from '../api/index'
import {mixin} from '../mixins/index'
export default {
  mixins: [mixin],
  data: function (){
    return {
      ruleForm: {
        name: '',
        password: ''
      },
      rules: {
        name: [
          {required:true,message:'请输入用户名',trigger:'blur'}
        ],
        password: [
          {required:true,message:'请输入密码',trigger:'blur'}
        ]
      }
    }
  },
  methods:{
    submitForm(){
      getLoginStatus(this.ruleForm)
        .then(res =>{
          if (res.code == 200){
            localStorage.setItem('username',this.ruleForm.name)    // 将用户名放进缓存，供其他页面获取
            this.$message({
              type: 'success',
              message: '登录成功'
            })
            this.$router.push("/info")
          }else {
            this.$message({
              type: 'error',
              message: '用户名或密码错误'
            })
          }
        })
    }
  }
}
</script>

<style scoped>
.login-wrap {
  position: relative;
  background: url("../assets/img/background.jpg");
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}
.ms-title {
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #fff;
}
.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 300px;
  height: 160px;
  margin-left: -186px;
  margin-top: -150px;
  padding: 40px;
  border-radius: 5px;
  background: #fff;
  /*透明度*/
  opacity: 0.9;
}
.login-btn {
  text-align: center;
}
.login-btn button {
  width: 100%;
  height: 36px;
}
</style>
