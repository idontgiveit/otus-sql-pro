package ru.otus.isolation.races;

import ru.otus.isolation.Transaction;

import java.sql.Connection;
import java.util.concurrent.CyclicBarrier;


public class NonRepeatebleReadRead extends Transaction {
    public NonRepeatebleReadRead(final int number, final CyclicBarrier barrier, final Connection connection) {
        super(number, barrier, connection);
    }

    @Override
    public void perform(int id, String name) throws Exception {
        printActualValues("Reader");
        barrier.await();
        printActualValues("Reader");
    }
}
