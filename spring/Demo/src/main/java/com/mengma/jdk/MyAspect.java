package com.mengma.jdk;

//在该包下创建一个切面类 MyAspect，编辑后如下所示。
public class MyAspect {
    public void myBefore() {
        System.out.println("方法执行之前");
    }

    public void myAfter() {
        System.out.println("方法执行之后");
    }
}
