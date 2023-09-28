package ru.otus.db;

import ru.otus.isolation.ConnectionFactory;
import ru.otus.isolation.ConnectionSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;


public class FillProduct {
    public static void main(String[] args) throws Exception {
        ConnectionSettings settings = new ConnectionSettings();
        Connection connection1 = ConnectionFactory.connect(settings);

        Random rnd = new Random();
        for (int i = 0; i < 10000; i ++)  {
            System.out.println(i);
            PreparedStatement ps = connection1.prepareStatement(
                    "insert into product (id, name, product_type_id, initial_price) values(?,?,?,?)");
            ps.setInt(1, i);
            ps.setString(2, "product " + i);
            ps.setInt(3,i % 10);
            ps.setDouble(4, rnd.nextFloat(1000));
            ps.executeUpdate();
        }

        connection1.commit();
    }
}
