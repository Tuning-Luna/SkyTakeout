package com.tuning.mapper;

import com.Tuning.OperationType;
import com.Tuning.entity.ShoppingCart;
import com.tuning.annotation.AutoFill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
  // 更新购物车数量
  void updateNumberById(ShoppingCart shoppingCart);

  // 添加到购物车
  @AutoFill(OperationType.INSERT)
  void insert(ShoppingCart shoppingCart);

  // 条件查询
  List<ShoppingCart> list(ShoppingCart shoppingCart);

  // 删除userId的全部购物车数据
  void deleteByUserId(Long userId);

  // 根据id删除
  void deleteById(Long id);

  void insertBatch(List<ShoppingCart> shoppingCartList);
}
