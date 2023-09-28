package ru.otus.isolation.races;

import ru.otus.isolation.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CyclicBarrier;


public class Serialization extends Transaction {
    public Serialization(final int number, final CyclicBarrier barrier, final Connection connection) {
        super(number, barrier, connection);
    }

    @Override
    public void perform(int id, String name) throws Exception {
        barrier.await();

        PreparedStatement prep = connection.prepareStatement("select person_rate from names where id = 1 for update");
        ResultSet rs = prep.executeQuery();

        rs.next();
        int ctr = rs.getInt(1);

        prep = connection.prepareStatement("update names set person_rate = ? where id = 1");
        prep.setInt(1, ctr + 1);
        prep.executeUpdate();

        connection.commit();
    }
}
