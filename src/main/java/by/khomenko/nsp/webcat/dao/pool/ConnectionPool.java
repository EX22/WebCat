package by.khomenko.nsp.webcat.dao.pool;

import by.khomenko.nsp.webcat.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {


    /**
     * Instance of logger that provides logging capability for this class'
     * performance.
     */
    private static final Logger LOGGER
            = LogManager.getLogger(ConnectionPool.class);

    private String url;
    private String user;
    private String password;
    private int maxSize;
    private int checkConnectionTimeout;

    /**
     * Tool for controlling access to a shared resource by multiple threads.
     */
    private final Lock lock = new ReentrantLock();

    private BlockingQueue<PooledConnection> freeConnections
            = new LinkedBlockingQueue<>();
    private Set<PooledConnection> usedConnections
            = new ConcurrentSkipListSet<>();

    private ConnectionPool() {
    }

    public Connection getConnection() throws PersistentException {
        lock.lock();
        PooledConnection connection = null;
        while (connection == null) {
            try {
                if (!freeConnections.isEmpty()) {
                    connection = freeConnections.take();
                    if (!connection.isValid(checkConnectionTimeout)) {
                        try {
                            connection.getConnection().close();
                        } catch (SQLException e) {
                            LOGGER.info("Closing connection ", e);
                        }
                        connection = null;
                    }
                } else if (usedConnections.size() < maxSize) {
                    connection = createConnection();
                } else {
                    LOGGER.error("The limit of number of database "
                            + "connections is exceeded");
                    lock.unlock();
                    throw new PersistentException();
                }
            } catch (SQLException e) {
                LOGGER.error("It is impossible to connect to a database", e);
                lock.unlock();
                throw new PersistentException(e);
            } catch (InterruptedException ex) {
                LOGGER.fatal("Thread interrupted ", ex);
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
        usedConnections.add(connection);
        LOGGER.debug(String.format("Connection was received from pool."
                        + " Current pool size: "
                        + "%d used connections; %d free connection",
                usedConnections.size(), freeConnections.size()));
        lock.unlock();
        return connection;
    }

    public void freeConnection(PooledConnection connection) {
        lock.lock();
        try {
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                LOGGER.debug(String.format("Connection was returned into pool."
                                + " Current pool "
                                + "size: %d used connections;"
                                + " %d free connection",
                        usedConnections.size(), freeConnections.size()));
            }
        } catch (SQLException e1) {
            LOGGER.warn("It is impossible to return database connection "
                    + "into pool", e1);
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
                LOGGER.error("Closing connection an exception occurred. ",e2);
            }
        } catch (InterruptedException ex) {
            LOGGER.fatal("Thread interrupted ", ex);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void init(String driverClass, String url, String user,
                     String password, int startSize, int maxSize,
                     int checkConnectionTimeout)
            throws PersistentException {

        lock.lock();
        try {
            destroy();
            Class.forName(driverClass);
            this.url = url;
            this.user = user;
            this.password = password;
            this.maxSize = maxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;
            for (int counter = 0; counter < startSize; counter++) {
                freeConnections.put(createConnection());
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.fatal("It is impossible to initialize connection"
                    + " pool", e);
            throw new PersistentException(e);
        } catch (InterruptedException ex) {
            LOGGER.fatal("Thread interrupted ", ex);
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    private static ConnectionPool instance = new ConnectionPool();

    public static ConnectionPool getInstance() {
        return instance;
    }

    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(url, user,
                password));
    }

    public void destroy() {
        lock.lock();
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
                LOGGER.error("Closing connection an exception occurred. ",e);
            }
        }
        usedConnections.clear();
        lock.unlock();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }
}
