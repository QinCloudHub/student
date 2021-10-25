package com.management.app.support;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.app.core.mapperwrapper.AccountQuery;
import com.management.app.core.model.entity.Account;
import com.management.app.infrastructure.consts.AppConst;
import com.management.app.infrastructure.utils.IocUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;

/**
 * 自定义拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * jackson的json转换mapper，spring自带的bean
     */
    private static final ObjectMapper objectMapper = IocUtil.getBean(ObjectMapper.class);

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(Logger.class);

    /**
     * 在请求处理之前调用，Controller方法之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是登录接口不拦截
        if ("/login".equals(request.getServletPath())) {
            return true;
        }
        // 获取authHeader头
        String authHeader = request.getHeader(AppConst.AUTH_HEADER);
        if (authHeader != null) {
            String auth = new String(Base64.getDecoder().decode(authHeader));
            String[] strings = auth.split("\\|");
            String username = strings[0];
            String password = strings[1];
            if (AccountQuery.getBy(username) != null) {
                Account account = AccountQuery.getBy(username);
                if (password.equals(account.getPassword())) {
                    logger.info("header验证成功");
                    return true;
                }
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(objectMapper.convertValue(Rest.refused("没有登录"), JsonNode.class).toPrettyString());
        return false;
    }

    /**
     * 请求处理之后进行调用，在视图被渲染之前，controller调用方法之后
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束之后被调用，也就是DispatcherServlet 渲染了对应的视图之后执行，主要用于清理资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
