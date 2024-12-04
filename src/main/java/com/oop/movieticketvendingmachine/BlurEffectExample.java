package com.oop.movieticketvendingmachine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BlurEffectExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Membuat AnchorPane
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: lightblue;"); // Warna latar belakang

        // Menambahkan elemen ke dalam AnchorPane
        Button button = new Button("Click Me");
        button.setLayoutX(100);
        button.setLayoutY(100);
        anchorPane.getChildren().add(button);

        // Menambahkan efek blur ke AnchorPane
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(10); // Anda bisa mengatur radius untuk mengubah tingkat blur
        anchorPane.setEffect(blur);

        // Membuat scene dan menampilkan stage
        Scene scene = new Scene(anchorPane, 300, 300);
        primaryStage.setTitle("Blur Effect Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}