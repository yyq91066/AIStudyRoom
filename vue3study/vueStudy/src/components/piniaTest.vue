<script setup lang="ts">
import {useCounterStore} from "../../stores/counter.ts";
const counterStore = useCounterStore()
</script>

<template>
<button @click="counterStore.increment">
  {{ counterStore.count }}
</button>
</template>

<style scoped>

</style>