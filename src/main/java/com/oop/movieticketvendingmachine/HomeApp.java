package com.oop.movieticketvendingmachine;


import com.oop.movieticketvendingmachine.controllers.*;
import com.oop.movieticketvendingmachine.models.Keranjang;
import com.oop.movieticketvendingmachine.models.Ticket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.oop.movieticketvendingmachine.models.Keranjang.isiKeranjang;

public class HomeApp extends Application {
    public static AnchorPane mainPage;
    public static Label tharga;

    @Override
    public void start(Stage stage) throws IOException {
        // Memuat scene halaman home
        FXMLLoader mainPageLoader = new FXMLLoader(HomeApp.class.getResource("fxml/Home.fxml"));
        mainPage = mainPageLoader.load();
        HomeController homeC = mainPageLoader.getController();

        // Memuat poster dan judul film yang akan bisa diklik
        homeC.loadMovieCards();

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

        Scene home = new Scene(mainPage);
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
        tharga = homeC.getThrghome();
        Button qrBtn = homeC.getBcheckout();
        Button qrClose = byrC.getCloseBtn();
        ImageView succeedQr = byrC.getSucceedBtn();
        Button succeedClose = succeedC.getCloseBtn();
        Button cancelAgree = batalByrC.getAgreeBtn();
        Button cancelNotAgree = batalByrC.getNotAgreeBtn();
        Button notSucceedClose = notSucceedC.getCloseBtn();

        // Update total harga pertama (Rp 0)
        updateTotalHarga();

        // Mengaktifkan action objek
        qrBtn.setOnMouseClicked(event -> {
            if (isiKeranjang.size() <= 0) return;
            hlmByr.setVisible(true);
        });
        qrClose.setOnAction(event -> cancelConfirm.setVisible(true));
        succeedQr.setOnMouseClicked(event -> {
            succeedScene.setVisible(true);
            for(Ticket ticket : isiKeranjang){
                byrC.updateTicketStatus(ticket.getIdTiket());
            }
            Keranjang.kosongkan();

            hlmByr.setVisible(false);
            updateTotalHarga();
        });
        succeedClose.setOnMouseClicked(event -> succeedScene.setVisible(false));
        cancelAgree.setOnAction(event -> {
            notSucceedScene.setVisible(true);
            hlmByr.setVisible(false);
        });
        cancelNotAgree.setOnMouseClicked(event -> cancelConfirm.setVisible(false));
        notSucceedClose.setOnAction(event -> {
            notSucceedScene.setVisible(false);
            cancelConfirm.setVisible(false);
        });

        // Mengatur dan menampilkan stage
        stage.setTitle("Cinema Ticket Vending Machine");
        stage.setScene(home);
//        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public static void updateTotalHarga() {
        tharga.setText("Rp " + Keranjang.getTotalBelanja() + ",00");
    }

    public static void main(String[] args) {
        launch();
    }
}
