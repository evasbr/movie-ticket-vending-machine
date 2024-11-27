package com.oop.movieticketvendingmachine;

import com.oop.movieticketvendingmachine.controllers.HomeController;
import com.oop.movieticketvendingmachine.controllers.QrController;
import com.oop.movieticketvendingmachine.controllers.SucceedController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Memuat scene halaman home
        FXMLLoader mainPageLoader = new FXMLLoader(HomeApp.class.getResource("fxml/Home.fxml"));
        AnchorPane mainPage = mainPageLoader.load();
        HomeController homeC = mainPageLoader.getController();

        // Membuat scene halaman bayar
        FXMLLoader bayarLoader = new FXMLLoader(HomeApp.class.getResource("fxml/qr-view.fxml"));
        AnchorPane hlmByr = bayarLoader.load();
        QrController byrC = bayarLoader.getController();

        // Membuat scene pembayaran berhasil
        FXMLLoader succeedLoader = new FXMLLoader(HomeApp.class.getResource("fxml/succeed-view.fxml"));
        AnchorPane succeedScene = succeedLoader.load();
        SucceedController succeedC = succeedLoader.getController();

        homeC.loadMovieCards();
        // Membuat ScrollPane dan membungkus konten FXML

        // Menambahkan stylesheet CSS
        Scene home = new Scene(mainPage);
        Scene bayar = new Scene(hlmByr);
        Scene succeed = new Scene(succeedScene);

        // Memunculkan scene popup dan membuatnya transparan
        mainPage.getChildren().add(hlmByr);
        mainPage.getChildren().add(succeedScene);
        hlmByr.setVisible(false);
        succeedScene.setVisible(false);

        // Menambahkan action objek
        Button qrBtn = homeC.getBcheckout();
        Button qrClose = byrC.getCloseBtn();
        ImageView succeedQr = byrC.getSucceedBtn();
        Button succeedClose = succeedC.getCloseBtn();

        // Mengaktifkan action objek
        qrBtn.setOnMouseClicked(event -> hlmByr.setVisible(true));
        qrClose.setOnMouseReleased(event -> hlmByr.setVisible(false));
        succeedQr.setOnMouseClicked(event -> succeedScene.setVisible(true));
        succeedQr.setOnMouseReleased(event -> hlmByr.setVisible(false));
        succeedClose.setOnMouseClicked(event -> succeedScene.setVisible(false));


        // Mengatur dan menampilkan stage
        stage.setTitle("Cinema Ticket Vending Machine");
        stage.setScene(home);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
