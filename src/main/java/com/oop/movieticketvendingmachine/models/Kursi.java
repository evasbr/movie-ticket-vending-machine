package com.oop.movieticketvendingmachine.models;

import javafx.scene.control.Button;

public class Kursi {
    private String status;
    private Button btn;
    private String id_tiket;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public String getId_tiket() {
        return id_tiket;
    }

    public void setId_tiket(String id_tiket) {
        this.id_tiket = id_tiket;
    }
}
