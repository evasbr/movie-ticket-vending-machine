package com.oop.movieticketvendingmachine;

import com.oop.movieticketvendingmachine.models.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeApp extends Application {
    public static AnchorPane mainPage;;
    static Stage stage;

    @Override
    public void start(Stage stage) {
        HomeApp.stage = stage;

        // Memuat scene halaman home
        FXMLLoader mainPageLoader = Utils.customFXMLLoader("fxml/Home.fxml");
        mainPage = mainPageLoader.getRoot();
        Scene home = new Scene(mainPage);

        // Mengatur dan menampilkan stage
        setWindowTitle("Cinema Ticket Vending Machine");
        stage.setScene(home);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();
        stage.setAlwaysOnTop(false);
    }

    public static void setWindowTitle(String title) { stage.setTitle(title); }

    public static void main(String[] args) {
        launch();
    }
}
