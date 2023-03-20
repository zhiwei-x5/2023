package com.xzw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Article;
import com.xzw.domain.entity.SgArticleUser;
import com.xzw.mapper.SgArticleUserMapper;
import com.xzw.service.SgArticleUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (SgArticleUser)表服务实现类
 *
 * @author makejava
 * @since 2023-01-31 20:26:43
 */
@Service("sgArticleUserService")
public class SgArticleUserServiceImpl extends ServiceImpl<SgArticleUserMapper, SgArticleUser> implements SgArticleUserService {

    @Override
    public List<Long> listSgArticleUser(Long id) {
        //第一步：先获取到文章id
        LambdaQueryWrapper<SgArticleUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SgArticleUser::getUserId, id);
        List<SgArticleUser> SgArticleUserList = list(lambdaQueryWrapper);

        List<Long> list = new ArrayList<>();
        for (SgArticleUser sgArticleUser:SgArticleUserList){
            list.add(sgArticleUser.getArticleId());
        }
        return list;
    }



    @Override
    public ResponseResult getWidget(Long articleId) {
        LambdaQueryWrapper<SgArticleUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SgArticleUser::getArticleId,articleId);
        List<SgArticleUser> list = list(lambdaQueryWrapper);
        return ResponseResult.okResult(list.size());
    }
    //显示是否添加
    @Override
    public void addById(Long userId, Long articleId) {
        getBaseMapper().add(userId,articleId);
    }
    @Override
    public void delById(Long userId, Long articleId) {
        getBaseMapper().del(userId,articleId);
    }
    @Override
    public ResponseResult showById(Long userId, Long articleId) {
        LambdaQueryWrapper<SgArticleUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SgArticleUser::getUserId,userId);
        lambdaQueryWrapper.eq(SgArticleUser::getArticleId,articleId);
        boolean res = count(lambdaQueryWrapper) !=0 ;
        System.out.println("res");
        System.out.println(res);
        return ResponseResult.okResult(res);
    }
}

