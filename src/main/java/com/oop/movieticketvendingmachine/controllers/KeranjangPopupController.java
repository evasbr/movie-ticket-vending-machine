package com.oop.movieticketvendingmachine.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class KeranjangPopupController {
    @FXML
    private Button closeBtn;

    @FXML
    private VBox keranjangItemWrapper;

    @FXML
    public void initialize() {

    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public VBox getKeranjangItemWrapper() {
        return keranjangItemWrapper;
    }
}
