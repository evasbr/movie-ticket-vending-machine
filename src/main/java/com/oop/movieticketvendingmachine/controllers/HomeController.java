package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

public class HomeController {
    @FXML
    private FlowPane contfilm;


    public void loadMovieCards() {
        List<Movie> movies = getMoviesList(); // Ambil daftar film dari database
        contfilm.setHgap(40);
        contfilm.setVgap(40);
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
}
