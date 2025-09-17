package com.tuning.controller.admin;

import com.Tuning.result.ApiResult;
import com.Tuning.utils.AliyunOSSOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/common")
public class UplaodContoller {

  private final AliyunOSSOperator aliyunOSSOperator;

  @Autowired
  public UplaodContoller(AliyunOSSOperator aliyunOSSOperator) {
    this.aliyunOSSOperator = aliyunOSSOperator;
  }

  @PostMapping("/upload")
  public ApiResult<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
    if (file.isEmpty()) {
      return ApiResult.fail(HttpStatus.BAD_REQUEST, "文件不存在");
    }

    String upload = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());

    return ApiResult.ok(upload, "上传成功");
  }

}
