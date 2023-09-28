package ru.otus.isolation.races;

import ru.otus.isolation.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CyclicBarrier;


public class NonRepeatebleReadWrite extends Transaction {
    public NonRepeatebleReadWrite(final int number, final CyclicBarrier barrier, final Connection connection) {
        super(number, barrier, connection);
    }

    @Override
    public void perform(int id, String name) throws Exception {
        PreparedStatement prep = connection.prepareStatement("insert into names (id, person_name) values (?, ?)");
        prep.setInt(1, id + 1);
        prep.setString(2, name + "1");
        prep.executeUpdate();
        connection.commit();
        barrier.await();
    }
}
