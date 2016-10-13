package com.focustech.cief.filemanage.dataserver.extend.spring.jdbc.core;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.util.Assert;

public class JdbcTemplate4Session extends JdbcTemplate {
    private SessionFactory sessionFactory;

    public JdbcTemplate4Session() {
    }

    public JdbcTemplate4Session(DataSource dataSource, SessionFactory sessionFactory) {
        setDataSource(dataSource);
        this.sessionFactory = sessionFactory;
        setExceptionTranslator(new SQLStateSQLExceptionTranslator());// ʹ�ü򵥵�sql�������������ͨ�����Դ����
        afterPropertiesSet();
    }

    public JdbcTemplate4Session(DataSource dataSource, SessionFactory sessionFactory, boolean lazyInit) {
        setDataSource(dataSource);
        setLazyInit(lazyInit);
        this.sessionFactory = sessionFactory;
        setExceptionTranslator(new SQLStateSQLExceptionTranslator());// ʹ�ü򵥵�sql�������������ͨ�����Դ����
        afterPropertiesSet();
    }

    @Override
    public void afterPropertiesSet() {
        if (getDataSource() == null) {
            throw new IllegalArgumentException("Property 'dataSource' is required");
        }
        if (sessionFactory == null) {
            throw new IllegalArgumentException("Property 'sessionFactory' is required!!!!");
        }
        if (!isLazyInit()) {
            getExceptionTranslator();
        }
    }

    @Override
    public Object execute(ConnectionCallback action) throws DataAccessException {
        Assert.notNull(action, "Callback object must not be null");
        Connection con = ConnectionUtils.getConnection(sessionFactory);
        try {
            Connection conToUse = con;
            if (super.getNativeJdbcExtractor() != null) {
                // Extract native JDBC Connection, castable to OracleConnection or the like.
                conToUse = super.getNativeJdbcExtractor().getNativeConnection(con);
            }
            else {
                // Create close-suppressing Connection proxy, also preparing returned Statements.
                conToUse = createConnectionProxy(con);
            }
            return action.doInConnection(conToUse);
        }
        catch (SQLException ex) {
            throw getExceptionTranslator().translate("ConnectionCallback", getSql(action), ex);
        }
    }

    @Override
    public Object execute(StatementCallback action) throws DataAccessException {
        Assert.notNull(action, "Callback object must not be null");
        Connection con = ConnectionUtils.getConnection(sessionFactory);
        Statement stmt = null;
        try {
            Connection conToUse = con;
            if ((super.getNativeJdbcExtractor() != null)
                    && super.getNativeJdbcExtractor().isNativeConnectionNecessaryForNativeStatements()) {
                conToUse = super.getNativeJdbcExtractor().getNativeConnection(con);
            }
            stmt = conToUse.createStatement();
            applyStatementSettings(stmt);
            Statement stmtToUse = stmt;
            if (super.getNativeJdbcExtractor() != null) {
                stmtToUse = super.getNativeJdbcExtractor().getNativeStatement(stmt);
            }
            Object result = action.doInStatement(stmtToUse);
            handleWarnings(stmt.getWarnings());
            return result;
        }
        catch (SQLException ex) {
            // Release Connection early, to avoid potential connection pool deadlock
            // in the case when the exception translator hasn't been initialized yet.
            JdbcUtils.closeStatement(stmt);
            stmt = null;
            throw getExceptionTranslator().translate("StatementCallback", getSql(action), ex);
        }
        finally {
            JdbcUtils.closeStatement(stmt);
        }
    }

