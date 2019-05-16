package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {
    String importClass = "com.mysql.jdbc.Driver";

    String url = "jdbc:mysql://192.168.43.175/timo";
    String username = "timo";
    String password = "12345678";

//    public static void main(String[] argv) {
//
//        System.out.println("-------- MySQL JDBC Connection Testing ------------");
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Where is your MySQL JDBC Driver?");
//            e.printStackTrace();
//            return;
//        }
//
//        System.out.println("MySQL JDBC Driver Registered!");
//        Connection connection = null;
//
//        try {
//            connection = DriverManager
//                    .getConnection("jdbc:mysql://localhost:3306/iuea", "root", "");
//
//        } catch (SQLException e) {
//            System.out.println("Connection Failed! Check output console");
//            e.printStackTrace();
//            return;
//        }
//
//        if (connection != null) {
//            System.out.println("You made it, take control of your database now!");
//        } else {
//            System.out.println("Failed to make connection!");
//        }
//    }

    @SuppressLint("NewApi")
    public Connection CONN(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn =null;
        String ConnURL = null;

        try{
            Class.forName(importClass);
            conn = DriverManager.getConnection(url,username,password);


            conn = DriverManager.getConnection(ConnURL);

        } catch (SQLException e){
            Log.e("ERROR ", e.getMessage());
        }catch (ClassNotFoundException e){
            Log.e("ERROR class not Found",e.getMessage());
        } catch (Exception e){
            Log.e("Error exception", e.getMessage());
        }
        return conn;
    }
}
