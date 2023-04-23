module com.bank.bankapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bank.bankapp to javafx.fxml;
    exports com.bank.bankapp;
}