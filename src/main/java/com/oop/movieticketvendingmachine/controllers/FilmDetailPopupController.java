package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class FilmDetailPopupController {
    public static List<Ticket> tickets;

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
    private ChoiceBox<String> waktuTiket;

    @FXML
    private ChoiceBox<String> tempatDuduk;


    String filterWaktu ="";
    String filterKursi ="";

    @FXML
    public void initialize(int idFilm) {
        closeBtn.setOnAction(event -> {
            // Mendapatkan stage dari tombol yang diklik
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close(); // Menutup stage
        });

        waktuTiket.setOnAction(event -> {
            System.out.println("filterWaktu: " + filterWaktu);
            System.out.println("filterKursi: " + filterKursi);
            System.out.println("Item yang dipilih: " + waktuTiket.getValue());

            filterWaktu = waktuTiket.getValue();
            waktuDropDown();
        });

        tempatDuduk.setOnAction(event -> {
            System.out.println("filterWaktu: " + filterWaktu);
            System.out.println("filterKursi: " + filterKursi);
            System.out.println("Item yang dipilih: " + tempatDuduk.getValue());
            filterKursi= tempatDuduk.getValue();
            kursiDropDown();
        });

        tickets = getTicketList(idFilm);
        kursiDropDown();
        waktuDropDown();
    }

    public void kursiDropDown(){
        Set<String> itemKursi = new LinkedHashSet<>();
        tempatDuduk.getItems().clear();

        for(Ticket ticket : tickets){
            if(filterWaktu == null || filterWaktu.isEmpty()){
                itemKursi.add(ticket.getNamaKursi());
            } else {
                if(ticket.getNamaKursi().equals(filterWaktu)){
                    itemKursi.add(ticket.getNamaKursi());
                }
            }
        }

        tempatDuduk.getItems().addAll(itemKursi);
    }

    public void waktuDropDown(){
        Set<String> itemWaktu = new LinkedHashSet<>();
        waktuTiket.getItems().clear();

        for(Ticket ticket : tickets){
            if(filterKursi == null || filterKursi.isEmpty()){
                // Jika tidak ada filter waktu, tambahkan semua waktu tiket
                Timestamp jadwalDate = (Timestamp) ticket.getJadwal(); // Ambil jadwal tiket
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String jadwalString = dateFormat.format(jadwalDate);
                itemWaktu.add(jadwalString);
            } else {
                if(ticket.getNamaKursi().equals(filterWaktu)){
                    // Jika tidak ada filter waktu, tambahkan semua waktu tiket
                    Timestamp jadwalDate = (Timestamp) ticket.getJadwal(); // Ambil jadwal tiket
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String jadwalString = dateFormat.format(jadwalDate);
                    itemWaktu.add(jadwalString);
                }
            }
        }

        waktuTiket.getItems().addAll(itemWaktu);
    }

    public List<Ticket> getTicketList(int idFilm) {
        String query = "SELECT * FROM TIKET WHERE status_tiket = 'tersedia' AND id_film = ?";
        Connection connection = null;
        List<Ticket> daftarTiket = new ArrayList<>();

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
                daftarTiket.add(ticket);
            }

            // Menutup ResultSet dan PreparedStatement
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return daftarTiket;
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
}
