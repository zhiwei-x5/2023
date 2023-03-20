package com.xzw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.AddBookstoreDto;
import com.xzw.domain.entity.SgBookstore;
import com.xzw.domain.entity.SgBookstoreCategory;
import com.xzw.domain.vo.SgBookstoreCategoryVo;
import com.xzw.domain.vo.SgBookstoreVo;
import com.xzw.mapper.SgBookstoreCategoryMapper;
import com.xzw.service.SgBookstoreCategoryService;
import com.xzw.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类表(SgBookstoreCategory)表服务实现类
 *
 * @author makejava
 * @since 2023-02-07 16:21:16
 */
@Service("sgBookstoreCategoryService")
public class SgBookstoreCategoryServiceImpl extends ServiceImpl<SgBookstoreCategoryMapper, SgBookstoreCategory> implements SgBookstoreCategoryService {

    @Override
    public ResponseResult getCategoryList() {
//        List<SgBookstoreCategory> sgBookstoreCategories = getBaseMapper().selectList(null);
        LambdaQueryWrapper<SgBookstoreCategory> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.select(SgBookstoreCategory::getId,SgBookstoreCategory::getName);

        List<SgBookstoreCategory> list = list(lambdaQueryWrapper);

        List<SgBookstoreCategoryVo> listVo = BeanCopyUtils.copyBeanList(list,SgBookstoreCategoryVo.class);
        return ResponseResult.okResult(listVo);
    }



}

