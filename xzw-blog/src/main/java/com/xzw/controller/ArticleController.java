package com.xzw.controller;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Article;
import com.xzw.domain.entity.SgArticleDz;
import com.xzw.domain.entity.SgArticleUser;
import com.xzw.service.ArticleService;
import com.xzw.service.SgArticleDzService;
import com.xzw.service.SgArticleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController注解是@Controller和@ResponseBody的合集,表示这是个控制器 bean,
 * @ResponseBody：将函数的返回值直接填入 HTTP 响应体中,是 REST 风格的控制器。*/
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SgArticleUserService sgArticleUserService;

    @Autowired
    private SgArticleDzService sgArticleDzService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){

        ResponseResult result =  articleService.hotArticleList();
        return result;
        /**
            之前:将返回数据转为json格式，如果加了@ResponseBody则还会将其存到响应体中
            ①导入jackson依赖
                 <!-- jackson，帮助进行json转换-->
                 <dependency>
                    <groupId>com.fasterxml.jackson.core</groupId> <artifactId>jackson-databind</artifactId> <version>2.9.0</version>
                 </dependency>
             ②开启mvc的注解驱动
                <mvc:annotation-driven></mvc:annotation-driven>
            现在：SpringBoot项目中使用了web的start后，不需要进行额外的依赖和配置
                 <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-starter-web</artifactId>
                 </dependency>
         * */
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/articleListSearch")
    public ResponseResult articleListSearch(Integer pageNum,Integer pageSize,String name){
        return articleService.articleListSearch(pageNum,pageSize,name);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

    @GetMapping("/{id}")/** 路径上的id需要加@PathVariable注解标识，参数名与占位符名字一样可以不用@PathVariable加上("id")*/
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
    /**收藏*/
    @GetMapping("/getBookmark")
    public ResponseResult getBookmark(Integer id, Article article,Integer pageNum, Integer pageSize){
        Long Longid = Long.valueOf(id);
        //1、先查出文章id集合
        List<Long> SgArticleUserList = sgArticleUserService.listSgArticleUser(Longid);
        //2、再查出每个文章id所对应的信息
        return articleService.listSgArticleUser(article,SgArticleUserList,pageNum, pageSize);
    }
    /**删除收藏*/
    @DeleteMapping("/delBookmark")
    public ResponseResult remove(Long userId, Long articleId){
        sgArticleUserService.delById(userId,articleId);
        return ResponseResult.okResult();
    }
    /**显示收藏*/
    @GetMapping("/getWidget/{articleId}")
    public ResponseResult getWidget(@PathVariable("articleId") Long articleId){
        return sgArticleUserService.getWidget(articleId);
    }
    /**是否添加*/
    @PostMapping("/showWidget")
    public ResponseResult showWidget(@RequestBody SgArticleUser sgArticleUser){

        return sgArticleUserService.showById(sgArticleUser.getUserId(),sgArticleUser.getArticleId());
    }
    /**添加收藏*/
    @PostMapping("/addWidget")
    public ResponseResult addWidget(@RequestBody SgArticleUser sgArticleUser){
        sgArticleUserService.addById(sgArticleUser.getUserId(),sgArticleUser.getArticleId());
        return ResponseResult.okResult();
    }



    /**显示点赞*/
    @GetMapping("/getdz/{articleId}")
    public ResponseResult getdz(@PathVariable("articleId") Long articleId){
        return sgArticleDzService.getdz(articleId);
    }
    /**是否添加*/
    @PostMapping("/showdz")
    public ResponseResult showdz(@RequestBody SgArticleUser sgArticleUser){
        return sgArticleDzService.showdz(sgArticleUser.getUserId(),sgArticleUser.getArticleId());
    }
    /**添加点赞*/
    @PostMapping("/adddz")
    public ResponseResult adddz(@RequestBody SgArticleDz sgArticleDz){
        sgArticleDzService.adddz(sgArticleDz.getUserId(),sgArticleDz.getArticleId());
        return ResponseResult.okResult();
    }
    /**删除点赞*/
    @DeleteMapping("/deldz")
    public ResponseResult deldz(Long userId, Long articleId){
        sgArticleDzService.deldz(userId,articleId);
        return ResponseResult.okResult();
    }



    /**查询自己所有的文章*/
    @GetMapping("/getMyArticle")
    public ResponseResult getMyArticle(Integer id,Article article,Integer pageNum, Integer pageSize){
        Long Longid = Long.valueOf(id);

        System.out.println("article.getCategoryId()");
        System.out.println(article.getCategoryId());
        //2、再查出每个文章id所对应的信息
        return articleService.listMySgArticleUser(Longid, article,pageNum, pageSize);
    }

    /**删除的文章*/
    @DeleteMapping("/delMyArticle")
    public ResponseResult delMyArticle(Long userId, Long articleId){
        articleService.removeById(articleId);
        return ResponseResult.okResult();
    }
}
