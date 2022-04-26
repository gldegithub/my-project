package org.leo.energy.launch.service.impl;

import org.leo.energy.common.constant.AppConstant;
import org.leo.energy.launch.service.LauncherService;

import java.util.Map;
import java.util.Properties;

/**
 * @Author:gonglong
 * @Date:2022/4/8 17:26
 */
public class DefaultLauncherServiceImpl implements LauncherService {
    @Override
    public void launcher( Map<String, Object> defaults, String appName, String profile) {
        //设置系统属性
        Properties properties = System.getProperties();
        properties.setProperty("spring.application.name", appName);
        //默认Properties
        defaults.put("spring.application.name", appName);
        defaults.put("spring.profiles.active", profile);
        defaults.put("leo.energy.env", profile);
        defaults.put("leo.energy.name", appName);
        defaults.put("leo.energy.dev-mode", !"prod".equals(profile));
        defaults.put("leo.energy.version", AppConstant.APPLICATION_VERSION);
        defaults.put("spring.main.allow-bean-definition-overriding", true);
        defaults.put("spring.cloud.nacos.discovery.server-addr", "127.0.0.1:8848");
        defaults.put("spring.cloud.nacos.config.server-addr", "127.0.0.1:8848");
        defaults.put("spring.cloud.nacos.config.prefix", "leo-energy");
        defaults.put("spring.cloud.nacos.config.file-extension", "yaml");
        defaults.put("spring.cloud.sentinel.transport.dashboard", "127.0.0.1:8858");
        defaults.put("spring.cloud.alibaba.seata.tx-service-group", appName.concat("-group"));
    }
}
