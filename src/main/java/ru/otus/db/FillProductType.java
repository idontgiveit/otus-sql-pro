package ru.otus.db;

import ru.otus.isolation.ConnectionFactory;
import ru.otus.isolation.ConnectionSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class FillProductType {
    public static void main(String[] args) throws Exception {
        ConnectionSettings settings = new ConnectionSettings();
        Connection connection1 = ConnectionFactory.connect(settings);

        for (int i = 0; i < 10; i ++)  {
            PreparedStatement ps = connection1.prepareStatement("insert into product_type (id, name) values(?,?)");
            ps.setInt(1, i);
            ps.setString(2, "product type " + i);
            ps.executeUpdate();
        }

        connection1.commit();
    }
}
