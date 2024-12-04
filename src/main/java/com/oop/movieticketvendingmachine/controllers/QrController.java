package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.database.databaseConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class QrController {
    @FXML
    private Button closeBtn;

    @FXML
    private ImageView succeedBtn;

    public Button getCloseBtn() {
        return closeBtn;
    }

    public ImageView getSucceedBtn(){
        return succeedBtn;
    }

    public boolean updateTicketStatus(int idTiket) {
        String query = "UPDATE TIKET SET status_tiket = 'dipesan' WHERE id_tiket = ?";
        Connection connection = null;
        boolean isUpdated = false;

        try {
            connection = databaseConfig.connectDB();  // Membuka koneksi ke database
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Menetapkan parameter untuk query
            preparedStatement.setInt(1, idTiket);       // Set ID tiket

            // Eksekusi update query
            int rowsAffected = preparedStatement.executeUpdate(); // Mengembalikan jumlah baris yang terpengaruh

            // Jika ada baris yang terpengaruh, maka status berhasil diperbarui
            if (rowsAffected > 0) {
                isUpdated = true;
            }

            preparedStatement.close();  // Menutup PreparedStatement
        } catch (Exception e) {
            e.printStackTrace();  // Menampilkan stack trace jika ada kesalahan
        }

        return isUpdated;  // Mengembalikan apakah update berhasil
    }
}
