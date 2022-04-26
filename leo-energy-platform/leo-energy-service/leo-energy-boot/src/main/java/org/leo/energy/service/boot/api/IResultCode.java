package org.leo.energy.service.boot.api;

import java.io.Serializable;

/**
 * @Author:gonglong
 * @Date:2022/4/12 16:07
 */
public interface IResultCode extends Serializable {

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return int
     */
    int getCode();

}
