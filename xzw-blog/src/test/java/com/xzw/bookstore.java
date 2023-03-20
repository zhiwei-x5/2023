package com.xzw;

import com.xzw.domain.entity.Article;
import com.xzw.domain.entity.SgBookstore;
import com.xzw.service.ArticleService;
import com.xzw.service.SgArticleDzService;
import com.xzw.service.SgArticleUserService;
import com.xzw.service.SgBookstoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(classes = XZWBlogApplication.class)
//@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
/*@RunWith(SpringRunner.class)的作用表明Test测试类要使用注入的类，比如@Autowired注入的类，
有了@RunWith(SpringRunner.class)这些类才能实例化到spring容器中，自动注入才能生效*/
public class bookstore {
   @Autowired
    private SgBookstoreService sgBookstoreService;

   @Test
    public void bookstoreTest(){
       SgBookstore sgBookstore = new SgBookstore();
       sgBookstore.setBookName("2");
       sgBookstoreService.bookstoreList(sgBookstore,1,10);

   }
}
