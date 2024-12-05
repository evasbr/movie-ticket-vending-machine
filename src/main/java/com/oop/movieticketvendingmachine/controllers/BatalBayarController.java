package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.HomeApp;
import com.oop.movieticketvendingmachine.models.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class BatalBayarController {
    @FXML
    private Button agreeBtn;

    @FXML
    private Button notAgreeBtn;

    @FXML
    private AnchorPane root;

    // Properti non FXML
    private AnchorPane parentOfRoot;

    @FXML
    public void initialize(AnchorPane parent) {
        parentOfRoot = parent;
        HomeApp.setWindowTitle("Konfirmasi Batal Bayar");

        notAgreeBtn.setOnAction(event -> {
            parentOfRoot.getChildren().remove(root);
            HomeApp.setWindowTitle("Menunggu Pembayaran");
        });

        agreeBtn.setOnAction(event -> {
            // Membuat popup pembayaran gagal
            FXMLLoader notSucceedLoader = Utils.customFXMLLoader("fxml/notsucceed-view.fxml");
            AnchorPane notSucceedPopup = notSucceedLoader.getRoot();
            NotSucceedController notSucceedC = notSucceedLoader.getController();

            // Hapus popup konfirmasi dan popup QR
            parentOfRoot.getChildren().remove(root);
            HomeApp.mainPage.getChildren().remove(parentOfRoot);

            // Tampilkan popup gagal bayar menjadi anak dari mainpage
            QrController.stopTimeline = true;
            notSucceedC.initialize(HomeApp.mainPage);
            HomeApp.mainPage.getChildren().add(notSucceedPopup);
        });
    }

    public Button getAgreeBtn() {
        return agreeBtn;
    }
}
