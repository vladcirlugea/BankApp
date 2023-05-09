package com.bank.bankapp.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label balance;
    public Label income_label;
    public Label expense_label;
    public ListView transaction_list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        login_date.setText(formattedDate);
    }
}
