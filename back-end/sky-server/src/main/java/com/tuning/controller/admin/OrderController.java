package com.tuning.controller.admin;

import com.Tuning.dto.OrdersPageQueryDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.result.PageResult;
import com.Tuning.vo.OrderVO;
import com.tuning.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController {

  final private OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/conditionSearch")
  public ApiResult<PageResult<OrderVO>> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
    PageResult<OrderVO> pageResult = orderService.conditionSearch(ordersPageQueryDTO);
    return ApiResult.ok(pageResult);
  }
}
