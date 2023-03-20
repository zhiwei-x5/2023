package com.xzw.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Category;
import com.xzw.domain.vo.CategoryVo;
import com.xzw.domain.vo.PageVo;
import com.xzw.service.CategoryService;
import com.xzw.utils.BeanCopyUtils;
import com.xzw.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @PutMapping
    public ResponseResult edit(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable(value = "id")Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }
    @PostMapping
    public ResponseResult add(@RequestBody Category category){
        categoryService.save(category);
        return ResponseResult.okResult();
    }
    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public ResponseResult list(Category category, Integer pageNum, Integer pageSize) {
        PageVo pageVo = categoryService.selectCategoryPage(category,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }



}
