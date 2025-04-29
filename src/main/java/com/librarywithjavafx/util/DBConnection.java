package com.librarywithjavafx.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/librarywithjavafxdb";

    private static final String USER = "root";
    private static final String PASSWORD = "Ey.103430"; //the sql root password

    public static Connection getConnection(){

        try{
            return DriverManager.getConnection(URL,USER,PASSWORD);

        } catch (SQLException e){
            System.out.println("could not connect to database");
            e.printStackTrace();
            return null;
        }
    }

}
