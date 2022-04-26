package org.leo.energy.user.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.leo.energy.common.utils.SpringUtil;
import org.leo.energy.user.entity.User;
import org.leo.energy.user.factory.ServiceBeanFactory;
import org.leo.energy.user.service.IUserService;
import org.leo.energy.user.vo.UserVO;
import org.springframework.beans.factory.BeanFactory;

import java.beans.beancontext.BeanContext;

/**
 * @Author:gonglong
 * @Date:2022/4/18 16:03
 */
public class UserWrapper extends DefaultEntityWrapper<User, UserVO>  {

    private final static ServiceBeanFactory serviceBeans;

    static {
        serviceBeans = SpringUtil.getBean(ServiceBeanFactory.class);
    }

    public UserWrapper(Class<UserVO> userVOClass) {
        super(userVOClass);
    }

    public static UserWrapper build() {
        return new UserWrapper(UserVO.class);
    }

    @Override
    public UserVO entityVO(User entity) {
        UserVO userVO = super.entityVO(entity);
        return userVO;
    }
}
