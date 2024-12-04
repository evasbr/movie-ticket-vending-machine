package com.oop.movieticketvendingmachine.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardController {
    @FXML
    private ImageView filmPoster;

    @FXML
    private Label filmTitle;

    public void setImage(String url) {
        Image image = new Image(url);
        filmPoster.setImage(image);
    }

    public void setTitle(String title) {
        filmTitle.setText(title);
    }
}
