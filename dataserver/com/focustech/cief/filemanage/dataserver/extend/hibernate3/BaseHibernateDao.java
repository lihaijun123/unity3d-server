package com.focustech.cief.filemanage.dataserver.extend.hibernate3;

import java.io.Serializable;

public interface BaseHibernateDao<T> {
    public void insert(T t);

    public void update(T t);

    public void delete(Serializable id);

    public void delete(T t);

    public T select(Serializable id);

    public T pessimisticSelect(Serializable id);

    public void insertOrUpdate(T t);
}
