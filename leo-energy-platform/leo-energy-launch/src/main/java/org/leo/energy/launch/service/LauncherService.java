package org.leo.energy.launch.service;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.Ordered;

import java.util.Map;

/**
 * @Author:gonglong
 * @Date:2022/4/8 16:47
 */
public interface LauncherService  extends Ordered, Comparable<LauncherService> {

    /**
     * 启动时 处理 SpringApplicationBuilder
     * @param defaultProperties defaultLaunchProperties
     * @param appName SpringApplicationAppName
     * @param profile SpringApplicationProfile
     */
    void launcher(Map<String, Object> defaultProperties, String appName, String profile);

    /**
     * 如果springboot排序规则不匹配的话，可动态修改
     *
     * @return order
     */
    @Override
    default int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 对比排序
     *
     * @param o LauncherService
     * @return compare
     */
    @Override
    default int compareTo(LauncherService o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }
}
