package org.leo.energy.service.boot.security;

/**
 * @Author:gonglong
 * @Date:2022/4/13 10:24
 */

@FunctionalInterface
public interface AuthUserCustomizer {

    void customize( ClientLoggerBuilder clientLoggerBuilder);

}
