package com.kklv.arg_apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lvzhendong
 * @data 2023/11/20
 * @description
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface ArgumentsField {
    String value() default "";

}
