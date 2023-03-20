package com.xzw.service;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.User;

public interface ABackLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
