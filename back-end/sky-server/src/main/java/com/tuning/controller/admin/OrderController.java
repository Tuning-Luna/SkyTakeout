package com.tuning.controller.admin;

import com.Tuning.dto.OrdersCancelDTO;
import com.Tuning.dto.OrdersConfirmDTO;
import com.Tuning.dto.OrdersPageQueryDTO;
import com.Tuning.dto.OrdersRejectionDTO;
import com.Tuning.result.ApiResult;
import com.Tuning.result.PageResult;
import com.Tuning.vo.OrderStatisticsVO;
import com.Tuning.vo.OrderVO;
import com.tuning.service.OrderService;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/statistics")
  public ApiResult<OrderStatisticsVO> statistics() {
    OrderStatisticsVO orderStatisticsVO = orderService.statistics();
    return ApiResult.ok(orderStatisticsVO);
  }

  @GetMapping("/details/{id}")
  public ApiResult<OrderVO> details(@PathVariable("id") Long id) {
    OrderVO orderVO = orderService.details(id);
    return ApiResult.ok(orderVO);
  }

  @PutMapping("/confirm")
  public ApiResult<String> confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
    orderService.confirm(ordersConfirmDTO);
    return ApiResult.ok();
  }

  @PutMapping("/rejection")
  public ApiResult<String> rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
    orderService.rejection(ordersRejectionDTO);
    return ApiResult.ok();
  }

  @PutMapping("/cancel")
  public ApiResult<String> cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
    orderService.cancel(ordersCancelDTO);
    return ApiResult.ok();
  }

  @PutMapping("/delivery/{id}")
  public ApiResult<String> delivery(@PathVariable("id") Long id) {
    orderService.delivery(id);
    return ApiResult.ok();
  }

  @PutMapping("/complete/{id}")
  public ApiResult complete(@PathVariable("id") Long id) {
    orderService.complete(id);
    return ApiResult.ok();
  }
}
