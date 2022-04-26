package org.leo.energy.service.boot.config;

import lombok.AllArgsConstructor;
import org.leo.energy.service.boot.props.MybatisPlusProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:gonglong
 * @Date:2022/4/11 16:51
 */
@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@MapperScan("org.leo.energy.**.mapper.**")
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MybatisPlusConfiguration {

}
