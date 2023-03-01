package com.shaluy.interceptor;

import com.shaluy.entity.UserInfo;
import com.shaluy.result.Result;
import com.shaluy.result.ResultCodeEnum;
import com.shaluy.util.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //此方法为拦截器方法，在执行controller方法前执行
        //获取session域中userInfo
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("userInfo");

        //判断用户是否登录
        if (userInfo == null){
            Result result = Result.build("还没有登录", ResultCodeEnum.LOGIN_AUTH);
            //利用webUtil工具将result结果相应到前端
            WebUtil.writeJSON(response,result);
            return false;
        }

        //调用正常的控制层方法
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
