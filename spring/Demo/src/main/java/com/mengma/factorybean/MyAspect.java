package com.mengma.factorybean;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

//创建切面类 MyAspect
//Spring通知类型及使用ProxyFactoryBean创建AOP代理
//org.aopalliance.intercept.MethodInterceptor（环绕通知）
//在方法前后自动执行的通知称为环绕通知，可以应用于日志、事务管理等功能。
public class MyAspect implements MethodInterceptor {
    public Object invoke(MethodInvocation mi) throws Throwable {
        System.out.println("方法执行之前");
        // 执行目标方法
        Object obj = mi.proceed();
        System.out.println("方法执行之后");
        return obj;
    }
}
