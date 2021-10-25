package com.management.app.infrastructure.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class IocUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    private static final IocUtil INSTANCE = new IocUtil();

    private IocUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IocUtil.context = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return context.getBean(tClass);
    }

    public static IocUtil getInstance() {
        return INSTANCE;
    }
}
