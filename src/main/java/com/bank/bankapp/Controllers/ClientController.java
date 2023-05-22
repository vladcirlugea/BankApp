package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane client_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case "Transfer" -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransferView());
                case "Account Details" -> client_parent.setCenter(Model.getInstance().getViewFactory().getAccountDetailsView());
                case "Transaction History" -> client_parent.setCenter(Model.getInstance().getViewFactory().getTransactionsHistory());
                default -> client_parent.setCenter(Model.getInstance().getViewFactory().getHomeView());
            }
        });
    }
}
