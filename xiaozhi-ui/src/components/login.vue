<template>
<div>
    <!-- <h1>LOGIN</h1> -->
    <br>     
    enter your email
    <input type="text" v-model="registerDTO.email"></input><br>
    enter your username
    <input type="text" v-model="registerDTO.username"></input><br>
    enter your password
    <input type="text" v-model="registerDTO.password"></input><br>
    enter vertify code
    <input type="text" v-model="registerDTO.code"></input><br>
    <button @click="sentEmailCode">send</button>
    <button @click="register">register</button>
    
</div>

</template>

<script lang="ts" setup>
    import { ref ,reactive} from 'vue';
    import login from '../hooks/login';
    import { Bottom } from '@element-plus/icons-vue';
    let registerDTO=reactive({
                username:'',
                email:'',
                code:'',
                password:''
    })
    let {getRegisterCode,UserRegister}=login()

    function sentEmailCode(){
        let result=getRegisterCode(registerDTO.email)
        console.log('sentEmailCode@@@@@@',result)
    }
    function register(){
        let result=UserRegister(registerDTO)
        console.log('register12121212',result)
        console.log('register12121212',registerDTO)
    }
</script>

<style>

</style>