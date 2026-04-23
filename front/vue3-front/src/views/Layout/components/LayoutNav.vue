<script setup>
import { useRouter } from 'vue-router'
const userStore = useUserStore()
const router = useRouter()
const confirm = () => {
  console.log('用户要退出登录了')
  // 退出登录业务逻辑实现
  // 1.清除用户信息 触发action
  userStore.clearUserInfo()
  // 2.跳转到登录页
  router.push('/login')
}
</script>

<template>
<!--  <nav class="app-topnav">-->
<!--    <div class="container">-->
<!--      <ul>-->
<!--        &lt;!&ndash; 多模版渲染 区分登录状态和非登录状态 &ndash;&gt;-->

<!--        &lt;!&ndash; 适配思路: 登录时显示第一块 非登录时显示第二块  是否有token &ndash;&gt;-->
<!--        <template v-if="userStore.userInfo.token">-->
<!--          <li><a href="javascript:;"><i class=" iconfont icon-user"></i>{{ userStore.userInfo.account }}</a></li>-->
<!--          <li>-->
<!--            <el-popconfirm @confirm="confirm" title="确认退出吗?" confirm-button-text="确认" cancel-button-text="取消">-->
<!--              <template #reference>-->
<!--                <a href="javascript:;">退出登录</a>-->
<!--              </template>-->
<!--            </el-popconfirm>-->
<!--          </li>-->
<!--          <li><a href="javascript:;">我的订单</a></li>-->
<!--          <li><a href="javascript:;">会员中心</a></li>-->
<!--        </template>-->
<!--        <template v-else>-->
<!--          <li><a href="javascript:;" @click="$router.push('/login')">请先登录</a></li>-->
<!--          <li><a href="javascript:;">帮助中心</a></li>-->
<!--          <li><a href="javascript:;">关于我们</a></li>-->
<!--        </template>-->
<!--      </ul>-->
<!--    </div>-->
<!--  </nav>-->
</template>


<style scoped >
</style>