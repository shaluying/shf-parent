package com.shaluy.service.impl;

import com.shaluy.mapper.BaseMapper;
import com.shaluy.service.BaseService;
import com.shaluy.util.CastUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected abstract BaseMapper<T> getEntityDao();

    public Integer insert(T t) {
        return getEntityDao().insert(t);
    }

    public void delete(Long id) {
        getEntityDao().delete(id);
    }

    public Integer update(T t) {
        return getEntityDao().update(t);
    }

    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    public PageInfo<T> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 3);
        //开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<T>(getEntityDao().findPage(filters), 5);
    }
}
