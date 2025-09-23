package com.tuning.service.impl;

import com.Tuning.context.BaseContext;
import com.Tuning.dto.ShoppingCartDTO;
import com.Tuning.entity.Dish;
import com.Tuning.entity.Setmeal;
import com.Tuning.entity.ShoppingCart;
import com.tuning.mapper.DishMapper;
import com.tuning.mapper.SetmealMapper;
import com.tuning.mapper.ShoppingCartMapper;
import com.tuning.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

  final private ShoppingCartMapper shoppingCartMapper;
  final private DishMapper dishMapper;
  final private SetmealMapper setmealMapper;

  @Autowired
  public ShoppingCartServiceImpl(ShoppingCartMapper shoppingCartMapper,
                                 DishMapper dishMapper,
                                 SetmealMapper setmealMapper) {
    this.shoppingCartMapper = shoppingCartMapper;
    this.dishMapper = dishMapper;
    this.setmealMapper = setmealMapper;
  }


  @Override
  public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
    ShoppingCart shoppingCart = new ShoppingCart();
    BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
    // 只能查询自己的购物车数据
    shoppingCart.setUserId(BaseContext.getCurrentId());

    // 判断当前商品是否在购物车中
    List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);

    // 不为空
    if (shoppingCartList != null && ! shoppingCartList.isEmpty()) {
      shoppingCart = shoppingCartList.get(0);
      shoppingCart.setNumber(shoppingCart.getNumber() + 1);
      shoppingCartMapper.updateNumberById(shoppingCart);
    }
    else {
      // 为空，新增逻辑
      // 判断当前添加到购物车的是菜品还是套餐
      Long dishId = shoppingCartDTO.getDishId();
      if (dishId != null) {
        // 添加到购物车的是菜品
        Dish dish = dishMapper.getById(dishId);
        shoppingCart.setName(dish.getName());
        shoppingCart.setImage(dish.getImage());
        shoppingCart.setAmount(dish.getPrice());
      }
      else {
        // 添加到购物车的是套餐
        Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
        shoppingCart.setName(setmeal.getName());
        shoppingCart.setImage(setmeal.getImage());
        shoppingCart.setAmount(setmeal.getPrice());
      }
      shoppingCart.setNumber(1);
      shoppingCart.setCreateTime(LocalDateTime.now());
      shoppingCartMapper.insert(shoppingCart);
    }
  }

  @Override
  public List<ShoppingCart> showShoppingCart() {
    ShoppingCart build = ShoppingCart.builder().userId(BaseContext.getCurrentId()).build();
    return shoppingCartMapper.list(build);
  }

  @Override
  public void clear() {
    shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
  }

  @Override
  public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
    ShoppingCart shoppingCart = new ShoppingCart();
    BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

    shoppingCart.setUserId(BaseContext.getCurrentId());
    List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

    if (list != null && ! list.isEmpty()) {
      shoppingCart = list.get(0);

      Integer number = shoppingCart.getNumber();
      if (number == 1) {
        shoppingCartMapper.deleteById(shoppingCart.getId());
      }
      else {
        shoppingCart.setNumber(shoppingCart.getNumber() - 1);
        shoppingCartMapper.updateNumberById(shoppingCart);
      }
    }


  }
}
