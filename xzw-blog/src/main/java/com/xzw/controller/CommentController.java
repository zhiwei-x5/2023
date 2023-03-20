package com.xzw.controller;

import com.xzw.constants.SystemConstants;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Comment;
import com.xzw.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    @ApiOperation(value = "文章评论",notes = "获取某篇文章评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "页面id "),
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        /**文章评论*/
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT, articleId,pageNum,pageSize);
    }
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        /**友联评论*/
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

}
