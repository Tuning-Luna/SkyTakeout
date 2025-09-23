package com.tuning.service;

import com.Tuning.dto.ShoppingCartDTO;
import com.Tuning.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
  void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

  List<ShoppingCart> showShoppingCart();

  void clear();

  void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
