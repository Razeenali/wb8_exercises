package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.wb2demo2 <username> <password>");
            System.exit(1);
        }
        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind", username, password);
             Scanner scanner = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");

            int choice;
            do {
                // Home screen menu
                System.out.println("What do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        // Display all products
                        try (PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM northwind.products;");
                             ResultSet results = pStatement.executeQuery()) {
                            while (results.next()) {
                                int productID = results.getInt("ProductID");
                                String product = results.getString("ProductName");
                                double price = results.getDouble("UnitPrice");
                                int stock = results.getInt("UnitsInStock");

                                System.out.printf("Product ID: %d \nName: %s\nPrice: %.2f\nStock: %d\n", productID, product, price, stock);
                                System.out.println("-------------------------------------------");
                            }
                        } catch (SQLException e) {
                            System.out.println("There was a SQL issue displaying products");
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        // Display all customers
                        try (PreparedStatement pStatement = connection.prepareStatement("SELECT ContactName, CompanyName, City, Country, Phone FROM northwind.customers ORDER BY Country;");
                             ResultSet results = pStatement.executeQuery()) {
                            while (results.next()) {
                                String contactName = results.getString("ContactName");
                                String companyName = results.getString("CompanyName");
                                String city = results.getString("City");
                                String country = results.getString("Country");
                                String phone = results.getString("Phone");

                                System.out.printf("Contact Name: %s\nCompany Name: %s\nCity: %s\nCountry: %s\nPhone: %s\n", contactName, companyName, city, country, phone);
                                System.out.println("-------------------------------------------");
                            }
                        } catch (SQLException e) {
                            System.out.println("There was a SQL issue displaying customers");
                            e.printStackTrace();
                        }
                        break;

                    case 3:
                        // Display all categories
                        try (PreparedStatement pStatement = connection.prepareStatement("SELECT CategoryID, CategoryName FROM northwind.categories ORDER BY CategoryID;");
                             ResultSet results = pStatement.executeQuery()) {
                            while (results.next()) {
                                int categoryId = results.getInt("CategoryID");
                                String categoryName = results.getString("CategoryName");

                                System.out.printf("Category ID: %d \nCategory Name: %s\n", categoryId, categoryName);
                                System.out.println("-------------------------------------------");
                            }

                            System.out.print("Enter a Category ID to display products in that category: ");
                            int selectedCategoryId = scanner.nextInt();
                            scanner.nextLine();

                            try (PreparedStatement productStatement = connection.prepareStatement("SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM northwind.products WHERE CategoryID = ?;");) {
                                productStatement.setInt(1, selectedCategoryId);
                                try (ResultSet productResults = productStatement.executeQuery()) {
                                    while (productResults.next()) {
                                        int productID = productResults.getInt("ProductID");
                                        String productName = productResults.getString("ProductName");
                                        double unitPrice = productResults.getDouble("UnitPrice");
                                        int unitsInStock = productResults.getInt("UnitsInStock");

                                        System.out.printf("Product ID: %d \nProduct Name: %s\nUnit Price: %.2f\nUnits in Stock: %d\n", productID, productName, unitPrice, unitsInStock);
                                        System.out.println("-------------------------------------------");
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            System.out.println("There was a SQL issue displaying categories or products in category");
                            e.printStackTrace();
                        }
                        break;

                    case 0:
                        System.out.println("Exiting application...");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }

            } while (choice != 0);

        } catch (ClassNotFoundException e) {
            System.out.println("There was an error finding a class");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("There was a SQL issue");
            e.printStackTrace();
        }
    }
}
