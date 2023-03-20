package com.xzw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzw.domain.ResponseResult;
import com.xzw.domain.dto.AddBookstoreDto;
import com.xzw.domain.entity.SgBookstore;
import com.xzw.domain.vo.PageVo;
import com.xzw.domain.vo.SgBookstoreVo;
import com.xzw.mapper.SgBookstoreMapper;
import com.xzw.service.SgBookstoreCategoryService;
import com.xzw.service.SgBookstoreService;
import com.xzw.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * (SgBookstore)表服务实现类
 *
 * @author makejava
 * @since 2023-02-07 14:33:11
 */
@Service("sgBookstoreService")
public class SgBookstoreServiceImpl extends ServiceImpl<SgBookstoreMapper, SgBookstore> implements SgBookstoreService {

    @Autowired
    private SgBookstoreCategoryService sgBookstoreCategoryService;

    @Override
    public ResponseResult bookstoreList(SgBookstore sgBookstore, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SgBookstore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(sgBookstore.getStatus() !=null,SgBookstore::getStatus, sgBookstore.getStatus());
        //如果请求名字为null即为false则不进行该条件
        lambdaQueryWrapper.like(StringUtils.hasText(sgBookstore.getBookName()), SgBookstore::getBookName,sgBookstore.getBookName());
        lambdaQueryWrapper.eq(sgBookstore.getCategory() !=null ,SgBookstore::getCategory,sgBookstore.getCategory());

        //分页查询
        Page<SgBookstore> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        //查询categoryName
        List<SgBookstore> sgbookstores = page.getRecords();

//        sgbookstores.stream()
//                //@Accessors(chain = true)//在ArticleServiceImpl的articleList方法中的stream将set的void返回类型改成sgbookstore，如此返回就是当前sgbookstore对象本身
//                .map(sgbookstore -> sgbookstore.setCategoryStr(sgBookstoreCategoryService.getById(sgbookstore.getCategory()).getName()))
//                .collect(Collectors.toList());//Collectors.toList() 将流中的所有元素导出到一个列表( List )中

        //封装查询结果
        List<SgBookstoreVo> sgBookstoreVo = BeanCopyUtils.copyBeanList(sgbookstores, SgBookstoreVo.class);

        System.out.println("xzwflfl111111111");
        System.out.println(sgBookstoreVo);
        PageVo pageVo = new PageVo(sgBookstoreVo,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addBookstore(AddBookstoreDto addBookstoreDto) {
        SgBookstore sgBookstore = BeanCopyUtils.copyBean(addBookstoreDto,SgBookstore.class);
        save(sgBookstore);
        return ResponseResult.okResult();
    }
    /**我的书籍*/
    @Override
    public ResponseResult MyBookstore(Integer id, SgBookstore sgBookstore, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SgBookstore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SgBookstore::getUserId, id);
        //如果请求名字为null即为false则不进行该条件
        lambdaQueryWrapper.like(StringUtils.hasText(sgBookstore.getBookName()), SgBookstore::getBookName,sgBookstore.getBookName());

        //分页查询
        Page<SgBookstore> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        //查询categoryName
        List<SgBookstore> sgbookstores = page.getRecords();

       /* sgbookstores.stream()
                //@Accessors(chain = true)//在ArticleServiceImpl的articleList方法中的stream将set的void返回类型改成sgbookstore，如此返回就是当前sgbookstore对象本身
                .map(sgbookstore -> sgbookstore.setCategoryStr(sgBookstoreCategoryService.getById(sgbookstore.getCategory()).getName()))
                .collect(Collectors.toList());//Collectors.toList() 将流中的所有元素导出到一个列表( List )中
*/
        //封装查询结果
        List<SgBookstoreVo> sgBookstoreVo = BeanCopyUtils.copyBeanList(sgbookstores, SgBookstoreVo.class);

        PageVo pageVo = new PageVo(sgBookstoreVo,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }
    /**所有的书籍*/
    @Override
    public ResponseResult AllBookstore( SgBookstore sgBookstore, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SgBookstore> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果请求名字为null即为false则不进行该条件
        lambdaQueryWrapper.like(StringUtils.hasText(sgBookstore.getBookName()), SgBookstore::getBookName,sgBookstore.getBookName());

        //分页查询
        Page<SgBookstore> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        //查询categoryName
        List<SgBookstore> sgbookstores = page.getRecords();

       /* sgbookstores.stream()
                //@Accessors(chain = true)//在ArticleServiceImpl的articleList方法中的stream将set的void返回类型改成sgbookstore，如此返回就是当前sgbookstore对象本身
                .map(sgbookstore -> sgbookstore.setCategoryStr(sgBookstoreCategoryService.getById(sgbookstore.getCategory()).getName()))
                .collect(Collectors.toList());//Collectors.toList() 将流中的所有元素导出到一个列表( List )中
*/
        //封装查询结果
        List<SgBookstoreVo> sgBookstoreVo = BeanCopyUtils.copyBeanList(sgbookstores, SgBookstoreVo.class);

        PageVo pageVo = new PageVo(sgBookstoreVo,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }
}

