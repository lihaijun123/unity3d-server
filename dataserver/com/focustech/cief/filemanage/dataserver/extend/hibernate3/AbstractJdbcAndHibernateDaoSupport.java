package com.focustech.cief.filemanage.dataserver.extend.hibernate3;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.InitializingBean;

import com.focustech.cief.filemanage.dataserver.extend.spring.jdbc.core.JdbcTemplate4Session;

public abstract class AbstractJdbcAndHibernateDaoSupport<T> extends AbstractHibernateDaoSupport<T> implements
InitializingBean {
    private JdbcTemplate4Session jdbcTemplate;
    private DataSource ossDataSource = new DummyDataSource();
    protected Log log;

    public void init() {
        if (null == jdbcTemplate) {
            if (ossDataSource == null) {
                throw new IllegalArgumentException("ossDataSource is not prepared!");
            }
            else {
                jdbcTemplate = new JdbcTemplate4Session(ossDataSource, getSessionFactory());
            }
        }
    }

    public JdbcTemplate4Session getJdbcTemplate() {
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
