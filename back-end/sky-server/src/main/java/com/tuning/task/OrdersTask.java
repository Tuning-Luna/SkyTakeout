package com.tuning.task;

import com.Tuning.entity.Orders;
import com.tuning.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrdersTask {
  final private OrderMapper orderMapper;

  @Autowired
  public OrdersTask(OrderMapper orderMapper) {
    this.orderMapper = orderMapper;
  }

  // 每分钟检查下订单时候超时
  @Scheduled(cron = "0 * * * * ?")
  public void processTimeoutOrder() {
    LocalDateTime time = LocalDateTime.now().plusMinutes(- 15);

    List<Orders> lt = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);

    if (lt != null && ! lt.isEmpty()) {
      for (Orders orders : lt) {
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("订单超时，自动取消");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
      }
    }
  }

  // 一直处于派送中的订单
  @Scheduled(cron = "0 0 1 * * ?")
  public void processDeliveryOrder() {
    LocalDateTime time = LocalDateTime.now().plusMinutes(- 60);

    List<Orders> lt = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);

    if (lt != null && ! lt.isEmpty()) {
      for (Orders orders : lt) {
        orders.setStatus(Orders.COMPLETED);
        orderMapper.update(orders);
      }
    }
  }

}
