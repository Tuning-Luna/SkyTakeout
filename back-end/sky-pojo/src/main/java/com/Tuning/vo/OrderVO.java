package com.Tuning.vo;

import com.Tuning.entity.OrderDetail;
import com.Tuning.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends Orders {

  // 订单菜品信息
  private String orderDishes;

  // 订单详情
  private List<OrderDetail> orderDetailList;
}
