package org.leo.energy.service.boot.launcher;

import org.leo.energy.launch.service.LauncherService;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.core.Ordered;

import java.util.Map;

import static org.leo.energy.common.constant.LoggingConstant.LOG4J2_CONFIG;

/**
 * @Author:gonglong
 * @Date:2022/4/11 14:40
 */
public class BootstrapServiceImpl implements LauncherService {
    @Override
    public void launcher(Map<String, Object> defaultProperties, String appName, String profile) {
        //defaultProperties.put("spring.profiles.include", "boot");
    }
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

}
