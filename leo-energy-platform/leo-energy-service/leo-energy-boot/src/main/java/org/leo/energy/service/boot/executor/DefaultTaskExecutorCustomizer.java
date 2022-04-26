package org.leo.energy.service.boot.executor;

import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @Author:gonglong
 * @Date:2022/4/24 9:27
 */
public class DefaultTaskExecutorCustomizer implements TaskExecutorCustomizer {
    @Override
    public void customize(ThreadPoolTaskExecutor taskExecutor) {
        taskExecutor.setKeepAliveSeconds(30);
        //可更改任务的最大存储容量
        //taskExecutor.setQueueCapacity(50000);
    }
}
