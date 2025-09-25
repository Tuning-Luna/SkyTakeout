package com.Tuning.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDataVO {

  private Double turnover;// 营业额

  private Integer validOrderCount;// 有效订单数

  private Double orderCompletionRate;// 订单完成率

  private Double unitPrice;// 平均客单价

  private Integer newUsers;// 新增用户数

}
