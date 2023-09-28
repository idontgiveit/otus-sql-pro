package ru.otus.isolation;

import java.sql.Connection;


public class ConnectionSettings {
    public String url = "jdbc:postgresql://localhost:5432/otus";
    public String login = "test";
    public String pwd = "test";
    public int transactionIsolation = Connection.TRANSACTION_READ_COMMITTED;
}
