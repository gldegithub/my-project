package org.leo.energy.user.wrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:gonglong
 * @Date:2022/4/18 16:06
 */
public abstract class EntityToVOWrapper<E, V> {


    public abstract V entityVO(E entity);

    public List<V> listVO(List<E> list) {
        return list.stream().map(this::entityVO).collect(Collectors.toList());
    }

    public IPage<V> pageVO(IPage<E> pages) {
        List<V> records = this.listVO(pages.getRecords());
        IPage<V> pageVo = new Page<V>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);
        return pageVo;
    }
}
