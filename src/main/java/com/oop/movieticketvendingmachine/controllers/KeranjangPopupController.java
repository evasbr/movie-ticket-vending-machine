package com.oop.movieticketvendingmachine.controllers;

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

import java.io.IOException;

import static com.oop.movieticketvendingmachine.controllers.HomeController.movieFromId;
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

    @FXML
    public void initialize() {
        loadKeranjangItem();
        updateTotalKeranjang();

        closeBtn.setOnAction(event -> root.setVisible(false));
    }

    public void loadKeranjangItem(){
        if(!isiKeranjang.isEmpty()){
            for (Ticket item : isiKeranjang) {
                try {
                    FXMLLoader krjItemLoader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/keranjangItem.fxml"));
                    HBox krjItem = krjItemLoader.load();  // Memuat item keranjang

                    KeranjangItemController krjItemC = krjItemLoader.getController();

                    krjItemC.setInfo(item.toString());
                    krjItemC.setHargaTxt(Utils.IDRFormat(movieFromId(item.getIdFilm()).getHarga()));;

                    keranjangItemWrapper.getChildren().add(krjItem);  // Menambahkan item ke UI

                    // Mendapatkan tombol hapus dan menambahkan event handler untuk menghapus item
                    krjItemC.getDelBtn().setOnAction(event -> {
                        Keranjang.hapusIsiKeranjang(item.getIdTiket());
                        keranjangItemWrapper.getChildren().remove(krjItem);
                        updateTotalKeranjang();

                        if (isiKeranjang.isEmpty()){
                            keranjangItemWrapper.getChildren().add(new Label("Tidak ada isi keranjang"));
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            keranjangItemWrapper.getChildren().add(new Label("Tidak ada isi keranjang"));
        }
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public void updateTotalKeranjang() {
        totalKeranjang.setText(Utils.IDRFormat(Keranjang.getTotalBelanja()));
    }
}