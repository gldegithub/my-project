package org.leo.energy.auth.launcher;

import org.leo.energy.launch.service.LauncherService;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.core.Ordered;

import java.util.Map;

import static org.leo.energy.common.constant.LoggingConstant.LOG4J2_CONFIG;

/**
 * @Author:gonglong
 * @Date:2022/4/11 14:40
 */
public class AuthLauncherServiceImpl implements LauncherService {
    @Override
    public void launcher(Map<String, Object> defaultProperties, String appName, String profile) {
        //默认使用log4j2日志配置
        defaultProperties.put(LoggingApplicationListener.CONFIG_PROPERTY, LOG4J2_CONFIG);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

}
