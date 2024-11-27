package com.oop.movieticketvendingmachine;

import com.oop.movieticketvendingmachine.controllers.HomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Memuat FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApp.class.getResource("fxml/Home.fxml"));

        ScrollPane scrollPane = new ScrollPane(fxmlLoader.load());

        HomeController controller = fxmlLoader.getController();

        controller.loadMovieCards();
        // Membuat ScrollPane dan membungkus konten FXML

        // Menambahkan stylesheet CSS
        String css = this.getClass().getResource("css/style.css").toExternalForm();
        Scene scene = new Scene(scrollPane);
        scene.getStylesheets().add(css);

        // Mengatur dan menampilkan stage
        stage.setTitle("Cinema Ticket Vending Machine");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
