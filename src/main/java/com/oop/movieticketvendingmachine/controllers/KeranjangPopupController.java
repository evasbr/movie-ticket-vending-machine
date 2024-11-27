package com.oop.movieticketvendingmachine.controllers;

import com.oop.movieticketvendingmachine.models.Keranjang;
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
        totalKeranjang.setText("Rp " + String.valueOf(Keranjang.getTotalBelanja()) + ",00");

        closeBtn.setOnAction(event -> {
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();  // Menutup pop-up
        });
    }

    public void setTotalKeranjang(Label totalKeranjang) {
        this.totalKeranjang = totalKeranjang;
    }

    public void loadKeranjangItem(){
        keranjangItemWrapper.getChildren().clear();  // Membersihkan semua item sebelumnya
        if(!isiKeranjang.isEmpty()){
            for (Ticket tiketKeranjang : isiKeranjang) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/oop/movieticketvendingmachine/fxml/keranjangItem.fxml"));
                    HBox itemKeranjang = loader.load();  // Memuat item keranjang

                    KeranjangItemController controller = loader.getController();
                    String info = "";
                    String judul = "";


                    for (Movie movie : movies) {
                        if (movie.getId() == tiketKeranjang.getIdFilm()) {
                            judul = movie.getJudul();
                            break;
                        }
                    }
                    info = judul + " | " + tiketKeranjang.getNamaKursi() + " | " + tiketKeranjang.getJadwal();

                    controller.setInfo(info);
                    controller.setHargaTxt("Rp50.000,00");

                    // Mendapatkan tombol hapus dan menambahkan event handler untuk menghapus item
                    controller.getDelBtn().setOnAction(event -> {
                        Keranjang.hapusIsiKeranjang(tiketKeranjang.getIdTiket());
                        loadKeranjangItem();  // Memperbarui tampilan setelah penghapusan
                        totalKeranjang.setText("Rp " + String.valueOf(Keranjang.getTotalBelanja()) + ",00");
                    });

                    keranjangItemWrapper.getChildren().add(itemKeranjang);  // Menambahkan item ke UI


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

    public VBox getKeranjangItemWrapper() {
        return keranjangItemWrapper;
    }

}