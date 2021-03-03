package com.ruoyi.project.tool;

import org.springframework.stereotype.*;
import org.springframework.context.*;

import java.util.*;

/**
 * 获取Spring Bean工具类
 * Created by dong on 2019/4/14.
 */
@Component
@SuppressWarnings("unchecked")
public class SpringContextHolder implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    public void setApplicationContext(final ApplicationContext applicationContext) {

        SpringContextHolder.applicationContext = applicationContext;

    }

    public static ApplicationContext getApplicationContext() {

        checkApplicationContext();

        return SpringContextHolder.applicationContext;

    }

    public static <T> T getBean(final String name) {

        checkApplicationContext();

        return (T)SpringContextHolder.applicationContext.getBean(name);

    }

    public static <T> T getBean(Class<T> clazz) {

        checkApplicationContext();

        @SuppressWarnings("rawtypes")
        Map beanMaps = SpringContextHolder.applicationContext.getBeansOfType((Class)clazz);

        if (beanMaps != null && !beanMaps.isEmpty()) {

            return (T) beanMaps.values().iterator().next();
        }
        return null;

    }

    public static boolean containsBean(String name) {

        return SpringContextHolder.applicationContext.containsBean(name);

    }

    private static void checkApplicationContext() {

        if (SpringContextHolder.applicationContext == null) {

            throw new IllegalStateException("未获取到实例对象");

        }

    }

}