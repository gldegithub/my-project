package org.leo.energy.service.boot.security;

import org.leo.energy.service.boot.auth.AuthUser;

/**
 * 后期可以通过这个ClientLoggerBuilder来查看当前用户信息
 * @Author:gonglong
 * @Date:2022/4/13 10:35
 */
public class ClientLoggerBuilder {

    @Override
    public String toString() {
        return "ClientLoggerBuilder{" +
                "authUser=" + authUser +
                '}';
    }

    private  AuthUser authUser;


    public AuthUser getAuthUser() {
        return authUser;
    }

    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }


}
