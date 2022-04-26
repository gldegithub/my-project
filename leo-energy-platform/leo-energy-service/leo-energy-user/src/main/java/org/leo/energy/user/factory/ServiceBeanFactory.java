package org.leo.energy.user.factory;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.leo.energy.user.service.IUserService;
import org.springframework.stereotype.Component;

/**
 * @Author:gonglong
 * @Date:2022/4/18 16:40
 */
@Component
@AllArgsConstructor
@Data
public class ServiceBeanFactory {
    private IUserService userService;
}
