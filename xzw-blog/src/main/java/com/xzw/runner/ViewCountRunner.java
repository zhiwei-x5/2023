package com.xzw.runner;


import com.xzw.domain.entity.Article;
import com.xzw.mapper.ArticleMapper;
import com.xzw.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
/**在应用启动时把文章的浏览量存储到redis中*/
public class ViewCountRunner implements CommandLineRunner {
    /*项目启动之前，预先加载数据。比如，权限容器、特殊用户数据等。通常我们可以使用监听器、事件来操作。
    但是，springboot提供了一个简单的方式来实现此类需求，即，CommandLineRunner。*/

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询文章信息  id  viewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream()//Integer:这里要是使用Integer，是因为redis中对于long的1L是无法进行递增
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();//Integer:这里要是使用Integer，是因为redis中对于long的1L是无法进行递增
                }));
        //存储到redis中
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
