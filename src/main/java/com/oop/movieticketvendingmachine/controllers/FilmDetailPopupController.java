package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.HomeApp;
import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Keranjang;
import com.oop.movieticketvendingmachine.models.Movie;
import com.oop.movieticketvendingmachine.models.Ticket;
import com.oop.movieticketvendingmachine.models.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.oop.movieticketvendingmachine.models.Keranjang.isiKeranjang;

public class FilmDetailPopupController {
    @FXML
    private Button closeBtn;

    @FXML
    private Label judulFilm;

    @FXML
    private ImageView posterFilm;

    @FXML
    private Label deskripsiFilm;

    @FXML
    private ComboBox<Timestamp> waktuTiket;

    @FXML
    private ComboBox<Ticket> tempatDuduk;

    @FXML
    private Label lKursiDipilih;

    @FXML
    private Button buttonDenah;

    @FXML
    private Label thargaTiket;

    @FXML
    private Button btnPesan;

    @FXML
    private AnchorPane root;

    // Properti non FXML
    private AnchorPane parentOfRoot; // Parent of popup
    private FilmDetailPopupController filmPopupC;
    private Movie currentMovie;
    public static List<Ticket> tickets;
    private List<Ticket> ticketsWaktu, ticketsPesan;

    @FXML
    public void initialize(AnchorPane parent, Movie movie, FilmDetailPopupController filmPopupCon) {
        parentOfRoot = parent;
        currentMovie = movie;
        filmPopupC = filmPopupCon;
        closeBtn.setOnAction(event -> removePopup());

        // Setel tampilan berdasarkan film saat ini
        setJudulFilm(currentMovie.getJudul());
        setPosterFilm(currentMovie.getGambar());
        setDeskripsi(currentMovie.getDeskripsi());

        tempatDuduk.setDisable(true);
        lKursiDipilih.setDisable(true);
        btnPesan.setDisable(true);
        thargaTiket.setVisible(false);
        buttonDenah.setDisable(true);

        ticketsWaktu = new ArrayList<>();
        ticketsPesan = new ArrayList<>();
        tickets = getTicketList(); // Menyimpan semua tiket yang tersedia dari movie saat ini
        waktuDropDown();

        // Mengatur tampilan pilihan Timestamp pada dropdown
        waktuTiket.setConverter(new StringConverter<Timestamp>() {
            @Override
            public String toString(Timestamp timestamp) {
                return timestamp != null ? Utils.timestampToStr(timestamp) : "";
            }

            @Override
            public Timestamp fromString(String string) { return null; }
        });
        waktuTiket.setOnAction(event -> {
            buttonDenah.setDisable(false);
            tempatDuduk.setDisable(false);
            tempatDuduk.setPromptText("Pilih kursi");
            lKursiDipilih.setDisable(false);
            updateTicketsWaktu();
            kursiDropDown();
        });
// ------------------------------ Kemungkinan akan dihapus ------------------------------ //
        // Mengatur tampilan pilihan Ticket pada dropdown
        tempatDuduk.setConverter(new StringConverter<Ticket>() {
            @Override
            public String toString(Ticket ticket) {
                return ticket != null ? ticket.getNamaKursi() : "";
            }

            @Override
            public Ticket fromString(String string) { return null; }
        });
        tempatDuduk.setOnAction(event -> {
            updateTicketsPesan(tempatDuduk.getValue());
        });
//--------------------------------------------------------------------------------------- //
        // Tampilkan popup denah saat buttonDenah ditekan
        buttonDenah.setOnAction(event -> {
            try {
                FXMLLoader denahLoader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/DenahPopup.fxml"));
                AnchorPane denah = denahLoader.load();;
                DenahPopupController denahC = denahLoader.getController();

                denahC.initialize(root, filmPopupC);
                denahC.loadKursi();
                denahC.updateKursi(ticketsWaktu, ticketsPesan);

                root.getChildren().add(denah);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Memproses pesanan dan kembali ke Home
        btnPesan.setOnAction(event -> {
            for (Ticket ticket : ticketsPesan) {
                Keranjang.tambahIsiKeranjang(ticket);
            }

            HomeApp.updateTotalHarga();
            removePopup();
        });

        root.getChildren().remove(tempatDuduk);
    }

    public List<Ticket> getTicketList() {
        // id_film tidak dicari karena sudah didapat dari Movie currentMovie
        String query = "SELECT id_tiket, nama_kursi, jadwal_film, status_tiket FROM TIKET WHERE status_tiket = 'tersedia' AND id_film = ?";
        Connection connection = null;
        List<Ticket> daftarTiket = new ArrayList<>();

        try {
            connection = databaseConfig.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Set parameter untuk query
            preparedStatement.setInt(1, currentMovie.getId()); // Mengisi id_film
            // Eksekusi query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Proses hasil query
            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getInt("id_tiket"),
                        currentMovie.getId(),
                        resultSet.getTimestamp("jadwal_film"),
                        resultSet.getString("nama_kursi"),
                        resultSet.getString("status_tiket")
                );

                // Jangan masukkan tiket yang ada di keranjang
                if (!isiKeranjang.contains(ticket))
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

    // Tambahkan seluruh tiket pada waktu dipilih ke ComboBox waktuTiket
    public void waktuDropDown() {
        for (Ticket tiket : tickets) {
            if (!waktuTiket.getItems().contains(tiket.getJadwal())) {
                waktuTiket.getItems().add(tiket.getJadwal());
            }
        }
    }

    public void kursiDropDown() {
        tempatDuduk.getItems().clear();
        for (Ticket ticket : ticketsWaktu) {
            tempatDuduk.getItems().add(ticket);
        }
    }

    public void setJudulFilm(String judul) {
        judulFilm.setText(judul);
    }

    public void setPosterFilm(String url) {
        Image image = new Image(url);
        posterFilm.setImage(image);
    }

    public void setDeskripsi(String des) {
        deskripsiFilm.setText(des);
    }

    public void updateTharga() {
        thargaTiket.setText("Harga : " +
            Utils.IDRFormat(ticketsPesan.size() * currentMovie.getHarga()));
    }

    public void removePopup() {
        parentOfRoot.getChildren().remove(root);
    }

    // Update tiket berdasarkan waktu dipilih
    public void updateTicketsWaktu() {
        ticketsWaktu.clear();

        for (Ticket ticket : tickets) {
            if (ticket.getJadwal().equals(waktuTiket.getValue())) {
                ticketsWaktu.add(ticket);
            }
        }

        ticketsPesan.clear();
        updateTicketsPesan(null);
    }

    public void updateTicketsPesan(Ticket selectedTicket) {
        if (selectedTicket != null) {
            if (ticketsPesan.contains(selectedTicket)) {
                ticketsPesan.remove(selectedTicket);
            } else {
                ticketsPesan.add(selectedTicket);
            }
        }

        // Meng-update tampilan
        updateTharga();
        updateLKursiDipilih();
        if (!ticketsPesan.isEmpty()) {
            btnPesan.setDisable(false);
            thargaTiket.setVisible(true);
        } else {
            btnPesan.setDisable(true);
            thargaTiket.setVisible(false);
        }
    }

    public void updateLKursiDipilih() {
        if (ticketsPesan.isEmpty()) {
            lKursiDipilih.setText("Tempat duduk belum dipilih");
        } else {
            lKursiDipilih.setText(ticketsPesan.stream()
                    .filter(Objects::nonNull) // Hilangkan elemen null
                    .map(Ticket::getNamaKursi)
                    .collect(Collectors.joining(", ")));
        }
    }
}
