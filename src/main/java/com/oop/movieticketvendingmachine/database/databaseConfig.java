package com.oop.movieticketvendingmachine.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConfig {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    public static Connection connectDB() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi berhasil!");
            } catch (SQLException e) {
                System.err.println("Koneksi gagal: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Koneksi ditutup.");
            } catch (SQLException e) {
                System.err.println("Gagal menutup koneksi: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing koneksi...");
        Connection conn = connectDB(); // Panggil method connect() untuk tes
        if (conn != null) {
            System.out.println("Koneksi berhasil.");
        } else {
            System.out.println("Koneksi gagal.");
        }
        closeConnection(); // Menutup koneksi setelah testing
    }
}
