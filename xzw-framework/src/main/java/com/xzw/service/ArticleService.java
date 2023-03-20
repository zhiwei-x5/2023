package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.AddArticleDto;
import com.xzw.domain.entity.Article;
import com.xzw.domain.entity.SgArticleUser;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ArticleService extends IService<Article> {
    //查询热门文章，封装转为ResponseResult返回，将数据保存在ResponseResult的data中
    ResponseResult hotArticleList();
    /**
     在首页和分类页面都需要查询文章列表。
     首页：查询所有的文章
     分类页面：查询对应分类下的文章
     要求：①只能查询正式发布的文章 ②置顶的文章要显示在最前面
     */
    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     要求在文章列表点击阅读全文时能够跳转到文章详情页面，可以让用户阅读文章正文。
     要求：①要在文章详情中展示其分类名
     */
    ResponseResult getArticleDetail(Long id);

    /**更新文章浏览数量*/
    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult listSgArticleUser(Article article ,List<Long> SgArticleUserList ,Integer pageNum, Integer pageSize);

    ResponseResult articleListSearch(Integer pageNum, Integer pageSize, String name);

    ResponseResult listMySgArticleUser(Long longid ,Article article, Integer pageNum, Integer pageSize);

    ResponseResult listAllSgArticleUser(Article article, Integer pageNum, Integer pageSize);
}
