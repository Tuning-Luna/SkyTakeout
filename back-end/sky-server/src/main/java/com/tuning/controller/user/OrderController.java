package com.tuning.controller.user;

import com.Tuning.dto.OrdersSubmitDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.result.PageResult;
import com.Tuning.vo.OrderSubmitVO;
import com.Tuning.vo.OrderVO;
import com.tuning.service.OrderService;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/historyOrders")
  public ApiResult<PageResult<OrderVO>> page(int page, int pageSize, Integer status) {
    PageResult<OrderVO> pageResult = orderService.pageQuery4User(page, pageSize, status);
    return ApiResult.ok(pageResult);
  }

  @GetMapping("/orderDetail/{id}")
  public ApiResult<OrderVO> details(@PathVariable("id") Long id) {
    OrderVO orderVO = orderService.details(id);
    return ApiResult.ok(orderVO);
  }
}
