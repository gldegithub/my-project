package org.leo.energy.launch.config;

import lombok.AllArgsConstructor;
import org.leo.energy.launch.prop.LaunchParamsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @Author:gonglong
 * @Date:2022/4/11 15:39
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
        LaunchParamsProperties.class
})
public class LaunchParamsAutoConfiguration {

}
