package com.xzw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzw.domain.entity.SgArticleDz;
import org.apache.ibatis.annotations.Param;


/**
 * (SgArticleDz)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-03 13:28:28
 */
public interface SgArticleDzMapper extends BaseMapper<SgArticleDz> {

    void adddz(@Param("userId") Long userId, @Param("articleId") Long articleId);

    void deldz(@Param("userId") Long userId, @Param("articleId") Long articleId);
}

