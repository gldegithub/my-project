package org.leo.energy.user.wrapper;

import org.leo.energy.common.utils.BeanUtil;

/**
 * @Author:gonglong
 * @Date:2022/4/25 18:47
 */
public class WrapperUtil<E,V>  extends DefaultEntityWrapper<E,V> {

    public WrapperUtil(Class<V> typeParameterClass) {
        super(typeParameterClass);
    }

/*    @Override
    public V entityUserVo(E entity) {
        return  BeanUtil.copy(entity, typeParameterClass);
    }*/
}