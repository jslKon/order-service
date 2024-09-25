package com.example.coffeeshop.common.loghelper;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogExecutionTime {

    String messages() default "";

    String[] params() default "";
}
