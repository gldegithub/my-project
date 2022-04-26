package org.leo.energy.service;

import org.leo.energy.launch.LaunchApplication;

import static org.leo.energy.common.constant.AppConstant.APPLICATION_METER_NAME;

/**
 * @Author:gonglong
 * @Date:2022/4/11 17:17
 */
public class MeterApplication{
    public static void main(String[] args) {
        LaunchApplication.create(APPLICATION_METER_NAME, MeterApplication.class, args).run();
    }
}
