package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";        // username
        String password = "yearup"; // password

        try {
            // 1. Load driver (можно пропустить, но на всякий случай)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Make connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // 3. Create statement и request
            Statement statement = connection.createStatement();
            String query = "SELECT ProductName FROM products";

            // 4. Make request
            ResultSet results = statement.executeQuery(query);

            // 5. Process results
            while (results.next()) {
                String productName = results.getString("ProductName");
                System.out.println(productName);
            }

            // 6. Close connection
            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database error");
            e.printStackTrace();
        }
    }
}

