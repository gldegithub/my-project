package org.leo.energy.user;

import org.leo.energy.launch.LaunchApplication;

import static org.leo.energy.common.constant.AppConstant.APPLICATION_USER_NAME;

/**
 * @Author:gonglong
 * @Date:2022/4/13 16:02
 */
public class UserApplication {
    public static void main(String[] args) {
        LaunchApplication.create(APPLICATION_USER_NAME, UserApplication.class, args).run();
    }
}
