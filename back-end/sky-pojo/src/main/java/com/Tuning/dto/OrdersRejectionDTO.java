package com.Tuning.dto;

import lombok.Data;

@Data
public class OrdersRejectionDTO {
  private Long id;

  // 订单拒绝原因
  private String rejectionReason;
}