    @Override
    public Object execute(PreparedStatementCreator psc, PreparedStatementCallback action) throws DataAccessException {
        Assert.notNull(psc, "PreparedStatementCreator must not be null");
        Assert.notNull(action, "Callback object must not be null");
        if (logger.isDebugEnabled()) {
            String sql = getSql(psc);
            logger.debug("Executing prepared SQL statement" + (sql != null ? " [" + sql + "]" : ""));
        }
        Connection con = ConnectionUtils.getConnection(sessionFactory);
        PreparedStatement ps = null;
        try {
            Connection conToUse = con;
            if ((super.getNativeJdbcExtractor() != null)
                    && super.getNativeJdbcExtractor().isNativeConnectionNecessaryForNativePreparedStatements()) {
                conToUse = super.getNativeJdbcExtractor().getNativeConnection(con);
            }
            ps = psc.createPreparedStatement(conToUse);
            applyStatementSettings(ps);
            PreparedStatement psToUse = ps;
            if (super.getNativeJdbcExtractor() != null) {
                psToUse = super.getNativeJdbcExtractor().getNativePreparedStatement(ps);
            }
            Object result = action.doInPreparedStatement(psToUse);
            handleWarnings(ps.getWarnings());
            return result;
        }
        catch (SQLException ex) {
            // Release Connection early, to avoid potential connection pool deadlock
            // in the case when the exception translator hasn't been initialized yet.
            if (psc instanceof ParameterDisposer) {
                ((ParameterDisposer) psc).cleanupParameters();
            }
            String sql = getSql(psc);
            psc = null;
            JdbcUtils.closeStatement(ps);
            ps = null;
            throw getExceptionTranslator().translate("PreparedStatementCallback", sql, ex);
        }
        finally {
            if (psc instanceof ParameterDisposer) {
                ((ParameterDisposer) psc).cleanupParameters();
            }
            JdbcUtils.closeStatement(ps);
        }
    }

    @Override
    public Object execute(CallableStatementCreator csc, CallableStatementCallback action) throws DataAccessException {
        Assert.notNull(csc, "CallableStatementCreator must not be null");
        Assert.notNull(action, "Callback object must not be null");
        if (logger.isDebugEnabled()) {
            String sql = getSql(csc);
            logger.debug("Calling stored procedure" + (sql != null ? " [" + sql + "]" : ""));
        }
        Connection con = ConnectionUtils.getConnection(sessionFactory);
        CallableStatement cs = null;
        try {
            Connection conToUse = con;
            if (super.getNativeJdbcExtractor() != null) {
                conToUse = super.getNativeJdbcExtractor().getNativeConnection(con);
            }
            cs = csc.createCallableStatement(conToUse);
            applyStatementSettings(cs);
            CallableStatement csToUse = cs;
            if (super.getNativeJdbcExtractor() != null) {
                csToUse = super.getNativeJdbcExtractor().getNativeCallableStatement(cs);
            }
            Object result = action.doInCallableStatement(csToUse);
            handleWarnings(cs.getWarnings());
            return result;
        }
        catch (SQLException ex) {
            // Release Connection early, to avoid potential connection pool deadlock
            // in the case when the exception translator hasn't been initialized yet.
            if (csc instanceof ParameterDisposer) {
                ((ParameterDisposer) csc).cleanupParameters();
            }
            String sql = getSql(csc);
            csc = null;
            JdbcUtils.closeStatement(cs);
            cs = null;
            throw getExceptionTranslator().translate("CallableStatementCallback", sql, ex);
        }
        finally {
            if (csc instanceof ParameterDisposer) {
                ((ParameterDisposer) csc).cleanupParameters();
            }
            JdbcUtils.closeStatement(cs);
        }
    }

    /**
     * Determine SQL from potential provider object.
     *
     * @param sqlProvider object that's potentially a SqlProvider
     * @return the SQL string, or <code>null</code>
     * @see SqlProvider
     */
    protected static String getSql(Object sqlProvider) {
        if (sqlProvider instanceof SqlProvider) {
            return ((SqlProvider) sqlProvider).getSql();
        }
        else {
            return null;
        }
    }

    @Override
    protected void applyStatementSettings(Statement stmt) throws SQLException {
        int fetchSize = getFetchSize();
        if (fetchSize > 0) {
            stmt.setFetchSize(fetchSize);
        }
        int maxRows = getMaxRows();
        if (maxRows > 0) {
            stmt.setMaxRows(maxRows);
        }
        ConnectionUtils.applyTimeout(stmt, sessionFactory, getQueryTimeout());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
