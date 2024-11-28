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

        homeC.loadMovieCards();

        // Inisialisasi popup film
        FXMLLoader filmLoader = new FXMLLoader(getClass().getResource("fxml/FilmDetailPopUp.fxml"));
        AnchorPane film = filmLoader.load();
        FilmDetailPopupController filmC = filmLoader.getController();

        // Inisialisasi popup denah
        FXMLLoader denahLoader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/DenahPopup.fxml"));
        AnchorPane denah = denahLoader.load();
        DenahPopupController denahC = denahLoader.getController();
//        goToDenahBtn.setOnAction(event -> denah.setVisible(true));

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
        mainPage.getChildren().add(film);
        mainPage.getChildren().add(denah);

        hlmByr.setVisible(false);
        succeedScene.setVisible(false);
        cancelConfirm.setVisible(false);
        notSucceedScene.setVisible(false);
        film.setVisible(false);
        denah.setVisible(false);

        // Menambahkan action objek
        tharga = homeC.getThrghome();
        Button qrBtn = homeC.getBcheckout();
        Button qrClose = byrC.getCloseBtn();
        ImageView succeedQr = byrC.getSucceedBtn();
        Button succeedClose = succeedC.getCloseBtn();
        Button cancelAgree = batalByrC.getAgreeBtn();
        Button cancelNotAgree = batalByrC.getNotAgreeBtn();
        Button notSucceedClose = notSucceedC.getCloseBtn();
        Button openDenah = filmC.getButtonDenah();
        Button closeDenah = denahC.getCloseBtn();

        // Mengaktifkan action objek
        qrBtn.setOnMouseClicked(event -> hlmByr.setVisible(true));
        qrClose.setOnMouseReleased(event -> cancelConfirm.setVisible(true));
        succeedQr.setOnMouseClicked(event -> {
            succeedScene.setVisible(true);
            for(Ticket ticket : isiKeranjang){
                byrC.updateTicketStatus(ticket.getIdTiket());
            }
            Keranjang.kosongkan();
        });
        succeedQr.setOnMouseReleased(event -> hlmByr.setVisible(false));
        succeedClose.setOnMouseClicked(event -> succeedScene.setVisible(false));
        cancelAgree.setOnMouseClicked(event -> notSucceedScene.setVisible(true));
        cancelAgree.setOnMouseReleased(event -> hlmByr.setVisible(false));
        cancelNotAgree.setOnMouseClicked(event -> cancelConfirm.setVisible(false));
        notSucceedClose.setOnMouseClicked(event -> notSucceedScene.setVisible(false));
        notSucceedClose.setOnMouseReleased(event -> cancelConfirm.setVisible(false));
        openDenah.setOnMouseClicked(event -> denah.setVisible(true));
        closeDenah.setOnMouseClicked(event -> denah.setVisible(false));

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
