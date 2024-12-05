package com.oop.movieticketvendingmachine.models;

import com.oop.movieticketvendingmachine.controllers.HomeController;

import java.util.ArrayList;
import java.util.List;

import static com.oop.movieticketvendingmachine.controllers.HomeController.movieFromId;

public class Keranjang {
    public static List<Ticket> isiKeranjang = new ArrayList<>();
    public static int totalBelanja = 0;

    public static int getTotalBelanja(){
        return totalBelanja;
    }

    public static void hapusIsiKeranjang(int id){
        for (Ticket item : isiKeranjang){
            if (item.getIdTiket() == id){
                totalBelanja -= movieFromId(item.getIdFilm()).getHarga();
                isiKeranjang.remove(item);

                // Update teks total di halaman home
                HomeController.updateTotalHarga();
                break;
            }
        }
    }

    public static void tambahIsiKeranjang(Ticket tiket){
        isiKeranjang.add(tiket);
        totalBelanja += movieFromId(tiket.getIdFilm()).getHarga();

        // Update teks total di halaman home
        HomeController.updateTotalHarga();
    }

    public static void kosongkan(){
        totalBelanja = 0;
        isiKeranjang.clear();
    }
}
