package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.HomeApp;
import com.oop.movieticketvendingmachine.models.Ticket;
import com.oop.movieticketvendingmachine.models.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.util.*;

public class DenahPopupController {
    @FXML
    private FlowPane leftGrid;

    @FXML
    private FlowPane rightGrid;

    @FXML
    private Button closeBtn;

    @FXML
    private AnchorPane root;

    // Properti non FXML
    private AnchorPane parentOfRoot; // Parent of popup
    private FilmDetailPopupController filmPopupC;
    private HashMap<String, Button> kursiButtonHM;
    private HashMap<String, Ticket> kursiTicketsWktHM;

    @FXML
    public void initialize(AnchorPane parent, FilmDetailPopupController filmPopupCon) {
        parentOfRoot = parent;
        filmPopupC = filmPopupCon;
        kursiButtonHM = new HashMap<>();
        kursiTicketsWktHM = new HashMap<>();
        closeBtn.setOnAction(event -> removePopup());
        HomeApp.setWindowTitle("Denah Bioskop");
    }

    public void loadKursi() {
        // Hapus kursi dummy dari SceneBuilder
        kursiButtonHM.clear();

        // Bioskop terdapat 4 baris A B C D dengan masing-masing 12 kolom bangku
        final String[] rows = {"A", "B", "C", "D"};

        for (String row : rows) {
            for (int i = 1; i <= 12; i++) {
                String namaKursi = row + i;
                FXMLLoader krsBtnLoader = Utils.customFXMLLoader("fxml/KursiButton.fxml");
                Button krsBtn = krsBtnLoader.getRoot();
                krsBtn.setText(namaKursi);

                // 1-6 ada di grid kiri, 7-12 ada di grid kanan
                if (i <= 6) {
                    leftGrid.getChildren().add(krsBtn);
                } else {
                    rightGrid.getChildren().add(krsBtn);
                }

                krsBtn.setOnAction(event -> {
                    // Kursi nonaktif tidak dapat diapa-apakan
                    if (krsBtn.getStyleClass().contains("kursiBtnUnavail"))
                        return;

                    // Ubah ke seleksi jika kursi tersedia
                    if (krsBtn.getStyleClass().remove("kursiBtnAvail")) {
                        krsBtn.getStyleClass().add("kursiBtnSelect");
                    }
                    // Ubah ke tersedia jika kursi sudah diseleksi
                    else {
                        krsBtn.getStyleClass().remove("kursiBtnSelect");
                        krsBtn.getStyleClass().add("kursiBtnAvail");
                    }

                    // Update kursi dipilih pada Film Detail Popup
                    filmPopupC.updateTicketsPesan(kursiTicketsWktHM.get(namaKursi));
                });

                kursiButtonHM.put(namaKursi, krsBtn);
            }
        }
    }

    // Update kursi berdasarkan aktivitas tiket pada Film Detail Popup
    public void updateKursi(List<Ticket> ticketsWaktu, List<Ticket> ticketsPesan) {
        for (Ticket tiket : ticketsWaktu) {
            Button btnRef = kursiButtonHM.get(tiket.getNamaKursi());
            kursiTicketsWktHM.put(tiket.getNamaKursi(), tiket);

            // Jika kursi sudah dipesan
            if (ticketsPesan.contains(tiket)) {
                btnRef.getStyleClass().remove("kursiBtnUnavail");
                btnRef.getStyleClass().add("kursiBtnSelect");
            }
            // Jika kursi belum dipesan
            else if (tiket.getStatus_tiket().equals("tersedia")) {
                btnRef.getStyleClass().remove("kursiBtnUnavail");
                btnRef.getStyleClass().add("kursiBtnAvail");
            }
        }
    }

    public void removePopup() {
        parentOfRoot.getChildren().remove(root);
        HomeApp.setWindowTitle("Detail Film - " + filmPopupC.getJudulFilm());
    }
}
