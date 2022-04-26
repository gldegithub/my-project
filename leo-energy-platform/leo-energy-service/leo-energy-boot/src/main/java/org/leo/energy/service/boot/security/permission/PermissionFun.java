package org.leo.energy.service.boot.security.permission;

import org.leo.energy.service.boot.auth.AuthUser;
import org.leo.energy.common.constant.RoleConstant;
import org.leo.energy.common.utils.CollectionUtil;
import org.leo.energy.common.utils.Func;
import org.leo.energy.common.utils.StringUtil;
import org.leo.energy.service.boot.security.SecureUtil;

/**
 * @Author:gonglong
 * @Date:2022/4/13 14:36
 */
public class PermissionFun {

    /**
     * 放行所有请求
     *
     * @return {boolean}
     */
    public boolean permitAll() {
        return true;
    }

    /**
     * 只有超管角色才可访问
     *
     * @return {boolean}
     */
    public boolean denyAll() {
        return hasRole(RoleConstant.ADMIN);
    }

    /**
     * 判断是否有该角色权限
     *
     * @param role 单角色
     * @return {boolean}
     */
    public boolean hasRole(String role) {
        return hasAnyRole(role);
    }

    /**
     * 判断是否有该角色权限
     *
     * @param role 角色集合
     * @return {boolean}
     */
    public boolean hasAnyRole(String... role) {
        AuthUser user = SecureUtil.getUser();
        if (user != null) {
            String userRole = user.getRoleName();
            if (StringUtil.isBlank(userRole)) {
                return false;
            }
            String[] roles = Func.toStrArray(userRole);
            for (String r : role) {
                if (CollectionUtil.contains(roles, r)) {
                    return true;
                }
            }
        }
        return false;
    }
}
