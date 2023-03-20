package com.xzw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzw.domain.entity.SgArticleUser;
import org.apache.ibatis.annotations.Param;


/**
 * (SgArticleUser)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-31 20:26:41
 */
public interface SgArticleUserMapper extends BaseMapper<SgArticleUser> {

     void del(@Param("userId")Long userId, @Param("bookmarkId")Long articleId);

     void add(@Param("userId")Long userId, @Param("bookmarkId")Long articleId);
}

