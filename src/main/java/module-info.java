module com.sidorovich.tatarinov.sdes {
    requires static lombok;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sidorovich.tatarinov.sdes to javafx.fxml;
    exports com.sidorovich.tatarinov.sdes;
    exports com.sidorovich.tatarinov.sdes.service;
    exports com.sidorovich.tatarinov.sdes.controller;
    opens com.sidorovich.tatarinov.sdes.controller to javafx.fxml;
}