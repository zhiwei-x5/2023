package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.SgArticleDz;


/**
 * (SgArticleDz)表服务接口
 *
 * @author makejava
 * @since 2023-02-03 13:28:29
 */
public interface SgArticleDzService extends IService<SgArticleDz> {

    ResponseResult getdz( Long articleId);

    void adddz(Long userId, Long articleId);

    void deldz(Long userId, Long articleId);

    ResponseResult showdz(Long userId, Long articleId);
}

