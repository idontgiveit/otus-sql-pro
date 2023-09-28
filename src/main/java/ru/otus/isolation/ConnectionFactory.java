package ru.otus.isolation;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionFactory {

    public static Connection connect(ConnectionSettings settings) throws Exception{
        Connection conn = DriverManager.getConnection(settings.url, settings.login, settings.pwd);
        conn.setTransactionIsolation(settings.transactionIsolation);
        conn.setAutoCommit(false);
        System.out.println("----> Connected to the PostgreSQL server successfully.");
        return conn;
    }
}
