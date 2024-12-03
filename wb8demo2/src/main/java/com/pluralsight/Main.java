package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.wb2demo2 <username> <password>");
            System.exit(1);
        }
        // get the user name and password from the command line args
        String username = args[0];
        String password = args[1];

        try{
            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");


            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection;
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sakila", username, password);

            int country_id = 103;

            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM sakila.city WHERE country_id = ?;");
            pStatement.setInt(1,country_id);

            ResultSet results = pStatement.executeQuery();
            // process the results
            while (results.next()) {
                int cityId = results.getInt("city_id");
                String city = results.getString("city");

                System.out.println(cityId);
                System.out.println(city);

            }
            // 3. Close the connection
            results.close();
            pStatement.close();
            connection.close();

        }catch (ClassNotFoundException e){
            System.out.println("There was an error finding a class");
            e.printStackTrace();

        }
        catch (SQLException e){
            System.out.println("There was a SQL issue");
            e.printStackTrace();
        }



    }
}