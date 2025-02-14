package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.HomeApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SucceedController {
    @FXML
    private Button closeBtn;

    @FXML
    private AnchorPane root;

    // Properti non-FXML
    private AnchorPane parentOfRoot;

    @FXML
    public void initialize(AnchorPane parent) {
        parentOfRoot = parent;
        closeBtn.setOnAction(event -> removePopup());
        HomeApp.setWindowTitle("Pembayaran Berhasil");
    }

    private void removePopup() {
        parentOfRoot.getChildren().remove(root);
        HomeApp.setWindowTitle("Cinema Ticket Vending Machine");
    }
}
