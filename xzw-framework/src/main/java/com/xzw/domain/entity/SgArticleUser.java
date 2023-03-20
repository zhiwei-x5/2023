package com.xzw.domain.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (SgArticleUser)表实体类
 *
 * @author makejava
 * @since 2023-01-31 20:26:38
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_article_user")
@Accessors(chain = true)//在ArticleServiceImpl的articleList方法中的strem将set的void返回类型改成Article，如此返回就是当前article对象本身
public class SgArticleUser  {

    private Long userId;

    private Long articleId;


    @TableField(exist = false)
    private String title;
    @TableField(exist = false)
    //所属分类id
    private Long categoryId;
}

