package com.tuning.skyserver;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

// @SpringBootTest
public class HttpClientTest {

  @Test
  public void getTest() throws Exception {
    // Aliyun 的 OSS 使用了这个依赖，所以不需要配置，直接传递即可
    CloseableHttpClient httpClient = HttpClients.createDefault();

    HttpGet httpGet = new HttpGet("http://localhost:80");

    CloseableHttpResponse execute = httpClient.execute(httpGet);

    System.out.println(EntityUtils.toString(execute.getEntity()));

    execute.close();
    httpClient.close();
  }

  @Test
  public void postTest() throws IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    HttpPost request = new HttpPost("http://localhost:80");

    // 构建请求体(JSON 字符串)
    String json = "{\"name\":\"Tuning Luna\",\"age\":18}";
    StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
    request.setEntity(entity);

    // 可以设置 Header
    request.setHeader("Accept", "application/json");
    request.setHeader("Content-Type", "application/json");

    // 执行请求
    var response = httpClient.execute(request);

    // 获取返回状态码
    int statusCode = response.getStatusLine().getStatusCode();
    System.out.println("Status Code: " + statusCode);

    // 获取返回内容
    String body = EntityUtils.toString(response.getEntity());
    System.out.println("Response Body: " + body);
  }
}
