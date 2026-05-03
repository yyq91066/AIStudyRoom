import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login/index.vue'
import Layout from '@/views/Layout/index.vue'
import Home from '@/views/Home/index.vue'
import Category from '@/views/Category/index.vue'
import AIService from '@/views/AIService/index.vue'
import Report from '@/views/Report/index.vue'
import Register from '@/views/Login/components/register.vue'
import Reserve from '@/views/Reserve/index.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: Layout,
      children: [
        {
          path: '',
          component: Home,
        },
        {
          path: 'category',
          component: Category,
        },
        {
          path: 'aiservice',
          component: AIService,
        },
        {
          path: 'report',
          component: Report,
        },
        {
          path: 'reserve',
          component: Reserve,
        },
      ],
    },
    {
      path: '/login',
      component: Login,
    },
    {
      path: '/register',
      component: Register,
    },
  ],
})

export default router
