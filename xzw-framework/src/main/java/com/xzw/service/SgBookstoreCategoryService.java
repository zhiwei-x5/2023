package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.AddBookstoreDto;
import com.xzw.domain.entity.SgBookstore;
import com.xzw.domain.entity.SgBookstoreCategory;


/**
 * 分类表(SgBookstoreCategory)表服务接口
 *
 * @author makejava
 * @since 2023-02-07 16:21:16
 */
public interface SgBookstoreCategoryService extends IService<SgBookstoreCategory> {

    ResponseResult getCategoryList();


}

