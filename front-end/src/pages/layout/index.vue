<script lang="ts" setup>
import { useTestStore } from '@/store/test';

defineOptions({
  name: 'LayoutIndex'
})

const testStore = useTestStore()
</script>

<template>
  <div>Layout Index</div>
  <div>count:{{ testStore.count }}</div>
  <div>Doublecount:{{ testStore.double }}</div>
  <button @click="testStore.count++">+1</button>

  <router-link to="/login">去登录</router-link>
</template>

<style scoped lang="scss"></style>