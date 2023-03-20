package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.AddBookstoreDto;
import com.xzw.domain.entity.SgBookstore;


/**
 * (SgBookstore)表服务接口
 *
 * @author makejava
 * @since 2023-02-07 14:33:10
 */
public interface SgBookstoreService extends IService<SgBookstore> {

    ResponseResult bookstoreList(SgBookstore sgBookstore, Integer pageNum, Integer pageSize);

    ResponseResult addBookstore(AddBookstoreDto addBookstoreDto);

    ResponseResult MyBookstore(Integer id, SgBookstore sgBookstore, Integer pageNum, Integer pageSize);

    ResponseResult AllBookstore(SgBookstore sgBookstore, Integer pageNum, Integer pageSize);
}

