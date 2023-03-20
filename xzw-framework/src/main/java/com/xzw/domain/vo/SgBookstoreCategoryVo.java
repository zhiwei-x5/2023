package com.xzw.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 分类表(SgBookstoreCategory)表实体类
 *
 * @author makejava
 * @since 2023-02-07 16:21:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_bookstore_category")
public class SgBookstoreCategoryVo {
    @TableId
    private Long id;

    //分类名
    private String name;

}

