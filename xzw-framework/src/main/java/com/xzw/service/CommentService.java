package com.xzw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-11-15 13:25:36
 */
public interface CommentService extends IService<Comment> {
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

