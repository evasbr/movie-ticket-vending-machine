package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.models.Movie;
import com.oop.movieticketvendingmachine.models.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static com.oop.movieticketvendingmachine.controllers.FilmDetailPopupController.tickets;
import static com.oop.movieticketvendingmachine.controllers.HomeController.movies;
import static com.oop.movieticketvendingmachine.models.Keranjang.isiKeranjang;

public class KeranjangPopupController {
    @FXML
    private Button closeBtn;

    @FXML
    private Label totalKeranjang;

    @FXML
    private VBox keranjangItemWrapper;

    @FXML
    public void initialize() {
        loadKeranjangItem();

        closeBtn.setOnAction(event -> {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();  // Menutup pop-up
        });
    }

    public void loadKeranjangItem(){
        keranjangItemWrapper.getChildren().clear();  // Membersihkan semua item sebelumnya
        if(!isiKeranjang.isEmpty()){
            for (Integer id : isiKeranjang) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/keranjangItem.fxml"));
                    HBox itemKeranjang = loader.load();  // Memuat item keranjang

                    KeranjangItemController controller = loader.getController();
                    String info = "";
                    String judul = "";

                    for (Ticket tiket : tickets) {
                        if (id.equals(tiket.getIdTiket())) {
                            for (Movie movie : movies) {
                                if (movie.getId() == tiket.getIdFilm()) {
                                    judul = movie.getJudul();
                                    break;
                                }
                            }

                            info = judul + " | " + tiket.getNamaKursi() + " | " + tiket.getJadwal();
                            break;
                        }
                    }

                    controller.setInfo(info);
                    controller.setHargaTxt("Rp " + 50000 + ", 00");

                    // Mendapatkan tombol hapus dan menambahkan event handler untuk menghapus item
                    controller.getDelBtn().setOnAction(event -> {
                        // Menghapus ID tiket dari list isiKeranjang
                        isiKeranjang.remove(id);
                        loadKeranjangItem();  // Memperbarui tampilan setelah penghapusan
                    });

                    keranjangItemWrapper.getChildren().add(itemKeranjang);  // Menambahkan item ke UI

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Button getCloseBtn() {
        return closeBtn;
    }

    public VBox getKeranjangItemWrapper() {
        return keranjangItemWrapper;
    }

}