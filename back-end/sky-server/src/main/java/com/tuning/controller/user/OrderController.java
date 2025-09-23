package com.tuning.controller.user;

import com.Tuning.dto.OrdersSubmitDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.vo.OrderSubmitVO;
import com.tuning.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userOrderController")
@RequestMapping("/user/order")
public class OrderController {

  final private OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/submit")
  public ApiResult<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
    OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
    return ApiResult.ok(orderSubmitVO);
  }
}
