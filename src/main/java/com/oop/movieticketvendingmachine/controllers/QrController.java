package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.HomeApp;
import com.oop.movieticketvendingmachine.database.databaseConfig;
import com.oop.movieticketvendingmachine.models.Keranjang;
import com.oop.movieticketvendingmachine.models.Ticket;
import com.oop.movieticketvendingmachine.models.Utils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;

import static com.oop.movieticketvendingmachine.models.Keranjang.isiKeranjang;

public class QrController {
    @FXML
    private Button closeBtn;

    @FXML
    private ImageView succeedBtn;

    @FXML
    private Label timerLabel;

    @FXML
    private AnchorPane root;

    // Properti non-FXML
    private AnchorPane parentOfRoot;
    private Timeline timeline;
    public static boolean stopTimeline;

    @FXML
    public void initialize(AnchorPane parent) {
        this.parentOfRoot = parent;
        stopTimeline = false;
        HomeApp.setWindowTitle("Menunggu Pembayaran");
        startCountdown(timerLabel, 60);

        closeBtn.setOnAction(event -> {
            bukaKonfirmasiBatalBayar(false);
        });

        succeedBtn.setOnMouseClicked(event -> {
            // Hentikan timeline
            stopTimeline = true;

            // Buka popup pembayaran sukses
            FXMLLoader succeedLoader = Utils.customFXMLLoader("fxml/succeed-view.fxml");
            AnchorPane succeedPopup = succeedLoader.getRoot();
            SucceedController succeedC = succeedLoader.getController();

            // Proses barang pada keranjang
            for (Ticket tiket : isiKeranjang) {
                updateTicketStatus(tiket.getIdTiket());
            }
            Keranjang.kosongkan();
            HomeController.updateTotalHarga();

            // Hapus popup QR dan munculkan popup berhasil
            removePopup();
            succeedC.initialize(parentOfRoot);
            parentOfRoot.getChildren().add(succeedPopup);
        });
    }

    public void removePopup() {
        parentOfRoot.getChildren().remove(root);
        HomeApp.setWindowTitle("Cinema Ticket Vending Machine");
    }

    public boolean updateTicketStatus(int idTiket) {
        String query = "UPDATE TIKET SET status_tiket = 'dipesan' WHERE id_tiket = ?";
        Connection connection = null;
        boolean isUpdated = false;

        try {
            connection = databaseConfig.connectDB();  // Membuka koneksi ke database
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Menetapkan parameter untuk query
            preparedStatement.setInt(1, idTiket);       // Set ID tiket

            // Eksekusi update query
            // Mengembalikan jumlah baris yang terpengaruh
            int rowsAffected = preparedStatement.executeUpdate();

            // Jika ada baris yang terpengaruh, maka status berhasil diperbarui
            if (rowsAffected > 0) {
                isUpdated = true;
            }

            preparedStatement.close();  // Menutup PreparedStatement
        } catch (Exception e) {
            e.printStackTrace();  // Menampilkan stack trace jika ada kesalahan
        }

        return isUpdated;  // Mengembalikan apakah update berhasil
    }

    public void bukaKonfirmasiBatalBayar(boolean paksaBatal) {
        // Membuat popup konfirmasi bayar
        FXMLLoader cancelConfirmLoader = Utils.customFXMLLoader("fxml/batalbayar-view.fxml");
        AnchorPane cancelConfirm = cancelConfirmLoader.getRoot();
        BatalBayarController batalByrC = cancelConfirmLoader.getController();
        batalByrC.initialize(root);

        if (!paksaBatal) {
            root.getChildren().add(cancelConfirm);
        } else {
            batalByrC.getAgreeBtn().fire();
        }
    }

    private void startCountdown(Label label, int seconds) {
        final int[] time = {seconds}; // Gunakan array untuk mengubah nilai dalam lambda

        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                time[0]--; // Kurangi waktu
                label.setText(formatTime(time[0])); // Perbarui label

                // Jika waktu habis, hentikan timeline
                if (time[0] <= 0) {
                    label.setText("Time's up!"); // Pesan setelah countdown selesai
                    timeline.stop();
                    bukaKonfirmasiBatalBayar(true);
                }

                // Hentikan jika telah dibayar
                if (stopTimeline)
                    timeline.stop();
            })
        );

        timeline.setCycleCount(seconds); // Ulangi sesuai jumlah detik
        timeline.play(); // Mulai timer
        label.setText(formatTime(seconds)); // Set awal waktu
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds); // Format MM:SS
    }
}
