package com.management.app.infrastructure.config;

import com.management.app.infrastructure.utils.IocUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * IocUtil工具类需要被spring接管才能获取到spring的bean
 */
@Configuration
public class IocToolsAutoConfiguration {

    @Bean
    @Lazy(false)
    @ConditionalOnMissingBean(name = {"iocUtil"})
    public IocUtil iocUtil() {
        return IocUtil.getInstance();
    }
}
