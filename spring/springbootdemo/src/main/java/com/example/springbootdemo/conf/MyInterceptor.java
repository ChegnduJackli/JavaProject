package com.example.springbootdemo.conf;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

//自定义拦截器
@Slf4j
public class MyInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        log.info("====拦截到了方法：{}，在该方法执行之前执行====" + methodName);
        // 返回true才会继续执行，返回false则取消当前请求
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲 染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse
            response, Object handler, Exception ex) throws Exception {
        log.info("整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可 以做一些清理的工作了");
    }
}
