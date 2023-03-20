package com.xzw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.SgArticleDz;
import com.xzw.domain.entity.SgArticleUser;
import com.xzw.mapper.SgArticleDzMapper;
import org.springframework.stereotype.Service;
import com.xzw.service.SgArticleDzService;

/**
 * (SgArticleDz)表服务实现类
 *
 * @author makejava
 * @since 2023-02-03 13:28:29
 */
@Service("sgArticleDzService")
public class SgArticleDzServiceImpl extends ServiceImpl<SgArticleDzMapper, SgArticleDz> implements SgArticleDzService {

    @Override
    public ResponseResult getdz( Long articleId) {
        LambdaQueryWrapper<SgArticleDz> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SgArticleDz::getArticleId,articleId);
        int res = count(lambdaQueryWrapper);
        return ResponseResult.okResult(res);
    }
    @Override
    public ResponseResult showdz(Long userId, Long articleId) {
        LambdaQueryWrapper<SgArticleDz> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SgArticleDz::getUserId,userId);
        lambdaQueryWrapper.eq(SgArticleDz::getArticleId,articleId);
        boolean res = count(lambdaQueryWrapper) !=0 ;
        System.out.println("res");
        System.out.println(res);
        return ResponseResult.okResult(res);
    }
    @Override
    public void adddz(Long userId, Long articleId) {
        getBaseMapper().adddz(userId,articleId);
    }
    @Override
    public void deldz(Long userId, Long articleId) {
        getBaseMapper().deldz(userId,articleId);
    }

}

