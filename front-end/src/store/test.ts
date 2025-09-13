import { defineStore } from "pinia";
import { ref, computed } from "vue";


export const useTestStore = defineStore('test', () => {
  const count = ref<number>(0)
  const double = computed(() => count.value * 2)

  function increment() {
    count.value++
  }

  return {
    count,
    double,
    increment
  }
})