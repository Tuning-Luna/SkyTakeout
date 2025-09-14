import axios from 'axios'

const request = axios.create({
  baseURL: '/api/v1', // 这里不要加 http://localhost:8080，否则不会走Vite的代理
  timeout: 1000,
})

// 添加请求拦截器
request.interceptors.request.use(function (config) {
  // 在发送请求之前做些什么
  return config;
}, function (error) {
  // 对请求错误做些什么
  return Promise.reject(error);
});

// 响应拦截器
request.interceptors.response.use(
  function (response) {
    // 网络层面：只要是 2xx，这里就会进来
    const res = response.data

    // 业务层面：判断 code
    if (res.code === 0) {
      return res // 成功
    } else {
      return Promise.reject(new Error(res.message || '业务错误'))
    }
  },
  function (error) {
    // 网络层面：非 2xx 会到这里
    if (error.response) {
      // 后端返回了非 2xx
      console.error('网络错误:', error.response.status, error.response.data)
    } else {
      // 请求本身失败（断网、超时）
      console.error('请求失败:', error.message)
    }
    return Promise.reject(error)
  }
)


export { request }