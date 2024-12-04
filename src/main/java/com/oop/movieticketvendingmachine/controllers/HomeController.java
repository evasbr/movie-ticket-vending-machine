package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.HomeApp;
import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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

    @FXML
    public void initialize() {
        loadMovieCards();
        System.out.println("movie size = " + movies.size());
        bkeranjang.setOnAction(event -> {
            try {
                // Memuat FXML KeranjangPopup
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/KeranjangPopup.fxml"));
                AnchorPane keranjangPopup = loader.load();  // Memuat tampilan dari FXML
                root.getChildren().add(keranjangPopup);
                HomeApp.setWindowTitle("Detail Film");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadMovieCards() {
        movies = getMoviesList(); // Ambil daftar film dari database
        for (Movie movie : movies) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/FilmCard.fxml"));
                Pane movieCard = loader.load();

                CardController controller = loader.getController();

                controller.setImage(movie.getGambar()); // Set gambar dan judul film
                controller.setTitle(movie.getJudul());

                // Membuat event handler untuk card
                movieCard.setOnMouseClicked(event -> {
                    // Panggil metode untuk menampilkan detail film berdasarkan Movie
                    showMovieDetails(movie);
                });

                contfilm.getChildren().add(movieCard); // Tambahkan card film ke VBox
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Menampilkan Film Detail Popup untuk pemesanan
    private void showMovieDetails(Movie movie) {
        try {
            FXMLLoader filmPopupLoader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/FilmDetailPopUp.fxml"));
            AnchorPane filmPopup = filmPopupLoader.load();
            FilmDetailPopupController filmPopupC = filmPopupLoader.getController();

            // Menginisialisasi controller dengan Movie object
            filmPopupC.initialize(root, movie, filmPopupC);

            // Buat pop up
            root.getChildren().add(filmPopup);
            HomeApp.setWindowTitle("Detail Film - " + movie.getJudul());

        }catch (IOException e) {
            e.printStackTrace();
        }
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

    public Button getBcheckout() {
        return bcheckout;
    }

    public Label getThrghome(){
        return thrghome;
    }

    // Asumsikan semua id movie pada database berurutan dan mulai dari 1
    public static Movie movieFromId(int id) { return movies.get(id - 1); }
}