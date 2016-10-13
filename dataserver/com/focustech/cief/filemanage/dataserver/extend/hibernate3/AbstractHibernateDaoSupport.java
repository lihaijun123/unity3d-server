package com.focustech.cief.filemanage.dataserver.extend.hibernate3;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.focustech.cief.filemanage.dataserver.common.utils.GenericsUtil;

@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDaoSupport<T> {
    protected Log log ;
    protected Class<T> entityClass = GenericsUtil.getSuperClassGenricType(getClass());

    public abstract Session getCurrentSession();
    public abstract SessionFactory getSessionFactory();

    public T select(Serializable id) {
        return (T) getCurrentSession().get(entityClass, id);
    }

    public T pessimisticSelect(Serializable id) {
        return (T) getCurrentSession().get(entityClass, id, LockMode.UPGRADE);
    }

    public void insert(T t) {
        getCurrentSession().save(t);
    }

    public void delete(T t) {
        getCurrentSession().delete(t);
    }

    public void delete(Serializable id) {
        T t = select(id);
        if (null != t) {
            getCurrentSession().delete(t);
        }
    }

    public void update(T t) {
        getCurrentSession().merge(t);
    }

    public void insertOrUpdate(T t) {
        getCurrentSession().saveOrUpdate(t);
    }
}
