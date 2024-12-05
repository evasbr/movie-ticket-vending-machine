package com.oop.movieticketvendingmachine.models;

import java.sql.Timestamp;

import static com.oop.movieticketvendingmachine.controllers.HomeController.movieFromId;

public class Ticket {
    private int id_tiket;
    private int id_film;
    private Timestamp jadwal_film;
    private String nama_kursi;
    private String status_tiket;

    public Ticket(int id_tiket, int id_film, Timestamp jadwal_film, String nama_kursi, String status_tiket) {
        this.id_tiket = id_tiket;
        this.id_film = id_film;
        this.jadwal_film = jadwal_film;
        this.nama_kursi = nama_kursi;
        this.status_tiket = status_tiket;
    }

    public int getIdFilm() { return id_film; }
    public int getIdTiket() { return id_tiket; }
    public String getNamaKursi() { return nama_kursi; }
    public String getStatus_tiket() { return status_tiket; }
    public Timestamp getJadwal() { return jadwal_film; }

    // Menampilkan info tiket. Digunakan pada keranjangItem
    @Override
    public String toString() {
        String info = "";
        String judul = movieFromId(id_film).getJudul();
        String newJadwal = Utils.timestampToStr(getJadwal());
        info = judul + " | " + getNamaKursi() + " | " + newJadwal;

        return info;
    }

    // Membandingkan bahwa tiket ini sama dengan tiket lain dari segi isi,
    // bukan dari penempatan memori. Dipakai saat isiKeranjang.contains()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Periksa referensi
        if (obj == null || getClass() != obj.getClass()) return false; // Periksa tipe

        Ticket ticket = (Ticket) obj;
        // Bandingkan berdasarkan atribut yang menentukan "kesamaan"
        return id_film == ticket.id_film &&
               nama_kursi.equals(ticket.nama_kursi) &&
               status_tiket.equals(ticket.status_tiket) &&
               jadwal_film.equals(ticket.jadwal_film);
    }
}
