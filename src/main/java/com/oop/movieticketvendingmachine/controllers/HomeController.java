package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.oop.movieticketvendingmachine.models.Keranjang.isiKeranjang;

public class HomeController {
    public static List<Movie> movies;
    @FXML
    private FlowPane contfilm;

    @FXML
    private Button bkeranjang;

    @FXML
    private Button bcheckout;

    @FXML
    public void initialize() {
        loadMovieCards();
        isiKeranjang.add(1101);
        isiKeranjang.add(1102);
        isiKeranjang.add(1103);
        bkeranjang.setOnAction(event -> {
            try {
                // Memuat FXML KeranjangPopup
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/KeranjangPopup.fxml"));
                AnchorPane root = loader.load();  // Memuat tampilan dari FXML

                Stage popupStage = new Stage();
                popupStage.setTitle("Keranjang Anda");  // Memberikan judul pada pop-up

                // Menetapkan Scene ke Stage
                Scene popupScene = new Scene(root);
                popupStage.setScene(popupScene);

                // Menampilkan pop-up
                popupStage.show();  // Menampilkan pop-up

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        bkeranjang.setOnAction(event -> {
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/KeranjangPopup.fxml"));
//                Pane keranjang = loader.load();
//
//                Stage stage = new Stage();
//                Scene scene = new Scene(keranjang);
//
//                stage.setScene(scene);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
    }

    public void loadMovieCards() {
        movies = getMoviesList(); // Ambil daftar film dari database
        contfilm.setHgap(20);
        contfilm.setVgap(20);
        for (Movie movie : movies) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/FilmCard.fxml"));
                Pane movieCard = loader.load();

                CardController controller = loader.getController();

                controller.setImage(movie.getGambar()); // Set judul film
                controller.setTitle(movie.getJudul());

                movieCard.setUserData(movie.getId()); // Asumsi movie.getId() mengembalikan ID film

                // Membuat event handler untuk card
                movieCard.setOnMouseClicked(event -> {
                    Integer movieId = (Integer) movieCard.getUserData(); // Ambil ID film dari card
                    showMovieDetails(movieId, movie.getJudul(), movie.getGambar(), movie.getDeskripsi()); // Panggil metode untuk menampilkan detail film berdasarkan ID
                });
                contfilm.getChildren().add(movieCard);

//                contfilm.getChildren().add(movieCard); // Tambahkan card film ke VBox
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showMovieDetails(Integer movieId, String judul, String url, String deskripsi) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/FilmDetailPopUp.fxml"));
            AnchorPane movieCard = loader.load();
            FilmDetailPopupController controller = loader.getController();
            // Menginisialisasi controller dengan movieId
            controller.initialize(movieId);
            controller.setJudulFilm(judul);
            controller.setPosterFilm(url);
            controller.setDeskripsi(deskripsi);

            // Buat Stage baru untuk pop-up

            Stage stage = new Stage();
            stage.setScene(new Scene(movieCard));
            stage.setTitle("Detail Film");
//            stage.initModality(Modality.APPLICATION_MODAL); // Membuat pop-up modal
            stage.show(); // Menampilkan stage
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

    public FlowPane getMovieCard(){
        return contfilm;
    }
}