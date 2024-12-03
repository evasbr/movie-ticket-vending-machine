package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.HomeApp;
import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Keranjang;
import com.oop.movieticketvendingmachine.models.Movie;
import com.oop.movieticketvendingmachine.models.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FilmDetailPopupController {
    @FXML
    private Label judulFilm;

    @FXML
    private ImageView posterFilm;

    @FXML
    private Label deskripsiFilm;

    @FXML
    private Button buttonDenah;

    @FXML
    private Button btnPesan;

    @FXML
    private Button closeBtn;

    @FXML
    private ComboBox<String> waktuTiket;

    @FXML
    private ComboBox<String> tempatDuduk;

    @FXML
    private Label thargaTiket;

    @FXML
    private AnchorPane root;

    private AnchorPane parentOfRoot; // Parent of popup
    List<Ticket> tickets, ticketsWaktu, ticketsPesan;
    HashMap<String, Timestamp> daftarJadwalHM = new LinkedHashMap<>();
    Movie currentMovie;
    Timestamp selectedTime;
    List<String> selectedSeats = new LinkedList<>();

    @FXML
    public void initialize(AnchorPane parent, Movie movie) {
        parentOfRoot = parent;
        currentMovie = movie;
        setJudulFilm(currentMovie.getJudul());
        setPosterFilm(currentMovie.getGambar());
        setDeskripsi(currentMovie.getDeskripsi());
        closeBtn.setOnAction(event -> removePopup());

        tempatDuduk.setDisable(true);
        btnPesan.setDisable(true);
        buttonDenah.setDisable(true);
        thargaTiket.setVisible(false);

        ticketsWaktu = new LinkedList<>();
        ticketsPesan = new LinkedList<>();
        tickets = getTicketList(); // Menyimpan semua tiket yang tersedia dari movie saat ini
        waktuDropDown();

        waktuTiket.setOnAction(event -> {
            selectedTime = daftarJadwalHM.get(waktuTiket.getValue());
            buttonDenah.setDisable(false);
            tempatDuduk.setDisable(false);
            selectedSeats.clear();
            updateTicketsWaktu();
            kursiDropDown();
            resetScrollTempatDuduk();
        });

        tempatDuduk.setOnAction(event -> {
            selectedSeats.clear();
            selectedSeats.add(tempatDuduk.getValue());

            btnPesan.setDisable(false);
            updateTharga();
            thargaTiket.setVisible(true);
            updateTicketsPesan();
        });

        buttonDenah.setOnAction(event -> {
            FXMLLoader denahLoader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/DenahPopup.fxml"));
            AnchorPane denah;
            DenahPopupController denahC;

            try {
                denah = denahLoader.load();
                denahC = denahLoader.getController();
                denahC.initialize(root);
                denahC.updateKursi(ticketsWaktu, ticketsPesan);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            root.getChildren().add(denah);
        });

        btnPesan.setOnAction(event -> {
            for (Ticket ticket : ticketsPesan) {
                Keranjang.tambahIsiKeranjang(ticket);
                HomeApp.updateTotalHarga();
            }

            removePopup();
        });
    }

    public List<Ticket> getTicketList() {
        // id_film tidak dicari karena sudah didapat dari Movie currentMovie
        String query = "SELECT id_tiket, nama_kursi, jadwal_film, status_tiket FROM TIKET WHERE status_tiket = 'tersedia' AND id_film = ?";
        Connection connection = null;
        List<Ticket> daftarTiket = new LinkedList<>();

        try {
            connection = databaseConfig.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameter untuk query
            preparedStatement.setInt(1, currentMovie.getId()); // Mengisi id_film

            // Eksekusi query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Proses hasil query
            while (resultSet.next()) {
                Timestamp jadwalTs = resultSet.getTimestamp("jadwal_film");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String jadwalStr = dateFormat.format(jadwalTs);

                Ticket ticket = new Ticket(
                        resultSet.getInt("id_tiket"),
                        currentMovie.getId(),
                        jadwalTs,
                        resultSet.getString("nama_kursi"),
                        resultSet.getString("status_tiket"),
                        currentMovie.getHarga()
                );

                daftarTiket.add(ticket);
                daftarJadwalHM.put(jadwalStr, jadwalTs);
            }

            // Menutup ResultSet dan PreparedStatement
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return daftarTiket;
    }

    public void waktuDropDown(){
        // Menambahkan seluruh key dari daftarJadwalHM yang sudah berupa String dari value-nya yang berbentuk Timestamp
        for (String str : daftarJadwalHM.keySet()) {
            waktuTiket.getItems().add(str);
        }
    }

    public void kursiDropDown(){
        tempatDuduk.getItems().clear();
        for(Ticket ticket : ticketsWaktu){
            tempatDuduk.getItems().add(ticket.getNamaKursi());
        }
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

    public void updateTharga(){
        thargaTiket.setText("Harga : Rp " + selectedSeats.size() * currentMovie.getHarga() + ",00");
    }

    public Button getButtonDenah(){
        return buttonDenah;
    }

    public void removePopup() {
        parentOfRoot.getChildren().remove(root);
    }

    // Update tiket berdasarkan waktu dipilih
    public void updateTicketsWaktu() {
        ticketsWaktu.clear();

        for (Ticket ticket : tickets) {
            if (ticket.getJadwal().equals(selectedTime)) {
                ticketsWaktu.add(ticket);
            }
        }
    }

    public void updateTicketsPesan() {
        ticketsPesan.clear();
        int ticketAdded = 0;

        // Dapatkan tiket yang sudah difilter berdasarkan waktu terlebih dahulu
        for (Ticket ticket : ticketsWaktu) {
            // Filter tiket berdasarkan nama kursi
            if (ticket.getNamaKursi().equals(selectedSeats.get(ticketAdded))) {
                ticketsPesan.add(ticket);
                ticketAdded++;

                // Keluar dari loop saat jumlah tiket dipesan sudah sesuai dengan jumlah bangku dipilih
                if (ticketAdded >= selectedSeats.size()) {
                    break;
                }
            }
        }
    }

    public void resetScrollTempatDuduk() {
        ListView<?> listView = (ListView<?>) tempatDuduk.lookup(".list-view");
        if (listView != null) {
            listView.scrollTo(0); // Reset posisi scroll ke awal
        }
    }
}
