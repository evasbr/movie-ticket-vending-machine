package com.oop.movieticketvendingmachine.data;

public class Film {
    private String judul;
    private String deskripsi;
    private String tayang;
    private String imgSrc;

    public Film(String judul, String deskripsi, String tayang, String imgSrc) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tayang = tayang;
        this.imgSrc = imgSrc;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTayang() {
        return tayang;
    }

    public void setTayang(String tayang) {
        this.tayang = tayang;
    }
}