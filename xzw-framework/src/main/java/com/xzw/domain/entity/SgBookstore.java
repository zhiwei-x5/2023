package com.xzw.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (SgBookstore)表实体类
 *
 * @author makejava
 * @since 2023-02-07 14:33:08
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_bookstore")
@Accessors(chain = true)//在ArticleServiceImpl的articleList方法中的strem将set的void返回类型改成Article，如此返回就是当前article对象本身
public class SgBookstore {
    @TableId
    private Long id;

    private Long userId;

    private String bookName;
    
    private String img;
    
    private Double price;
    
    private Integer books;
    
    private Integer count;

    private Long category;

    @TableField(exist = false)
    private String categoryStr;
    //状态（0已发布，1草稿）
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}

