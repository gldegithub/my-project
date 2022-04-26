package org.leo.energy.service.boot.publisher;

import org.leo.energy.common.utils.*;
import org.leo.energy.service.boot.log.event.ErrorLogEvent;
import org.leo.energy.service.boot.log.model.LogError;
import org.leo.energy.service.boot.log.utils.LogUtil;
import org.leo.energy.service.boot.security.SecureUtil;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:gonglong
 * @Date:2022/4/20 18:04
 */
public class ErrorPublisher {

    public static void publishEvent(Throwable error) {
        HttpServletRequest request = WebUtil.getRequest();
        LogError logError = new LogError();
        LogUtil.populateErrorInfo(error, logError);
        LogUtil.populateRequestInfo(request, logError);
        SpringUtil.publishEvent(new ErrorLogEvent(logError));
    }
}
