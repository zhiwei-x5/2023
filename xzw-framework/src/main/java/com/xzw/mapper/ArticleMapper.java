package com.xzw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzw.domain.entity.Article;


//在对应的Mapper上继承基本的类baseMapper
public interface ArticleMapper extends BaseMapper<Article> {
    //所有的CRUD(增删改查）已经编写完成
    //不需要像以前的配置一些xml

}
