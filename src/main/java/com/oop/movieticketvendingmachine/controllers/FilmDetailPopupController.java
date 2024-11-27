package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FilmDetailPopupController {
    @FXML
    private Label judulFilm;

    @FXML
    private ImageView posterFilm;

    @FXML
    private Text deskripsiFilm;

    @FXML
    private Button buttonDenah;

    @FXML
    private Button closeBtn;

    @FXML
    private ComboBox<String> waktuTiket;

    @FXML
    private ComboBox<String> tempatDuduk;

    @FXML
    private Label thrgtiket;

//    @FXML
//    private VBox keranjangItemWrapper;

    @FXML
    public void initialize(int idFilm) {
        closeBtn.setOnAction(event -> {
            // Mendapatkan stage dari tombol yang diklik
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close(); // Menutup stage
        });

        List<Ticket> tickets = getTicketList(idFilm);
        for(Ticket ticket : tickets){
            Timestamp jadwalDate = (Timestamp) ticket.getJadwal();

            // Tentukan format yang diinginkan
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Contoh format
            // Mengonversi Date menjadi String
            String jadwalString = dateFormat.format(jadwalDate);

            // Menambahkan hasil ke ChoiceBox
            waktuTiket.getItems().add(jadwalString);

            tempatDuduk.getItems().addAll(ticket.getNamaKursi());
        }
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

//    public VBox getKeranjangItemWrapper() {
//        return keranjangItemWrapper;
//    }

    public List<Ticket> getTicketList(int idFilm) {
        String query = "SELECT * FROM TIKET WHERE status_tiket = 'tersedia' AND id_film = ?";
        Connection connection = null;
        List<Ticket> tickets = new ArrayList<>();

        try {
            connection = databaseConfig.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameter untuk query
            preparedStatement.setInt(1, idFilm); // Mengisi id_film

            // Eksekusi query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Proses hasil query
            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getInt("id_tiket"),
                        resultSet.getInt("id_film"),
                        resultSet.getTimestamp("jadwal_film"),
                        resultSet.getString("nama_kursi"),
                        resultSet.getString("status_tiket")
                );
                tickets.add(ticket);
            }

            // Menutup ResultSet dan PreparedStatement
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public void setJudulFilm(String judul){
        judulFilm.setText(judul);
    }

    public void setPosterFilm(String url){
        Image image = new Image(url);
        posterFilm.setImage(image);
    }

    public void setDeskripsi(String des){
        deskripsiFilm.setText(des);
    }

    public void setThrgtiket(){
        if (waktuTiket.isFocused() & tempatDuduk.isFocused()){
            thrgtiket.setVisible(true);
        }else{
            thrgtiket.setVisible(false);
        }
    }

}
