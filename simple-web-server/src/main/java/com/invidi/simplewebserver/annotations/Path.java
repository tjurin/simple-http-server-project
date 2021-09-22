package com.invidi.simplewebserver.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.invidi.simplewebserver.model.RequestMethod;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {
    String value() default "";

    RequestMethod method() default RequestMethod.GET;

    String[] params() default {};

    String[] headers() default {};
}
