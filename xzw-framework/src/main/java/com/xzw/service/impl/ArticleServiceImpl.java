package com.xzw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.constants.SystemConstants;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.AddArticleDto;
import com.xzw.domain.entity.*;
import com.xzw.domain.vo.*;
import com.xzw.mapper.ArticleMapper;
import com.xzw.service.ArticleService;
import com.xzw.service.ArticleTagService;
import com.xzw.service.CategoryService;
import com.xzw.utils.BeanCopyUtils;
import com.xzw.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;
//    @Autowired
//    private ArticleMapper articleMapper;

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);
      /*  List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);*/
        return ResponseResult.okResult();
    }

    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus,  SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        /**此处的page以及下面的page可以说是一样的，直接通过page调用方法即可*/
        //将设置好的pages以及queryWrapper查询到的数据传入page方法进行获取符合pages设置的内容
        page(page,queryWrapper);

        //将查到的数据保存到list集合中
        List<Article> articles = page.getRecords();

        /**bean拷贝
            安全：不希望返回前端的数据是查询到的所有字段，只希望返回部分
            需要创建一个类作为返回的对象，类：需要返回的字段与原来数据的字段名一致
            解决方法：使用bean拷贝BeanUtils.copyProperties(article,vo);将源数据与目标数据传入即可
          */
        // for (Article article : articles) {
        // List<HotArticleVo> articleVos = new ArrayList<>();
        // HotArticleVo vo = new HotArticleVo();
        // BeanUtils.copyProperties(article,vo);
        // articleVos.add(vo);
        // }

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果 有categoryId 就要 查询时要和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        // 状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);


        //查询categoryName
        List<Article> articles = page.getRecords();
        //方法一：for循环遍历，为每个articles内的对象的CategoryName字段进行赋值
        //articleId去查询articleName进行设置
        /*for (Article article : articles) {
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }*/
        //方法二：
        /*articles.stream()
                //传入一个Article，返回也是Article
                .map(new Function<Article, Article>() {
                    @Override
                    public Article apply(Article article) {
                        //获取分类id，查询分类信息，获取分类名称
                        Category category = categoryService.getById(article.getCategoryId());
                        String name = category.getName();
                        //把分类名称设置给article
                        article.setCategoryName(name);
                        return article;//需要设置Article中的@Accessors(chain = true)，改变set返回类型
                    }
                });*/
        //方法二转变：lambda
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());//Collectors.toList() 将流中的所有元素导出到一个列表( List )中

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }
    /**收藏搜索*/
    public ResponseResult  listSgArticleUser(Article article ,List<Long> SgArticleUserList, Integer pageNum, Integer pageSize){
        //标题搜索：如果标题搜索框不为null则根据内容进行搜索
        LambdaQueryWrapper<Article> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.in(Article::getId,SgArticleUserList);

        lambdaQueryWrapper2.eq(article.getCategoryId()!=null,Article::getCategoryId,article.getCategoryId());
        lambdaQueryWrapper2.like(StringUtils.hasText(article.getTitle()), Article::getTitle,article.getTitle());
//        List<Article> lists = articleMapper.selectList(lambdaQueryWrapper2);
//        System.out.println(lists);
//        List<Article> list = list(lambdaQueryWrapper2);
        //第二步：封装一个page信息
        Page<Article> aPage = new Page<>();
        aPage.setCurrent(pageNum);
        aPage.setSize(pageSize);

        page(aPage,lambdaQueryWrapper2);
        List<SgArticleUserVo> sgArticleUserVo = BeanCopyUtils.copyBeanList(aPage.getRecords(), SgArticleUserVo.class);
        PageVo pageVo = new PageVo();
        pageVo.setTotal(aPage.getTotal());
        pageVo.setRows(sgArticleUserVo);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult articleListSearch(Integer pageNum, Integer pageSize, String name) {
        //标题搜索：如果标题搜索框不为null则根据内容进行搜索
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasText(name), Article::getTitle, name);
        //转为实体类
       List<Article> list = list(lambdaQueryWrapper);
        if (list.size() > 0){
            //获取分类id所对应另外一张表的名字
            list.stream()
                    .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                    .collect(Collectors.toList());//Collectors.toList() 将流中的所有元素导出到一个列表( List )中
        }
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(list, ArticleListVo.class);
        return ResponseResult.okResult(articleListVos);
    }

    /**查询所有我的文章*/
    @Override
    public ResponseResult listMySgArticleUser(Long longid , Article article , Integer pageNum, Integer pageSize) {
        //标题搜索：如果标题搜索框不为null则根据内容进行搜索
        LambdaQueryWrapper<Article> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(Article::getCreateBy,longid);

        lambdaQueryWrapper2.eq(article.getCategoryId() != null ,Article::getCategoryId,article.getCategoryId());
        lambdaQueryWrapper2.like(StringUtils.hasText(article.getTitle()), Article::getTitle,article.getTitle());
//        List<Article> lists = articleMapper.selectList(lambdaQueryWrapper2);
//        System.out.println(lists);
//        List<Article> list = list(lambdaQueryWrapper2);
        //第二步：封装一个page信息
        Page<Article> aPage = new Page<>();
        aPage.setCurrent(pageNum);
        aPage.setSize(pageSize);

        page(aPage,lambdaQueryWrapper2);
        List<SgArticleUserVo> sgArticleUserVo = BeanCopyUtils.copyBeanList(aPage.getRecords(), SgArticleUserVo.class);
        PageVo pageVo = new PageVo();
        pageVo.setTotal(aPage.getTotal());
        pageVo.setRows(sgArticleUserVo);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult listAllSgArticleUser(Article article, Integer pageNum, Integer pageSize) {
        //标题搜索：如果标题搜索框不为null则根据内容进行搜索
        LambdaQueryWrapper<Article> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();

//        lambdaQueryWrapper2.eq(article.getCategoryId()!=null,Article::getCategoryId,article.getCategoryId());
        lambdaQueryWrapper2.like(StringUtils.hasText(article.getTitle()), Article::getTitle,article.getTitle());
//        List<Article> lists = articleMapper.selectList(lambdaQueryWrapper2);
//        System.out.println(lists);
//        List<Article> list = list(lambdaQueryWrapper2);
        //第二步：封装一个page信息
        Page<Article> aPage = new Page<>();
        aPage.setCurrent(pageNum);
        aPage.setSize(pageSize);

        page(aPage,lambdaQueryWrapper2);
        List<SgArticleUserVo> sgArticleUserVo = BeanCopyUtils.copyBeanList(aPage.getRecords(), SgArticleUserVo.class);
        PageVo pageVo = new PageVo();
        pageVo.setTotal(aPage.getTotal());
        pageVo.setRows(sgArticleUserVo);
        return ResponseResult.okResult(pageVo);
    }
}