package com.xzw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.constants.SystemConstants;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.entity.Article;
import com.xzw.domain.entity.Comment;
import com.xzw.domain.entity.User;
import com.xzw.domain.vo.CommentVo;
import com.xzw.domain.vo.PageVo;
import com.xzw.enums.AppHttpCodeEnum;
import com.xzw.exception.SystemException;
import com.xzw.mapper.CommentMapper;
import com.xzw.service.CommentService;
import com.xzw.service.UserService;
import com.xzw.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.xzw.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-11-15 13:25:36
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        /**
         ARTICLE_COMMENT：文章评论=0、LINK_COMMENT：友联评论=1
         只有当为文章评论的时候才会执行：传入id和文章id比较；注意：友链是所有文章的
         */
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论 rootId为-1
        queryWrapper.eq(Comment::getRootId,-1);

        //分页查询
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        for (CommentVo commentVo : commentVoList) {
            //查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {

        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }
    /**将从sg_comment中查到的数据传到该方法进行拼接新的数据返回前端*/
    private List<CommentVo> toCommentVoList(List<Comment> list){
        System.out.println("list-----:"+ list);
        //将sg_comment查到的数据赋给CommentVo对应的字段
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历vo集合，再通过userid查询sysy_user表中的用户名，再进行赋值给CommentVo
        for (CommentVo commentVo : commentVos) {
            //通过creatyBy查询评论者用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            String avatar = userService.getById(commentVo.getCreateBy()).getAvatar();
            commentVo.setUsername(nickName);
            commentVo.setAvatar(avatar);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询,不为-1说明不是根评论，即该条数据上有评论，将被评论的用户名查询出来
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

}

