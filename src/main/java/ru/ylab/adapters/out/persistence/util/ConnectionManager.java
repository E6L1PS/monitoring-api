package ru.ylab.adapters.out.persistence.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    /**
     * Получение соединения с базой данных.
     *
     * @return объект Connection для взаимодействия с базой данных
     * @throws RuntimeException в случае ошибки при установке соединения
     */
    public static Connection open() {
        try {
            return DriverManager.getConnection(
                    System.getenv(URL_KEY),
                    System.getenv(USERNAME_KEY),
                    System.getenv(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }
}
