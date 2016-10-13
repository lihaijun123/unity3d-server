package com.focustech.cief.filemanage.dataserver.extend.spring.jdbc.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.impl.SessionImpl;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

/**
 * <li>JDBC���Ӹ�����</li>
 *
 * @author yangpeng 2009-3-19 ����02:07:55
 * @since VFrw1.01
 */
public abstract class ConnectionUtils {
    /**
     * <li>ͨ��hibernate SessionFactory���JDBC Connection</li>
     *
     * @param sessionFactory hibernate SessionFactory instance.won't be null.
     * @return Connection
     * @throws RuntimeException session type is not {@link org.hibernate.impl.SessionImpl}.
     * @see org.hibernate.impl.SessionImpl
     */
    public static Connection getConnection(SessionFactory sessionFactory) {
        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        if (session instanceof SessionImpl) {
            SessionImpl sessionImpl = (SessionImpl) session;
            return sessionImpl.getJDBCContext().getConnectionManager().borrowConnection();
        }
        else {
            throw new RuntimeException("session type isn't expected sessionImpl!");
        }
    }

    /**
     * Apply the specified timeout - overridden by the current transaction timeout, if any - to the given JDBC Statement
     * object.
     *
     * @param stmt the JDBC Statement object
     * @param SessionFactory the SessionFactory that the Connection was obtained from
     * @param timeout the timeout to apply (or 0 for no timeout outside of a transaction)
     * @throws SQLException if thrown by JDBC methods
     * @see java.sql.Statement#setQueryTimeout
     */
    public static void applyTimeout(Statement stmt, SessionFactory sessionFactory, int timeout) throws SQLException {
        Assert.notNull(stmt, "No Statement specified");
        Assert.notNull(sessionFactory, "No SessionFactory specified");
        SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        if ((holder != null) && holder.hasTimeout()) {
            // Remaining transaction timeout overrides specified value.
            stmt.setQueryTimeout(holder.getTimeToLiveInSeconds());
        }
        else if (timeout > 0) {
            // No current transaction timeout -> apply specified value.
            stmt.setQueryTimeout(timeout);
        }
    }
}
