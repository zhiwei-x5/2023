package com.xzw.service;


import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();

}
