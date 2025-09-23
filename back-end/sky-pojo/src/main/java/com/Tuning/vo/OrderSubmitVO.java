package com.Tuning.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSubmitVO {
  // 订单id
  private Long id;
  // 订单号
  private String orderNumber;
  // 订单金额
  private BigDecimal orderAmount;
  // 下单时间
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime orderTime;
}
