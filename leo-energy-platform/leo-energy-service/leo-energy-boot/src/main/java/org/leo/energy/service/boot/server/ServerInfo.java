package org.leo.energy.service.boot.server;

/**
 * @Author:gonglong
 * @Date:2022/4/21 10:20
 */

import org.leo.energy.common.utils.StringPool;
import org.leo.energy.service.boot.log.utils.INetUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.DispatcherServlet;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerInfo implements SmartInitializingSingleton , EnvironmentAware {

    private final ServerProperties serverProperties;
    private Environment env;
    private String hostName;
    private String ip;
    private Integer port;
    private String ipWithPort;
    private String appName;

    public ServerInfo(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public void afterSingletonsInstantiated() {
        try {
            //String hostIp = INetUtil.getHostIp();
            this.ip = InetAddress.getLocalHost().getHostAddress();
            this.hostName = InetAddress.getLocalHost().getHostName();
            this.port = serverProperties.getPort();
            this.ipWithPort = this.ip + StringPool.COLON + this.port;
            this.appName = env.getProperty("spring.application.name");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public Environment getEnv() {
        return env;
    }

    public String getAppName() {
        return appName;
    }

    @Override
    public void setEnvironment(@Nullable Environment environment) {
        this.env = environment;
    }

    public ServerProperties getServerProperties() {
        return serverProperties;
    }

    public String getHostName() {
        return hostName;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public String getIpWithPort() {
        return ipWithPort;
    }

}
