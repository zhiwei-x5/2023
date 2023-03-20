package com.xzw.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SgArticleUserVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;

    private Long createBy;
}
