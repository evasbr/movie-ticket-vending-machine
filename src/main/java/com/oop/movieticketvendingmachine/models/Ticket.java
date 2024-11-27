package com.oop.movieticketvendingmachine.models;

import java.util.Date;

public class Ticket {
    private int id_tiket;
    private int id_film;
    private Date jadwal_film;
    private String nama_kursi;
    private String status_tiket;

    public Ticket(int id_tiket, int id_film, Date jadwal_film, String nama_kursi, String status_tiket){
        this.id_tiket = id_tiket;
        this.id_film = id_film;
        this.jadwal_film = jadwal_film;
        this.nama_kursi = nama_kursi;
        this.status_tiket = status_tiket;
    }

    public String getNamaKursi(){
        return nama_kursi;
    }

    public String getStatus_tiket(){
        return status_tiket;
    }

    public Date getJadwal(){
        return jadwal_film;
    }

    public int id_film(){
        return id_film;
    }

}
