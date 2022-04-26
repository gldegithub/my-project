package org.leo.energy.launch;

import org.leo.energy.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author:gonglong
 * @Date:2022/4/8 15:51
 */
public class LaunchApplication {

    public static SpringApplicationBuilder create(@Nullable ResourceLoader resourceLoader,
                                                  String appName, Class<?> source, String... args) {
        Assert.hasText(appName, "[appName]服务名不能为空");
        //使用默认的resourceLoader加载
        resourceLoader = (resourceLoader != null) ? resourceLoader
                : new DefaultResourceLoader(null);
        //获取生效的环境配置
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> profiles = Arrays.asList(activeProfiles);
        List<String> activeProfileList = new ArrayList<>(profiles);
        Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;
        SpringApplicationBuilder builder = new SpringApplicationBuilder(resourceLoader,source);
        String profile;
        if (activeProfileList.isEmpty()) {
            profile = "dev";
            activeProfileList.add(profile);
            //builder.profiles(profile);
        } else {
            if (activeProfileList.size() != 1) {
                throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) + "]");
            }
            profile = activeProfileList.get(0);
        }
        //String startJarPath = LaunchApplication.class.getClassLoader().getResource("/").getPath().split("!")[0];
        String startJarPath = LaunchApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String activePros = joinFun.apply(activeProfileList.toArray());
        System.out.printf("----启动中，读取到的环境变量:[%s]，jar地址:[%s]----%n", activePros, startJarPath);
        //配置默认属性
        Map<String,Object> defaults = new HashMap<>();
        List<LauncherService> launcherServices = SpringFactoriesLoader.loadFactories(LauncherService.class, resourceLoader.getClassLoader());
        launcherServices.forEach((launcherService) -> {
            launcherService.launcher(defaults,appName, profile);
        });
        builder.properties(defaults);
        builder.allowCircularReferences(true);
        return builder;
    }

    public static SpringApplicationBuilder create(String appName, Class<?> source, String... args) {
        return create(null,appName, source, args);
    }
}
