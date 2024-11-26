module com.oop.movieticketvendingmachine {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.oop.movieticketvendingmachine to javafx.fxml;
    exports com.oop.movieticketvendingmachine;
    exports com.oop.movieticketvendingmachine.controllers;
    opens com.oop.movieticketvendingmachine.controllers to javafx.fxml;
}