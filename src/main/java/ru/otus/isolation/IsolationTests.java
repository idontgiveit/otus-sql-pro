package ru.otus.isolation;

import ru.otus.isolation.races.*;

import java.sql.Connection;
import java.util.concurrent.CyclicBarrier;


public class IsolationTests {
    public static void main(String[] args) throws Exception {
//        dirtyReadTest();
//        nonRepeatebleTest();
//        phantomReadTest();
        serializableTest();
    }

    public static void serializableTest() throws Exception{
        CyclicBarrier barrier = new CyclicBarrier(2);

        ConnectionSettings settings = new ConnectionSettings();
        Connection connection1 = ConnectionFactory.connect(settings);
        Transaction item = new Serialization(1, barrier, connection1);
        runAsync(item, 3, "test");

        Connection connection2 = ConnectionFactory.connect(settings);
        Transaction item2 = new Serialization(2, barrier, connection2);
        runAsync(item2, 20, "positive sum");
    }

    public static void phantomReadTest() throws Exception{
        CyclicBarrier barrier = new CyclicBarrier(2);

        ConnectionSettings settings = new ConnectionSettings();
        Connection connection1 = ConnectionFactory.connect(settings);
        Transaction item = new PhantomReadRead(1, barrier, connection1);
        runAsync(item, 3, "test");

        Connection connection2 = ConnectionFactory.connect(settings);
        Transaction item2 = new PhantomReadWrite(2, barrier, connection2);
        runAsync(item2, 20, "positive sum");
    }

    public static void dirtyReadTest() throws Exception{
        CyclicBarrier barrier = new CyclicBarrier(2);

        ConnectionSettings settings = new ConnectionSettings();
        Connection connection1 = ConnectionFactory.connect(settings);

        Transaction item = new DirtyRead(1, barrier, connection1);
        runAsync(item, 3, "test");

        Connection connection2 = ConnectionFactory.connect(settings);
        Transaction item2 = new DirtyRead(2, barrier, connection1);
        runAsync(item2, 10, "another test");
    }

    public static void nonRepeatebleTest() throws Exception{
        CyclicBarrier barrier = new CyclicBarrier(2);

        ConnectionSettings settings = new ConnectionSettings();
        Connection connection1 = ConnectionFactory.connect(settings);
        Transaction item = new NonRepeatebleReadRead(1, barrier, connection1);
        runAsync(item, 3, "test");

        Connection connection2 = ConnectionFactory.connect(settings);
        Transaction item2 = new NonRepeatebleReadWrite(2, barrier, connection2);
        runAsync(item2, 10, "another test");
    }

    private static void runAsync(Transaction item, int id, String name) {
        new Thread(() -> {
            try {
                item.perform(id, name);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}