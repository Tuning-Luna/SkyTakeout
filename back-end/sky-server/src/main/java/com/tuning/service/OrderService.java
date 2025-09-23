package com.tuning.service;

import com.Tuning.dto.OrdersSubmitDTO;
import com.Tuning.vo.OrderSubmitVO;

public interface OrderService {
  OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
