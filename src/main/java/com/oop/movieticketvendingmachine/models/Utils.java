package com.oop.movieticketvendingmachine.models;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    public static String timestampToStr(Timestamp timestamp) {
        String pattern = "dd-MM-yyyy HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(timestamp);
    }

    public static String IDRFormat(int value) {
        // Membuat Locale dengan factory method
        Locale indonesia = Locale.of("id", "ID");

        // Menggunakan NumberFormat
        NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
        String formattedIDR = formatter.format(value).replace("Rp", "Rp ");

        return formattedIDR;
    }
}
