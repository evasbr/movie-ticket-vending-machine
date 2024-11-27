package com.oop.movieticketvendingmachine;


import com.oop.movieticketvendingmachine.controllers.*;
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
//        homeC.loadMovieCards();


        // Membuat scene halaman bayar
        FXMLLoader bayarLoader = new FXMLLoader(HomeApp.class.getResource("fxml/qr-view.fxml"));
        AnchorPane hlmByr = bayarLoader.load();
        QrController byrC = bayarLoader.getController();

        // Membuat scene pembayaran berhasil
        FXMLLoader succeedLoader = new FXMLLoader(HomeApp.class.getResource("fxml/succeed-view.fxml"));
        AnchorPane succeedScene = succeedLoader.load();
        SucceedController succeedC = succeedLoader.getController();


        // Membuat scene pembayaran gagal
        FXMLLoader notSucceedLoader = new FXMLLoader(HomeApp.class.getResource("fxml/notsucceed-view.fxml"));
        AnchorPane notSucceedScene = notSucceedLoader.load();
        NotSucceedController notSucceedC = notSucceedLoader.getController();

        // Membuat scene konfirmasi bayar
        FXMLLoader cancelConfirmLoader = new FXMLLoader(HomeApp.class.getResource("fxml/batalbayar-view.fxml"));
        AnchorPane cancelConfirm = cancelConfirmLoader.load();
        BatalBayarController batalByrC = cancelConfirmLoader.getController();
        Scene cancel = new Scene(cancelConfirm);
        Scene notSucceed = new Scene(notSucceedScene);

        // Memunculkan scene popup dan membuatnya transparan
        mainPage.getChildren().add(hlmByr);
        mainPage.getChildren().add(succeedScene);

        mainPage.getChildren().add(cancelConfirm);
        mainPage.getChildren().add(notSucceedScene);
        hlmByr.setVisible(false);
        succeedScene.setVisible(false);
        cancelConfirm.setVisible(false);
        notSucceedScene.setVisible(false);


        // Menambahkan action objek
        Button qrBtn = homeC.getBcheckout();
        Button qrClose = byrC.getCloseBtn();
        ImageView succeedQr = byrC.getSucceedBtn();
        Button succeedClose = succeedC.getCloseBtn();
        Button cancelAgree = batalByrC.getAgreeBtn();
        Button cancelNotAgree = batalByrC.getNotAgreeBtn();
        Button notSucceedClose = notSucceedC.getCloseBtn();

        // Mengaktifkan action objek
        qrBtn.setOnMouseClicked(event -> hlmByr.setVisible(true));
        qrClose.setOnMouseReleased(event -> cancelConfirm.setVisible(true));
        succeedQr.setOnMouseClicked(event -> succeedScene.setVisible(true));
        succeedQr.setOnMouseReleased(event -> hlmByr.setVisible(false));
        succeedClose.setOnMouseClicked(event -> succeedScene.setVisible(false));
        cancelAgree.setOnMouseClicked(event -> notSucceedScene.setVisible(true));
        cancelAgree.setOnMouseReleased(event -> hlmByr.setVisible(false));
        cancelNotAgree.setOnMouseClicked(event -> cancelConfirm.setVisible(false));
        notSucceedClose.setOnMouseClicked(event -> notSucceedScene.setVisible(false));
        notSucceedClose.setOnMouseReleased(event -> cancelConfirm.setVisible(false));

        // Mengatur dan menampilkan stage
        Scene main = new Scene(mainPage);
        stage.setTitle("Cinema Ticket Vending Machine");
        stage.setScene(main);
//        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
