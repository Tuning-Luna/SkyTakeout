package com.tuning.service;

import com.Tuning.vo.BusinessDataVO;
import com.Tuning.vo.DishOverViewVO;
import com.Tuning.vo.OrderOverViewVO;
import com.Tuning.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkspaceService {
  BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

  OrderOverViewVO getOrderOverView();

  DishOverViewVO getDishOverView();

  SetmealOverViewVO getSetmealOverView();
}
