import { createApp } from 'vue'
import App from './App.vue'
// Vue-Router配置
import router from './router'
// Pinia配置
import { createPinia } from 'pinia'
const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)

app.mount('#app')
