package com.oop.movieticketvendingmachine.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SucceedController {
    @FXML
    private Button closeBtn;

    @FXML
    public void initialize() {
    }

    public Button getCloseBtn(){
        return closeBtn;
    }
}
