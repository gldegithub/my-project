package org.leo.energy.service.boot.security.provider;

import java.io.Serializable;

/**
 * @Author:gonglong
 * @Date:2022/4/12 16:22
 */
public interface IClientDetails extends Serializable {

    /**
     * 客户端id.
     *
     * @return String.
     */
    String getClientId();

    /**
     * 客户端密钥.
     *
     * @return String.
     */
    String getClientSecret();

    /**
     * 客户端token过期时间
     *
     * @return Integer
     */
    Integer getAccessTokenValidity();

    /**
     * 客户端刷新token过期时间
     *
     * @return Integer
     */
    Integer getRefreshTokenValidity();

}