package com.tuning.controller.user;

import com.Tuning.dto.UserLoginDTO;
import com.Tuning.entity.User;
import com.Tuning.result.ApiResult;
import com.Tuning.utils.JWTUtil;
import com.Tuning.vo.UserLoginVO;
import com.tuning.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
public class UserController {

  final private UserLoginService userLoginService;

  @Autowired
  public UserController(UserLoginService userLoginService) {
    this.userLoginService = userLoginService;
  }

  @PostMapping("/login")
  public ApiResult<UserLoginVO> login(@RequestBody UserLoginDTO dto) {
    User user = userLoginService.login(dto);

    Map<String, Object> claim = new HashMap<>();
    claim.put("userId", user.getId());

    String token = JWTUtil.generateToken(claim);
    UserLoginVO vo = UserLoginVO.builder()
            .id(user.getId())
            .openid(user.getOpenid())
            .token(token).build();

    return ApiResult.ok(vo);
  }
}
