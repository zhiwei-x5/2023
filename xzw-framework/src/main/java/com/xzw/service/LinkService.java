package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Link;


/**
 * 友链(SgLink)表服务接口
 *
 * @author makejava
 * @since 2022-11-13 15:02:21
 */
public interface LinkService extends IService<Link> {
    //查询所有友情链接
    ResponseResult getAllLink();
}

