package com.oop.movieticketvendingmachine.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class KeranjangItemController {
    @FXML
    private Label info;

    @FXML
    private Label hargaTxt;

    @FXML
    private Button delBtn;

    public void setInfo(String val) {
        info.setText(val);
    }

    public Button getDelBtn(){
        return delBtn;
    }

    public void setHargaTxt(String val) {
        hargaTxt.setText(val);
    }

}
