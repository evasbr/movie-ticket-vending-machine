package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Keranjang;
import com.oop.movieticketvendingmachine.models.Movie;
import com.oop.movieticketvendingmachine.models.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.oop.movieticketvendingmachine.models.Keranjang.isiKeranjang;


public class HomeController {
    public static List<Movie> movies = new ArrayList<>();
    @FXML
    private FlowPane contfilm;

    @FXML
    private Button bkeranjang;

    @FXML
    private Button bcheckout;

    @FXML
    private Label thrghome;

    @FXML
    private AnchorPane root;

    // Properti non-FXML
    static Label thrg;

    @FXML
    public void initialize() {
        thrg = thrghome;
        loadMovieCards();
        HomeController.updateTotalHarga();
        System.out.println("movie size = " + movies.size());

        // Memuat popup halaman keranjang
        bkeranjang.setOnAction(event -> {
            FXMLLoader loader = Utils.customFXMLLoader("fxml/KeranjangPopup.fxml");
            AnchorPane keranjangPopup = loader.getRoot();
            KeranjangPopupController keranjangPopupC = loader.getController();
            keranjangPopupC.initialize(root, keranjangPopupC);

            root.getChildren().add(keranjangPopup);
        });

        // Membuat popup halaman bayar
        bcheckout.setOnAction(event -> {
            if (isiKeranjang.isEmpty()) return;

            FXMLLoader bayarLoader = Utils.customFXMLLoader("fxml/qr-view.fxml");
            AnchorPane hlmByr = bayarLoader.getRoot();
            QrController byrC = bayarLoader.getController();
            byrC.initialize(root);

            root.getChildren().add(hlmByr);
        });
    }

    // Menampilkan daftar movie pada halaman home
    public void loadMovieCards() {
        movies = getMoviesList(); // Ambil daftar film dari database
        for (Movie movie : movies) {
            FXMLLoader loader = Utils.customFXMLLoader("fxml/FilmCard.fxml");
            Pane movieCard = loader.getRoot();

            CardController controller = loader.getController();

            controller.setImage(movie.getGambar()); // Set gambar dan judul film
            controller.setTitle(movie.getJudul());

            // Membuat event handler untuk card
            movieCard.setOnMouseClicked(event -> {
                // Panggil metode untuk menampilkan detail film berdasarkan Movie
                showMovieDetails(movie);
            });

            contfilm.getChildren().add(movieCard); // Tambahkan card film ke VBox
        }
    }

    // Menampilkan Film Detail Popup untuk pemesanan
    private void showMovieDetails(Movie movie) {
        FXMLLoader filmPopupLoader = Utils.customFXMLLoader("fxml/FilmDetailPopUp.fxml");
        AnchorPane filmPopup = filmPopupLoader.getRoot();
        FilmDetailPopupController filmPopupC = filmPopupLoader.getController();

        // Menginisialisasi controller dengan Movie object
        filmPopupC.initialize(root, movie, filmPopupC);

        // Buat pop up
        root.getChildren().add(filmPopup);
    }

    public List<Movie> getMoviesList() {
        String query = "SELECT * FROM FILM"; // Contoh query
        Connection connection = null;
        List<Movie> movies = new ArrayList<>();

        try {
            connection = databaseConfig.connectDB();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Movie movie = new Movie(
                        resultSet.getInt("id_film"),
                        resultSet.getString("judul"),
                        resultSet.getString("poster"),
                        resultSet.getString("deskripsi"),
                        resultSet.getInt("harga")
                );
                movies.add(movie);
            }
//            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static void updateTotalHarga() {
        thrg.setText(Utils.IDRFormat(Keranjang.getTotalBelanja()));
    }

    // Asumsikan semua id movie pada database berurutan dan mulai dari 1
    public static Movie movieFromId(int id) { return movies.get(id - 1); }
}