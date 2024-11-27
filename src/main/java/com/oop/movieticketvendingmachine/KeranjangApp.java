package com.oop.movieticketvendingmachine;

import com.oop.movieticketvendingmachine.controllers.DummyBerandaController;
import com.oop.movieticketvendingmachine.controllers.KeranjangItemController;
import com.oop.movieticketvendingmachine.controllers.KeranjangPopupController;
import com.oop.movieticketvendingmachine.data.Film;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class KeranjangApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Inisialisasi halaman utama
        FXMLLoader mainPageLoader = new FXMLLoader(KeranjangApp.class.getResource("fxml/DummyBeranda.fxml"));
        AnchorPane mainPage = mainPageLoader.load();
        DummyBerandaController dummyBerandaC = mainPageLoader.getController();

        // Inisialisasi popup keranjang
        FXMLLoader kPopupLoader = new FXMLLoader(KeranjangApp.class.getResource("fxml/KeranjangPopup.fxml"));
        AnchorPane kPopup = kPopupLoader.load();
        KeranjangPopupController kPopupC = kPopupLoader.getController();

        // Pengaturan jendela
        Scene scene1 = new Scene(mainPage);
        Scene scene2 = new Scene(kPopup);
        stage.setTitle("Hello!");
        stage.setScene(scene1);
//        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);

        // Tambahkan ke halaman utama dan buat transparan
        mainPage.getChildren().add(kPopup);
        kPopup.setVisible(false);

        // Simpan objek interaktif berkaitan dengan keranjang
        Button kBtn = dummyBerandaC.getKeranjangBtn();
        Button kCloseBtn = kPopupC.getCloseBtn();
        VBox kItemWrapper = kPopupC.getKeranjangItemWrapper();
        // Hapus keranjangItem dummy
        kItemWrapper.getChildren().clear();

        // Atur event berkaitan dengan keranjang
        kBtn.setOnMouseClicked(event -> kPopup.setVisible(true));
        kCloseBtn.setOnMouseReleased(event -> kPopup.setVisible(false));

        // Tambahkan 4 keranjangItem dummy
        ArrayList<Film> daftarFilm = new ArrayList<>();
        daftarFilm.add(new Film("WICKED", "petualangan", "Minggu 19 Nov, 17:00", "AMBA.jpg"));
        daftarFilm.add(new Film("ZuPa", "horror", "Sabtu 12 Mei, 21:00", "ZuPa.jpg"));
        for (int i = 0; i < daftarFilm.size(); i++) {
            Film f = daftarFilm.get(i);
            for (int j = 0; j < 2; j++) {
                FXMLLoader kItemLoader = new FXMLLoader(KeranjangApp.class.getResource("fxml/KeranjangItem.fxml"));
                HBox kItem = kItemLoader.load();
                KeranjangItemController kItemC = kItemLoader.getController();

                kItemC.setInfo(f.getJudul() + ", " + f.getTayang() + ", " + "A" + (i + 1));
                kItemC.setHargaTxt("Rp " + i + "0.000,00");
                kItemWrapper.getChildren().add(kItem);
            }
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}