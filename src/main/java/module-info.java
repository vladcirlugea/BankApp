module com.bank.bankapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.mongodb.driver.sync.client;

    opens com.bank.bankapp to javafx.fxml;
    exports com.bank.bankapp;
    exports com.bank.bankapp.Controllers;
    exports com.bank.bankapp.Models;
    exports com.bank.bankapp.Views;
}