package com.xzw.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgBookstoreVo {
    private Long id;

    private Long userId;

    private String bookName;

    private String img;

    private Double price;

    private Integer books;

    private Integer count;

    private String category;
    //状态（0已发布，1草稿）
    private String status;
}
