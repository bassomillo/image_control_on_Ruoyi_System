package com.ruoyi.project.annotation;

import java.lang.annotation.*;

/**
 * Created by zhaojy201 on 2020/09/24.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenJudge {

    int logType() default 100;

    int module() default 100;

    /**
     * 操作说明
     * @return
     */
    String description() default "";

}
