package com.oop.movieticketvendingmachine.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BatalBayarController {
    @FXML
    private Button agreeBtn;

    @FXML
    private Button notAgreeBtn;

    @FXML
    public void initialize() {
    }

    public Button getAgreeBtn(){
        return agreeBtn;
    }

    public Button getNotAgreeBtn(){
        return notAgreeBtn;
    }
}
