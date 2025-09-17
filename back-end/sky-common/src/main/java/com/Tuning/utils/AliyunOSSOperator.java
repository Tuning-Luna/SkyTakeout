package com.Tuning.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliyunOSSOperator {

  private String endpoint;
  private String bucketName;
  private String region;


  public String upload(byte[] centent, String originalFileName) throws Exception {
    // 加载ID和KEY
    EnvironmentVariableCredentialsProvider credentialsProvider =
            CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

    // 创建示例对象
    ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
    clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
    OSS ossClient = OSSClientBuilder.create()
            .endpoint(endpoint)
            .credentialsProvider(credentialsProvider)
            .clientConfiguration(clientBuilderConfiguration)
            .region(region)
            .build();

    // 获取到2004/12 类似的格式，用于放在不同的文件夹
    String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));

    // 盐，避免重复
    String salt = UUID.randomUUID().toString().replace("-", "");

    // 最终拼接成新的文件名：例如：2004/12/2a6c1b0d44a41a8a13e4d83f6b2a7d1-birthday.jpg
    String fileName = dateDir + "/" + salt + "-" + originalFileName;

    try {
      // 根据传递的文件数据得到InputStream
      InputStream inputStream = new ByteArrayInputStream(centent);
      // 创建PutObjectRequest对象。
      PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream);
      // 创建PutObject请求。
      ossClient.putObject(putObjectRequest);

    } catch (OSSException oe) {
      System.out.println("Caught an OSSException, which means your request made it to OSS, "
              + "but was rejected with an error response for some reason.");
      System.out.println("Error Message:" + oe.getErrorMessage());
      System.out.println("Error Code:" + oe.getErrorCode());
      System.out.println("Request ID:" + oe.getRequestId());
      System.out.println("Host ID:" + oe.getHostId());
    } finally {
      ossClient.shutdown();
    }


    // 根据规律拼接出访问地址
    String path = "https://" + bucketName + "." +
            endpoint.replace("https://", "") + "/" + fileName;
    return path;
  }

}

