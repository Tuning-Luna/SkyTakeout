package com.tuning.controller.admin;

import com.Tuning.result.ApiResult;
import com.Tuning.vo.BusinessDataVO;
import com.Tuning.vo.DishOverViewVO;
import com.Tuning.vo.OrderOverViewVO;
import com.Tuning.vo.SetmealOverViewVO;
import com.tuning.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/admin/workspace")
public class WorkSpaceController {

  final private WorkspaceService workspaceService;

  @Autowired
  public WorkSpaceController(WorkspaceService workspaceService) {
    this.workspaceService = workspaceService;
  }


  @GetMapping("/businessData")
  public ApiResult<BusinessDataVO> businessData() {
    // 获得当天的开始时间
    LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
    // 获得当天的结束时间
    LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

    BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
    return ApiResult.ok(businessDataVO);
  }

  @GetMapping("/overviewOrders")
  public ApiResult<OrderOverViewVO> orderOverView() {
    return ApiResult.ok(workspaceService.getOrderOverView());
  }

  @GetMapping("/overviewDishes")
  public ApiResult<DishOverViewVO> dishOverView() {
    return ApiResult.ok(workspaceService.getDishOverView());
  }

  @GetMapping("/overviewSetmeals")
  public ApiResult<SetmealOverViewVO> setmealOverView() {
    return ApiResult.ok(workspaceService.getSetmealOverView());
  }
}
