package ru.ylab.adapters.util;

import ru.ylab.annotations.Singleton;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Утилитарный класс для управления соединением с базой данных.
 * Создан: 31.01.2024.
 *
 * @author Pesternikov Danil
 */
@Singleton
public final class ConnectionManager {

    /**
     * Ключи для доступа к переменным окружения, содержащим информацию о подключении к базе данных.
     */
    private final String URL_KEY;
    private final String USERNAME_KEY;
    private final String PASSWORD_KEY;
    private final int DEFAULT_POOL_SIZE;
    private BlockingQueue<Connection> pool;

    public ConnectionManager() {
        ResourceBundle resource = ResourceBundle.getBundle("application");
        this.URL_KEY = resource.getString("url");
        this.USERNAME_KEY = resource.getString("username");
        this.PASSWORD_KEY = resource.getString("password");
        this.DEFAULT_POOL_SIZE = Integer.parseInt(resource.getString("poolsize"));
        initConnectionPool();
    }

    public ConnectionManager(String URL_KEY, String USERNAME_KEY, String PASSWORD_KEY, int DEFAULT_POOL_SIZE) {
        this.URL_KEY = URL_KEY;
        this.USERNAME_KEY = USERNAME_KEY;
        this.PASSWORD_KEY = PASSWORD_KEY;
        this.DEFAULT_POOL_SIZE = DEFAULT_POOL_SIZE;
        initConnectionPool();
    }

    private void initConnectionPool() {
        int poolSize = DEFAULT_POOL_SIZE;
        pool = new ArrayBlockingQueue<>(poolSize);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < poolSize; i++) {
            Connection connection = open();
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection) proxy) : method.invoke(connection, args)
            );

            pool.add(proxyConnection);
        }

    }

    public Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получение соединения с базой данных.
     *
     * @return объект Connection для взаимодействия с базой данных
     * @throws RuntimeException в случае ошибки при установке соединения
     */
    private Connection open() {
        try {
            return DriverManager.getConnection(URL_KEY, USERNAME_KEY, PASSWORD_KEY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
