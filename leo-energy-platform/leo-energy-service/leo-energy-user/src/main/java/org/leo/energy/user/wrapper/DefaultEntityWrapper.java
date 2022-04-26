package org.leo.energy.user.wrapper;

import org.leo.energy.common.utils.BeanUtil;
import org.springframework.core.GenericTypeResolver;

/**
 * @Author:gonglong
 * @Date:2022/4/18 17:02
 */
public class DefaultEntityWrapper<E,V> extends  EntityToVOWrapper<E, V>{

    final Class<V> typeParameterClass;

    public DefaultEntityWrapper(Class<V> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public V entityVO(E entity) {
        return  BeanUtil.copy(entity, typeParameterClass);
    }
}
