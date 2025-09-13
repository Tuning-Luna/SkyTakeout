<script lang="ts" setup>
import { useTestStore } from '@/store/test';

const testStore = useTestStore()
</script>

<template>
  <div>Login Index</div>
  <div>count:{{ testStore.count }}</div>
  <div>Doublecount:{{ testStore.double }}</div>
  <button @click="testStore.count++">+1</button>

  <router-link to="/">去首页</router-link>
</template>

<style scoped lang="scss"></style>