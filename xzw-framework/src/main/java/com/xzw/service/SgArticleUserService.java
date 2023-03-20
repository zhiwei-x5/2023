package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Article;
import com.xzw.domain.entity.SgArticleUser;

import java.util.List;


/**
 * (SgArticleUser)表服务接口
 *
 * @author makejava
 * @since 2023-01-31 20:26:43
 */
public interface SgArticleUserService extends IService<SgArticleUser> {
    List<Long> listSgArticleUser(Long id);

    void delById(Long userId, Long articleId);

    ResponseResult getWidget(Long articleId);

    void addById(Long userId, Long articleId);

    ResponseResult showById(Long userId, Long articleId);



}

