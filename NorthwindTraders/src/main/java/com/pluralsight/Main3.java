package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = args[0];
        String password = args[1];

        String query1 = "SELECT * FROM Products";
        String query2 = "SELECT * FROM Customers";
        ResultSet results = null;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            boolean run = true;
            while (run){
            System.out.println("1-Display all products\n2-Display all customers\n0-Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    // Establishing connection
                    connection = DriverManager.getConnection(url, user, password);
                    statement = connection.prepareStatement(query1);

                    results = statement.executeQuery(); // Executing query

                    System.out.printf("%-10s %-35s %-12s %-15s%n", "ProductId", "ProductName", "UnitPrice", "UnitsInStock");
                    System.out.println("------------------------------------------------------------------------");

                    // Processing the result set
                    while (results.next()) {
                        int productId = results.getInt("ProductID");
                        String productName = results.getString("ProductName");
                        double unitPrice = results.getDouble("UnitPrice");
                        int unitsInStock = results.getInt("UnitsInStock");
                        System.out.printf("%-10s %-35s %-12s %-15s%n", productId, productName, unitPrice, unitsInStock);
                    }
                    break;
                case 2:
                    connection = DriverManager.getConnection(url, user, password);
                    statement = connection.prepareStatement(query2);

                    results = statement.executeQuery(); // Executing query

                    System.out.printf("%-10s %-35s %-25s %-25s %-25s", "CustomerId", "CompanyName", "ContactName", "ContactTitle", "Address\n");
                    System.out.println("------------------------------------------------------------------------");

                    // Processing the result set
                    while (results.next()) {
                        String productId = results.getString("CustomerId");
                        String companyName = results.getString("CompanyName");
                        String contractName = results.getString("ContactName");
                        String contactTitle = results.getString("ContactTitle");
                        String address = results.getString("Address");
                        System.out.printf("%-10s %-35s %-25s %-25s %-25s\n", productId, companyName, contractName, contactTitle, address);
                    }
                    break;

                case 0:
                    System.out.println("Closing...");
                    run = false;
            }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e ) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
