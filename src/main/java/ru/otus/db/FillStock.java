package ru.otus.db;

import ru.otus.isolation.ConnectionFactory;
import ru.otus.isolation.ConnectionSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class FillStock {
    public static void main(String[] args) throws Exception {
        ConnectionSettings settings = new ConnectionSettings();
        Connection connection1 = ConnectionFactory.connect(settings);

        PreparedStatement ps = connection1.prepareStatement(
                "select id, initial_price from product");

        ResultSet rs = ps.executeQuery();

        Random rnd = new Random();
        Map<Long, Double> prices = new HashMap<>();
        while (rs.next()) {
            prices.put(rs.getLong(1), rs.getDouble(2) + rnd.nextFloat(10));
        }


        for (int i = 0; i < 200000; i ++)  {
            System.out.println(i);
            ps = connection1.prepareStatement(
                    "insert into stock (id, warehouse_id, product_id, price) values(?,?,?,?)");
            ps.setInt(1, i);
            ps.setInt(2, rnd.nextInt(50));
            long productId = rnd.nextLong(10000);
            ps.setLong(3, productId);
            ps.setDouble(4, prices.get(productId));
            ps.executeUpdate();
        }

        connection1.commit();
    }

    private class Product {
        int id;
        float price;
    }
}
