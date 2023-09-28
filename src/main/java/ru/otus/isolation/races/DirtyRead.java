package ru.otus.isolation.races;

import ru.otus.isolation.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.CyclicBarrier;


public class DirtyRead extends Transaction {
    public DirtyRead(final int number, final CyclicBarrier barrier, final Connection connection) {
        super(number, barrier, connection);
    }

    public void perform(int id, String name) throws Exception {
        log("started --------------------------------------");
        printActualValues("values before insert");

        PreparedStatement prep = connection.prepareStatement("insert into names (id, person_name) values (?, ?)");
        prep.setInt(1, id);
        prep.setString(2, name);
        prep.executeUpdate();

        prep = connection.prepareStatement("insert into names (id, person_name) values (?, ?)");
        prep.setInt(1, id + 1);
        prep.setString(2, name + "1");
        prep.executeUpdate();

        log("before commit --------------------------------------");
        barrier.await();
        printActualValues("values after insert");

        connection.commit();
        log("finished --------------------------------------");
    }
}
