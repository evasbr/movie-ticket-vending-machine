package com.oop.movieticketvendingmachine.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class QrController {
    @FXML
    private Button closeBtn;

    @FXML
    private ImageView succeedBtn;

    @FXML
    public void initialize() {
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public ImageView getSucceedBtn(){
        return succeedBtn;
    }
}
