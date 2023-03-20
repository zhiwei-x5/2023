//package com.xzw;
//
//import com.xzw.domain.ResponseResult;
//import com.xzw.domain.entity.Article;
//import com.xzw.domain.entity.SgArticleUser;
//import com.xzw.service.ArticleService;
//import com.xzw.service.SgArticleUserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
///*@RunWith(SpringRunner.class)的作用表明Test测试类要使用注入的类，比如@Autowired注入的类，
//有了@RunWith(SpringRunner.class)这些类才能实例化到spring容器中，自动注入才能生效*/
//public class test1 {
//    @Autowired
//    private SgArticleUserService sgArticleUserService;
//    private ArticleService articleService;
//
//    @Test
//    public ResponseResult t1(){
//        Article article = new Article();
//        article.setId(1L);
//        System.out.println("111");
//        List<SgArticleUser> list = sgArticleUserService.listSgArticleUser(article);
//        System.out.println(list);
//        return null;
//    }
//}
