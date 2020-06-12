package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * @author ：jyk
 * @date ：Created in 2019/11/12 20:53
 * @description：
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface ServiceLimit {
    /**
     * 描述
     */
    String description()  default "";

    /**
     * key
     */
    String key() default "";

    /**
     * 类型
     */
    LimitType limitType() default LimitType.CUSTOMER;

    enum LimitType {
        /**
         * 自定义key
         */
        CUSTOMER,
        /**
         * 根据请求者IP
         */
        IP
    }
}
