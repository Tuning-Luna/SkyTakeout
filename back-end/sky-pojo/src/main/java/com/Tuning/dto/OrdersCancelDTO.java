package com.Tuning.dto;

import lombok.Data;

@Data
public class OrdersCancelDTO {
  private Long id;
  // 订单取消原因
  private String cancelReason;
}
