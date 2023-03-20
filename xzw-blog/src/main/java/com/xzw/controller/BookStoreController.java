package com.xzw.controller;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.AddBookstoreDto;
import com.xzw.domain.entity.Article;
import com.xzw.domain.entity.SgArticleUser;
import com.xzw.domain.entity.SgBookstore;
import com.xzw.domain.entity.SgBookstoreCategory;
import com.xzw.service.SgBookstoreCategoryService;
import com.xzw.service.SgBookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

/**
 * @RestController注解是@Controller和@ResponseBody的合集,表示这是个控制器 bean,
 * @ResponseBody：将函数的返回值直接填入 HTTP 响应体中,是 REST 风格的控制器。*/
@RestController
@RequestMapping("/Bookstore")
public class BookStoreController {
    @Autowired
    private SgBookstoreService sgBookstoreService;
    @Autowired
    private SgBookstoreCategoryService sgBookstoreCategoryService;

    /**获取书本展示列表*/
    @GetMapping("/getBookstore")
    public ResponseResult getBookstore(SgBookstore sgBookstore, Integer pageNum, Integer pageSize){
        return sgBookstoreService.bookstoreList(sgBookstore,pageNum,pageSize);
    }

    /**获取书本展示列表*/
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return sgBookstoreCategoryService.getCategoryList();
    }

    /**出售书本*/
    @PostMapping("/addBookstore")
    public ResponseResult addBookstore(@RequestBody AddBookstoreDto addBookstoreDto){
        return sgBookstoreService.addBookstore(addBookstoreDto);
    }
    /**展示我的所有书籍*/
    @GetMapping("/MyBookstore")
    public ResponseResult MyBookstore(Integer id,SgBookstore sgBookstore,Integer pageNum, Integer pageSize){
        return sgBookstoreService.MyBookstore(id, sgBookstore,pageNum, pageSize);
    }
    /**删除我的书籍*/
    @DeleteMapping("/delMybook/{query}")
    public ResponseResult delMybook(@PathVariable("query") Long id){
        System.out.println("demy");
        System.out.println(id);
        sgBookstoreService.removeById(id);
        return ResponseResult.okResult();
    }
}
