package ru.ylab.adapters.util;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Утилитарный класс для управления соединением с базой данных.
 * Создан: 31.01.2024.
 *
 * @author Pesternikov Danil
 */
public final class ConnectionManager {

    /**
     * Ключи для доступа к переменным окружения, содержащим информацию о подключении к базе данных.
     */
    private static final String URL_KEY = "JDBC_URL";
    private static final String USERNAME_KEY = "POSTGRES_USER";
    private static final String PASSWORD_KEY = "POSTGRES_PASSWORD";
    private static final int DEFAULT_POOL_SIZE = 10;

    private static BlockingQueue<Connection> pool;

    static {
        initConnectionPool();
    }

    private static void initConnectionPool() {
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

    public static Connection get() {
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
    private static Connection open() {
        try {
            var url = System.getenv(URL_KEY);
            var username = System.getenv(USERNAME_KEY);
            var password = System.getenv(PASSWORD_KEY);

            // Для интеграционного тестирования:
            if (url == null || username == null || password == null) {
                url = System.getProperty(URL_KEY);
                username = System.getProperty(USERNAME_KEY);
                password = System.getProperty(PASSWORD_KEY);
            }
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }
}
