package com.xzw;


import com.xzw.domain.ResponseResult;
import com.xzw.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**测试类的包名与启动类的包名不一致时需要加：路径*/


@SpringBootTest
public class TestService {
    @Autowired
    private CommentService commentService;

    @Test
    public void ComList(){
        System.out.println("cao");
        ResponseResult responseResult = commentService.commentList( "0",  3L,  1,  10);
        System.out.println("结果如下："+responseResult.getData());
    }

}