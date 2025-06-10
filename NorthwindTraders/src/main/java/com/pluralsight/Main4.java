package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.Scanner;

public class Main4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = args[0];
        String password = args[1];

        BasicDataSource bds = new BasicDataSource();
        bds.setUrl(url);
        bds.setUsername(user);
        bds.setPassword(password);

        String query1 = "SELECT * FROM Products";
        String query2 = "SELECT * FROM Customers";
        String query3 = "SELECT * FROM categories";
        String query4 = "SELECT * FROM categories WHERE CategoryID = ?";
        ResultSet results = null;
        PreparedStatement statement = null;

        try (Connection connection = bds.getConnection()){
            boolean run = true;
            while (run){
                System.out.println("\n1-Display all products\n2-Display all customers\n3-Display all categories\n4-Search by category ID\n0-Exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:

                        //connection = DriverManager.getConnection(url, user, password);


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
                        //connection = DriverManager.getConnection(url, user, password);
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

                    case 3:
                        //connection = DriverManager.getConnection(url, user, password);
                        statement = connection.prepareStatement(query3);

                        results = statement.executeQuery(); // Executing query

                        System.out.printf("%-10s    %-15s %-12s ", "CategoryId", "CategoryName", "Description\n");
                        System.out.println("------------------------------------------------------------------------");

                        // Processing the result set
                        while (results.next()) {
                            int categoryId = results.getInt("CategoryID");
                            String categoryName = results.getString("CategoryName");
                            String description = results.getString("Description");
                            System.out.printf("%-10s    %-15s %-12s\n", categoryId, categoryName,description);
                        }
                        break;

                    case 4:
                        System.out.print("Enter Category ID - ");
                        int catId = sc.nextInt(); sc.nextLine();

                       // connection = DriverManager.getConnection(url, user, password);
                        statement = connection.prepareStatement(query4);
                        statement.setInt(1,catId);

                        results = statement.executeQuery(); // Executing query

                        System.out.printf("%-10s    %-15s %-12s ", "CategoryId", "CategoryName", "Description\n");
                        System.out.println("------------------------------------------------------------------------");

                        // Processing the result set
                        while (results.next()) {
                            int categoryId = results.getInt("CategoryID");
                            String categoryName = results.getString("CategoryName");
                            String description = results.getString("Description");
                            System.out.printf("%-10s    %-15s %-12s\n", categoryId, categoryName,description);
                        }
                        break;
                    case 0:
                        System.out.println("Closing...");
                        run = false;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
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

        }
    }
}
