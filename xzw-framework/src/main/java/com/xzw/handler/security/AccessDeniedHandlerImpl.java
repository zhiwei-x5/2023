package com.xzw.handler.security;

import com.alibaba.fastjson.JSON;
import com.xzw.domain.ResponseResult;
import com.xzw.enums.AppHttpCodeEnum;
import com.xzw.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**响应的格式肯定是不符合我们项目的接口规范的。所以需要自定义异常处理。*/
@Component
/**授权失败*/
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        /**NO_OPERATOR_AUTH(403,"无权限操作"),*/
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
