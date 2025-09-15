package com.Tuning.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 使用分页查询返回的VO（和entity/Employee的数据一样）
public class EmployeePageQueryVO {

  private Long id;

  private String username;

  private String name;

  private String password;

  private String phone;

  private String sex;

  private String idNumber;

  private Integer status;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateTime;

  private Long createUser;

  private Long updateUser;
}
