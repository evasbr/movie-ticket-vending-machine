package com.oop.movieticketvendingmachine.models;

import com.oop.movieticketvendingmachine.HomeApp;

import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    public static List<Ticket> isiKeranjang = new ArrayList<>();
    public static int totalBelanja = 0;

    public static int getTotalBelanja(){
        return totalBelanja;
    }

    public static void hapusIsiKeranjang(int id){
        isiKeranjang.removeIf(item -> item.getIdTiket() == id);
        totalBelanja -= 50000;
    }

    public static void tambahIsiKeranjang(Ticket tiket){
        isiKeranjang.add(tiket);
        totalBelanja += 50000;

        // Update teks total di halaman home
        HomeApp.tharga.setText("Rp " + totalBelanja + ",00");
    }

    public static void kosongkan(){
        totalBelanja=0;
        isiKeranjang.clear();
    }
}
