package com.oop.movieticketvendingmachine.models;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
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

    // Mengubah integer menjadi String berformat mata uang Rupiah
    public static String IDRFormat(int value) {
        // Membuat Locale dengan factory method
        Locale indonesia = Locale.of("id", "ID");

        // Menggunakan NumberFormat
        NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
        String formattedIDR = formatter.format(value).replace("Rp", "Rp ");

        return formattedIDR;
    }

    // Mempersingkat FXMLLoad dan meng-handle error
    public static FXMLLoader customFXMLLoader(String path) {
        try {
            // Menegakses FXMLLoader
            FXMLLoader loader = new FXMLLoader(
                Utils.class.getResource("/com/oop/movieticketvendingmachine/" + path));
            loader.load(); // Memuat tampilan dari FXML
            return loader;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
