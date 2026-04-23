<script setup lang="ts">
import LayoutHeader from "./components/LayoutHeader.vue";
import LayoutFooter from "./components/LayoutFooter.vue";
import LayoutNav from "./components/LayoutNav.vue";
</script>
<template>
  <LayoutHeader/>
  <LayoutNav/>
    <RouterView/>
  <LayoutFooter/>
</template>
