package com.oop.movieticketvendingmachine.models;

public class Movie {
    private int id;
    private String judul;
    private String gambar;
    private String deskripsi;
    private int harga;

    public Movie(int id, String judul, String gambar, String deskripsi, int harga) {
        this.id = id;
        this.judul = judul;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }

    public int getId() { return id; }
    public String getJudul() { return judul; }
    public String getGambar() { return gambar; }
    public String getDeskripsi() { return deskripsi; }
    public int getHarga(){ return harga; }
}