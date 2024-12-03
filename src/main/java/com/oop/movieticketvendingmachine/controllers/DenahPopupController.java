package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.DenahApp;
import com.oop.movieticketvendingmachine.models.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
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

    private AnchorPane parentOfRoot; // Parent of popup
    HashMap<String, Button> kursiButtonHM;
    HashMap<String, Ticket> kursiTicketsWktHM;
    private List<Ticket> tksWaktu, tksPesan;

    @FXML
    public void initialize(AnchorPane parent) {
        parentOfRoot = parent;
        closeBtn.setOnAction(event -> removePopup());
        kursiButtonHM = new HashMap<>();
        kursiTicketsWktHM = new HashMap<>();
    }

    public void updateKursi(List<Ticket> ticketsWaktu, List<Ticket> ticketsPesan) throws IOException {
        tksWaktu = ticketsWaktu;
        tksPesan = ticketsPesan;
        updateNama_ButtonList();

        for (Ticket ticket : tksWaktu) {
            Button btnRef = kursiButtonHM.get(ticket.getNamaKursi());
            kursiTicketsWktHM.put(ticket.getNamaKursi(), ticket);

            if (ticketsPesan.contains(ticket)) {
                btnRef.getStyleClass().remove("kursiBtnUnavail");
                btnRef.getStyleClass().add("kursiBtnSelect");
            } else if (ticket.getStatus_tiket().equals("tersedia")) {
                btnRef.getStyleClass().remove("kursiBtnUnavail");
                btnRef.getStyleClass().add("kursiBtnAvail");
            }
        }
    }

    public void updateNama_ButtonList () throws IOException {
        kursiButtonHM.clear();
        final String[] labels = {"A", "B", "C", "D"};

        for (int i = 0; i < labels.length; i++) {
            for (int j = 1; j <= 12; j++) {
                String namaKursi = labels[i] + Integer.toString(j);
                FXMLLoader krsBtnLoader = new FXMLLoader(DenahApp.class.getResource("fxml/KursiButton.fxml"));
                Button krsBtn = krsBtnLoader.load();
                krsBtn.setText(namaKursi);

                if (j <= 6) {
                    leftGrid.getChildren().add(krsBtn);
                } else {
                    rightGrid.getChildren().add(krsBtn);
                }

                krsBtn.setOnAction(event -> {
                    // Tidak memiliki class select ataupun avail
                    if (krsBtn.getStyleClass().contains("kursiBtnUnavail")) {
                        return;
                    }

                    // Ubah ke seleksi
                    if (krsBtn.getStyleClass().remove("kursiBtnAvail")) {
                        krsBtn.getStyleClass().add("kursiBtnSelect");
                        tksPesan.add(kursiTicketsWktHM.get(krsBtn.getText()));
                    }
                    // Batal seleksi
                    else {
                        krsBtn.getStyleClass().remove("kursiBtnSelect");
                        krsBtn.getStyleClass().add("kursiBtnAvail");
                        tksPesan.remove(kursiTicketsWktHM.get(krsBtn.getText()));
                    }
                });

                kursiButtonHM.put(namaKursi, krsBtn);
            }
        }
    }

    public FlowPane getLeftGrid() {
        return leftGrid;
    }

    public FlowPane getRightGrid() {
        return rightGrid;
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public void removePopup() {
        parentOfRoot.getChildren().remove(root);
    }
}
