package com.oop.movieticketvendingmachine.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class KeranjangItemController {
    String id_tiket;
    String id_pemesanan;
    String id_kursi;

    @FXML
    private HBox kItem;

    @FXML
    private Label info;

    @FXML
    private Label hargaTxt;

    @FXML
    private Button delBtn;

    public void setData() {

    }

    public String getInfo() {
        return info.getText();
    }

    public void setInfo(String val) {
        info.setText(val);
    }

    public String getHargaTxt() {
        return hargaTxt.getText();
    }

    public void setHargaTxt(String val) {
        hargaTxt.setText(val);
    }

    public void deleteItem(MouseEvent event) {
        if (kItem.getParent() instanceof javafx.scene.layout.Pane parent) {
            parent.getChildren().remove(kItem); // Hapus KeranjangItem dari parent
        }

        // delete tiket di data
    }
}
