package ru.otus.isolation.races;

import ru.otus.isolation.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CyclicBarrier;


public class PhantomReadWrite extends Transaction {
    public PhantomReadWrite(final int number, final CyclicBarrier barrier, final Connection connection) {
        super(number, barrier, connection);
    }

    @Override
    public void perform(int id, String name) throws Exception {
        PreparedStatement prep = connection.prepareStatement("insert into names (id, person_name, person_rate) values (?, ?, ?)");
        prep.setInt(1, id);
        prep.setString(2, name);
        prep.setInt(3, 1);
        prep.executeUpdate();

        connection.commit();
        barrier.await();
    }
}
