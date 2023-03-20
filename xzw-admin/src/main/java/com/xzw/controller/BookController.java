package com.xzw.controller;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.SgBookstore;
import com.xzw.service.SgBookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/bookstore")
public class BookController {

    @Autowired
    private SgBookstoreService sgBookstoreService;

    /**展示我的书籍*/
    @GetMapping("/AllBookstore")
    public ResponseResult AllBookstore(SgBookstore sgBookstore, Integer pageNum, Integer pageSize){
        return sgBookstoreService.AllBookstore(sgBookstore,pageNum, pageSize);
    }
    /**删除我的书籍*/
    @DeleteMapping("/delAllbook/{query}")
    public ResponseResult delAllbook(@PathVariable("query") Long id){
        System.out.println("demy");
        System.out.println(id);
        sgBookstoreService.removeById(id);
        return ResponseResult.okResult();
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
