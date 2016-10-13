package com.focustech.cief.filemanage.dataserver.extend.hibernate3;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DummyDataSource implements DataSource {
    /*
     * (non-Javadoc)
     * @see javax.sql.DataSource#getConnection()
     */
    public Connection getConnection() throws SQLException {
        throw new RuntimeException("can't use this dummy datasource!");
    }

    /*
     * (non-Javadoc)
     * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
     */
    public Connection getConnection(String username, String password) throws SQLException {
        throw new RuntimeException("can't use this dummy datasource!");
    }

    /*
     * (non-Javadoc)
     * @see javax.sql.CommonDataSource#getLogWriter()
     */
    public PrintWriter getLogWriter() throws SQLException {
        throw new RuntimeException("can't use this dummy datasource!");
    }

    /*
     * (non-Javadoc)
     * @see javax.sql.CommonDataSource#getLoginTimeout()
     */
    public int getLoginTimeout() throws SQLException {
        throw new RuntimeException("can't use this dummy datasource!");
    }

    /*
     * (non-Javadoc)
     * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
     */
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new RuntimeException("can't use this dummy datasource!");
    }

    /*
     * (non-Javadoc)
     * @see javax.sql.CommonDataSource#setLoginTimeout(int)
     */
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new RuntimeException("can't use this dummy datasource!");
    }

    /*
     * (non-Javadoc)
     * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
     */
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new RuntimeException("can't use this dummy datasource!");
    }

    /*
     * (non-Javadoc)
     * @see java.sql.Wrapper#unwrap(java.lang.Class)
     */
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new RuntimeException("can't use this dummy datasource!");
    }
}
