package com.focustech.cief.filemanage.dataserver.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.focustech.cief.filemanage.dataserver.extend.hibernate3.AbstractHibernateDaoSupport;

public abstract class OssJdbcDaoSupport<T> extends AbstractHibernateDaoSupport<T> implements InitializingBean {
    private SimpleJdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("ossDataSource")
    private DataSource ossDataSource;

    public void init() {
        if (null == jdbcTemplate) {
            if (ossDataSource == null) {
                throw new IllegalArgumentException("ossDataSource is not prepared!");
            }
            else {
                jdbcTemplate = new SimpleJdbcTemplate(ossDataSource);
            }
        }
    }

    public SimpleJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
