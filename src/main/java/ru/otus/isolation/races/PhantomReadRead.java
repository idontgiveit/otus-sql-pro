package ru.otus.isolation.races;

import ru.otus.isolation.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CyclicBarrier;


public class PhantomReadRead extends Transaction {
    public PhantomReadRead(final int number, final CyclicBarrier barrier, final Connection connection) {
        super(number, barrier, connection);
    }

    @Override
    public void perform(int id, String name) throws Exception {
        PreparedStatement prep = connection.prepareStatement("select sum(person_rate) from names");
        ResultSet rs = prep.executeQuery();

        rs.next();
        log(rs.getInt(1));
        barrier.await();

        rs = prep.executeQuery();
        rs.next();
        log(rs.getInt(1));
    }
}
