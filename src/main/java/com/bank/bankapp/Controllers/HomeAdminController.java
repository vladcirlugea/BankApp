package com.bank.bankapp.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomeAdminController implements Initializable {
    public Text username_text;
    public Label date_label;
    public Text nrOfClients_text;
    public Text totalMoney_text;
    public Text nrOfAdmins_text;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        date_label.setText(formattedDate);
    }
}
