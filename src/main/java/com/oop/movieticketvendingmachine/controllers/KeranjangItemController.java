package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.models.Keranjang;
import com.oop.movieticketvendingmachine.models.Ticket;
import com.oop.movieticketvendingmachine.models.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import static com.oop.movieticketvendingmachine.controllers.HomeController.movieFromId;
import static com.oop.movieticketvendingmachine.models.Keranjang.isiKeranjang;

public class KeranjangItemController {
    @FXML
    private Label info;

    @FXML
    private Label hargaTxt;

    @FXML
    private Button delBtn;

    @FXML
    private HBox root;

    @FXML
    public void initialize(KeranjangPopupController kPopupC, Ticket item) {
        // Setel teks informasi dan harga item
        info.setText(item.toString());
        hargaTxt.setText(Utils.IDRFormat(movieFromId(item.getIdFilm()).getHarga()));

        // Mendapatkan tombol hapus dan menambahkan event handler untuk menghapus item
        delBtn.setOnAction(event -> {
            Keranjang.hapusIsiKeranjang(item.getIdTiket());
            kPopupC.getKeranjangItemWrapper().getChildren().remove(root);
            kPopupC.updateTotalKeranjang();

            if (isiKeranjang.isEmpty()){
                kPopupC.getKeranjangItemWrapper().
                        getChildren().add(new Label("Tidak ada isi keranjang"));
            }
        });
    }
}
