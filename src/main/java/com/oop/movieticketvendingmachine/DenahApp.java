package com.oop.movieticketvendingmachine;

import com.oop.movieticketvendingmachine.controllers.DenahPopupController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DenahApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Inisialisasi popup film
        AnchorPane dummyPage = new AnchorPane();
        dummyPage.setPrefWidth(1280);
        dummyPage.setPrefHeight(833);

        // Inisialisasi popup denah
        FXMLLoader denahLoader = new FXMLLoader(getClass().getResource("fxml/DenahPopup.fxml"));
        AnchorPane denah = denahLoader.load();
        DenahPopupController denahC = denahLoader.getController();

        //--------------- Hapus kalo udah nyambung ke real
        Button goToDenahBtn = new Button("Buka denah dari film dan waktu saat ini");
        dummyPage.getChildren().add(goToDenahBtn);
        goToDenahBtn.setOnAction(event -> denah.setVisible(true));
        //---------------
        dummyPage.getChildren().add(denah);

        // Pengaturan jendela
        Scene scene = new Scene(dummyPage);
        stage.setTitle("Hello!");
        stage.setScene(scene);
//        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);

        // Load kursi di denah tempat duduk. Asumsikan parameter kedua adalah string
        denahC.loadKursi(1, "2024-11-26 11:00:00");

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}