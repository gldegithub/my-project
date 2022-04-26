package org.leo.energy.service.boot.security.conf;

import org.leo.energy.service.boot.security.AuthUserCustomizer;
import org.leo.energy.service.boot.security.ClientLoggerBuilder;
import org.leo.energy.service.boot.security.aspect.AuthAspect;
import org.leo.energy.service.boot.security.filter.AuthFilter;
import org.leo.energy.service.boot.security.interceptor.ClientInterceptor;
import org.leo.energy.service.boot.security.interceptor.SecureInterceptor;
import org.leo.energy.service.boot.security.prop.SecureProperties;
import org.leo.energy.service.boot.security.provider.ClientDetailsServiceImpl;
import org.leo.energy.service.boot.security.provider.IClientDetailsService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author:gonglong
 * @Date:2022/4/12 15:54
 */
@Order
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SecureProperties.class})
public class SecurityAutoConfiguration implements WebMvcConfigurer {

    private final SecureProperties secureProperties;

    private final JdbcTemplate jdbcTemplate;

    public SecurityAutoConfiguration(SecureProperties secureProperties, JdbcTemplate jdbcTemplate) {
        this.secureProperties = secureProperties;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public AuthFilter AuthFilter() {
        return new AuthFilter(secureProperties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        secureProperties.getClient().forEach(
                cs -> registry.addInterceptor(new ClientInterceptor(cs.getClientId()))
                        .addPathPatterns(cs.getPathPatterns()));
        if (secureProperties.isEnableSecureInterceptor()) {
            registry.addInterceptor(new SecureInterceptor())
                    .excludePathPatterns(secureProperties.getSkipUrl());
            WebMvcConfigurer.super.addInterceptors(registry);
        }
    }


    @Bean
    public AuthAspect authAspect() {
        return new AuthAspect();
    }

    @Bean
    public ClientLoggerBuilder ClientUtil(ObjectProvider<AuthUserCustomizer> authUserCustomizer) {
        ClientLoggerBuilder clientLoggerBuilder = new ClientLoggerBuilder();
        authUserCustomizer.orderedStream().forEach((customizer)->customizer.customize(clientLoggerBuilder));
        return clientLoggerBuilder;
    }

    @Bean
    @ConditionalOnMissingBean(IClientDetailsService.class)
    public IClientDetailsService clientDetailsService() {
        return new ClientDetailsServiceImpl(jdbcTemplate);
    }
}
