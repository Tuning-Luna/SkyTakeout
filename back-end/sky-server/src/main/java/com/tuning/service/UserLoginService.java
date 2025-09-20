package com.tuning.service;

import com.Tuning.dto.UserLoginDTO;
import com.Tuning.entity.User;

public interface UserLoginService {
  User login(UserLoginDTO dto);
}
