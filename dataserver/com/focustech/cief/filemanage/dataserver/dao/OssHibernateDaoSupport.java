package com.focustech.cief.filemanage.dataserver.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OssHibernateDaoSupport<T> extends OssJdbcDaoSupport<T> {
    @Autowired
    @Qualifier("ossSessionFactory")
    private SessionFactory ossSessionFactory;

    @Override
    public final Session getCurrentSession() {
        Session session = ossSessionFactory.getCurrentSession();
        return session;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return ossSessionFactory;
    }
}
