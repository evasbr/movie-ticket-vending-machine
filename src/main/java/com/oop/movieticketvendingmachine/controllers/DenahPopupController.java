package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.DenahApp;
import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Kursi;
import com.oop.movieticketvendingmachine.models.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class DenahPopupController {
    @FXML
    private FlowPane leftGrid;

    @FXML
    private FlowPane rightGrid;

    @FXML
    private Button closeBtn;

    @FXML
    public void initialize() {
//         Tutup P O P U P
        closeBtn.setOnAction(event ->
            closeBtn.getParent().setVisible(false)
        );

//        closeBtn.getParent().setVisible(false);
    }

    // Ingat, parameter keduanya STRING
    public void loadKursi(int id_film, Date jadwal_film_raw) throws IOException {
        HashMap<String, Button> nama_ButtonList = getNama_ButtonList();
//
//        // Format string ke LocalDateTime
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);

        String query = "SELECT * FROM TIKET WHERE id_film = ? AND jadwal_film = ?";
        Connection connection = null;

        try {
            connection = databaseConfig.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Konversi LocalDateTime ke Timestamp
            Timestamp jadwal_film = new Timestamp(jadwal_film_raw.getTime());

            // Set parameter untuk query
            preparedStatement.setInt(1, id_film);
            preparedStatement.setTimestamp(2, jadwal_film);

            // Eksekusi query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Proses hasil query
            while (resultSet.next()) {
                String namaKursi = resultSet.getString("nama_kursi");
                Button btnRef = nama_ButtonList.get(namaKursi);
                switch (resultSet.getString("status_tiket")) {
                    case "tersedia":
                        btnRef.getStyleClass().remove("kursiBtnUnavail");
                        btnRef.getStyleClass().add("kursiBtnAvail");
                        break;
                }
            }

            // Menutup ResultSet dan PreparedStatement
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Button> getNama_ButtonList() throws IOException {
        final String[] labels = {"A", "B", "C", "D"};
        HashMap<String, Button> nama_ButtonList = new HashMap<>();

        for (int i = 0; i < labels.length; i++) {
            for (int j = 1; j <= 12; j++) {
                String namaKursi = labels[i] + Integer.toString(j);
                FXMLLoader krsBtnLoader = new FXMLLoader(DenahApp.class.getResource("fxml/KursiButton.fxml"));
                Button krsBtn = krsBtnLoader.load();
                krsBtn.setText(namaKursi);

                if (j <= 6) {
                    leftGrid.getChildren().add(krsBtn);
                } else {
                    rightGrid.getChildren().add(krsBtn);
                }

                krsBtn.setOnAction(event -> {
                    if (krsBtn.getStyleClass().contains("kursiBtnUnavail"))
                        return;

                    if (krsBtn.getStyleClass().remove("kursiBtnAvail")) {
                        krsBtn.getStyleClass().add("kursiBtnSelect");
                    } else {
                        krsBtn.getStyleClass().remove("kursiBtnSelect");
                        krsBtn.getStyleClass().add("kursiBtnAvail");
                    }
                });

                nama_ButtonList.put(namaKursi, krsBtn);
            }
        }

        return nama_ButtonList;
    }

    public FlowPane getLeftGrid() {
        return leftGrid;
    }

    public FlowPane getRightGrid() {
        return rightGrid;
    }

    public Button getCloseBtn() {
        return closeBtn;
    }
}
