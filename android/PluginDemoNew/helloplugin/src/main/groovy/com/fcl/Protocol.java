package com.fcl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by galio.fang on 18-8-14
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Protocol {
    String value();
}
