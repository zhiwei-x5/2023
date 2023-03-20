package com.xzw.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (SgArticleDz)表实体类
 *
 * @author makejava
 * @since 2023-02-03 13:28:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_article_dz")
public class SgArticleDz  {
    private Long userId;
    private Long articleId;
}

