package com.oop.movieticketvendingmachine.models;

import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    public static List<Integer> isiKeranjang = new ArrayList<>();
    public static int totalBelanja = 0;

    public static int getTotalBelanja(){
        return totalBelanja;
    }

    public static void hapusIsiKeranjang(int idTiket){
        isiKeranjang.removeIf(item -> item.equals(idTiket));
        totalBelanja -= 50000;
    }

    public static void tambahIsiKeranjang(int idTiket){
        isiKeranjang.removeIf(item -> item.equals(idTiket));
        totalBelanja += 50000;
    }
}
