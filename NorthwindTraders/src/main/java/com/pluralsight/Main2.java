package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = args[0];        // username
        String password = args[1]; // password

        ResultSet results = null;
        Connection connection = null;
        PreparedStatement stmt = null;

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter product ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            // 1. Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Make connection
            connection = DriverManager.getConnection(url, username, password);

            // 3. Create statement и request
            //Statement statement = connection.createStatement();
            String query = "SELECT ProductID, ProductName, ROUND(UnitPrice, 2) AS UnitPrice, UnitsInStock FROM products WHERE ProductId = ?";

            //For secure requests
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);

            // 4. Make request
            results = stmt.executeQuery();

            // 5. Process results
            while (results.next()) {
                int productID = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");
                System.out.printf("ID | ProductName | UnitPrice | UnitsInStock\n%d | %s | %f | %d", productID, productName, unitPrice, stock);
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
        finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
