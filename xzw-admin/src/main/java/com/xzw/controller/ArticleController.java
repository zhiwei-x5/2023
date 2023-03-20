package com.xzw.controller;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Article;
import com.xzw.service.ArticleService;
import com.xzw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/system/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    /**查询自己所有的文章*/
    @GetMapping("/getAllArticle")
    public ResponseResult getMyArticle(Article article, Integer pageNum, Integer pageSize){
        //2、再查出每个文章id所对应的信息
        return articleService.listAllSgArticleUser(article,pageNum, pageSize);
    }
    /**删除的文章*/
    @DeleteMapping("/delMyArticle/{ids}")
    public ResponseResult delMyArticle(@PathVariable(name = "ids")Long ids){
        System.out.println("xzw222222");
        System.out.println(ids);
        articleService.removeById(ids);
        return ResponseResult.okResult();
    }
    /**查询自己所有的文章*/
    @GetMapping("/getMyArticle")
    public ResponseResult getMyArticle(Integer id,Article article,Integer pageNum, Integer pageSize){
        Long Longid = Long.valueOf(id);

        //2、再查出每个文章id所对应的信息
        return articleService.listMySgArticleUser(Longid, article,pageNum, pageSize);
    }

    /**删除的文章*/
    @DeleteMapping("/delMyArticle")
    public ResponseResult delMyArticle(Long userId, Long articleId){
        articleService.removeById(articleId);
        return ResponseResult.okResult();
    }
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
