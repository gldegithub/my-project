package org.leo.energy.service.boot.security.prop;

import lombok.Data;
import org.leo.energy.service.boot.security.auth.AuthProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:gonglong
 * @Date:2022/4/12 15:56
 */

@ConfigurationProperties(prefix = "service.secure")
    public class SecureProperties {

    private  final List<String> skipUrl = new ArrayList<>();
    private  List<ClientSecure> client = new ArrayList<>();
    private  boolean enableSecureInterceptor = false;

    public boolean isEnableSecureInterceptor() {
        return enableSecureInterceptor;
    }

    public void setEnableSecureInterceptor(boolean enableSecureInterceptor) {
        this.enableSecureInterceptor = enableSecureInterceptor;
    }

    public SecureProperties() {
    }

    public List<ClientSecure> getClient() {
        return client;
    }

    public void setClient(List<ClientSecure> client) {
        this.client = client;
    }

    public List<String> getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(List<String> skipUrl) {
        this.skipUrl.addAll(skipUrl) ;
        this.skipUrl.addAll(AuthProvider.getDefaultSkipUrl());
    }

}
