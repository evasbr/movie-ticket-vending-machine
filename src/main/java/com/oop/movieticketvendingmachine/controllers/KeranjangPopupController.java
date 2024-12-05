package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.HomeApp;
import com.oop.movieticketvendingmachine.models.Keranjang;
import com.oop.movieticketvendingmachine.models.Ticket;
import com.oop.movieticketvendingmachine.models.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.oop.movieticketvendingmachine.models.Keranjang.isiKeranjang;

public class KeranjangPopupController {
    @FXML
    private Button closeBtn;

    @FXML
    private Label totalKeranjang;

    @FXML
    private VBox keranjangItemWrapper;

    @FXML
    private AnchorPane root;

    // Properti non-FXML
    private AnchorPane parentOfRoot;
    private KeranjangPopupController kPopupC;

    @FXML
    public void initialize(AnchorPane parent, KeranjangPopupController kPopupCon)  {
        parentOfRoot = parent;
        kPopupC = kPopupCon;
        loadKeranjangItem();
        updateTotalKeranjang();

        closeBtn.setOnAction(event -> removePopup());
    }

    public void removePopup() {
        parentOfRoot.getChildren().remove(root);
        HomeApp.setWindowTitle("Cinema Ticket Vending Machine");
    }

    public void loadKeranjangItem() {
        if(!isiKeranjang.isEmpty()){
            for (Ticket item : isiKeranjang) {
                FXMLLoader krjItemLoader = Utils.customFXMLLoader("fxml/KeranjangItem.fxml");
                HBox krjItem = krjItemLoader.getRoot();  // Memuat item keranjang
                KeranjangItemController krjItemC = krjItemLoader.getController();
                krjItemC.initialize(kPopupC, item);

                keranjangItemWrapper.getChildren().add(krjItem);  // Menambahkan item ke UI
            }
        } else {
            keranjangItemWrapper.getChildren().add(new Label("Tidak ada isi keranjang"));
        }
    }

    public void updateTotalKeranjang() {
        HomeApp.setWindowTitle("Keranjang - " + isiKeranjang.size() + " tiket");
        totalKeranjang.setText(Utils.IDRFormat(Keranjang.getTotalBelanja()));
    }

    public VBox getKeranjangItemWrapper() {
        return keranjangItemWrapper;
    }
}