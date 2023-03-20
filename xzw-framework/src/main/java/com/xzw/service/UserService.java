package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-02-09 00:28:29
 */
public interface UserService extends IService<User> {
    /**前台*/
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    /**后台*/
    // 查询用户详细
    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);
    //验证用户名是否唯一
    boolean checkUserNameUnique(String userName);
    //验证联系方式是否唯一
    boolean checkPhoneUnique(User user);
    //验证邮箱是否唯一
    boolean checkEmailUnique(User user);
    //添加用户
    ResponseResult addUser(User user);
    //修改用户信息
    void updateUser(User user);
}

