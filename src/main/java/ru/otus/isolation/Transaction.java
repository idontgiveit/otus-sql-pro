package ru.otus.isolation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CyclicBarrier;


public abstract class Transaction {
    protected final int number;

    protected final CyclicBarrier barrier;

    protected final Connection connection;

    public Transaction(int number, CyclicBarrier barrier, Connection connection) {
        this.number = number;
        this.barrier= barrier;
        this.connection = connection;
    }

    public abstract void perform(int id, String name) throws Exception;
    protected void printActualValues(String title) throws Exception {
        PreparedStatement prep = connection.prepareStatement("select * from names");
        ResultSet rs = prep.executeQuery();
        log(title + "----------------------");
        while (rs.next()) {
            log(rs.getInt(1) + " " + rs.getString(2));
        }
        System.out.println(header() + "----------------------");
    }

    private String header() {
        return "--- Transaction " + number + " -->";
    }

    protected void log(Object logStr) {
        System.out.println(header() + logStr);
    }
}
