package com.example.springbootdemo.service;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
@Inherited
public @interface OrgField {

    int id();
}

