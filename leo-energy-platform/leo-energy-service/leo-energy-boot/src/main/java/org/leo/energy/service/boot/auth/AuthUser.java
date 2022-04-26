package org.leo.energy.service.boot.auth;

import lombok.Data;

/**
 * @Author:gonglong
 * @Date:2022/4/13 9:49
 */
@Data
public class AuthUser {

    private static final long serialVersionUID = 1L;
    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 昵称
     */
    private String userName;
    /**
     * 账号
     */
    private String account;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色名
     */
    private String roleName;

}
