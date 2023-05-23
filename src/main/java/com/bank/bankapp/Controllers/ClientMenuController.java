package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button home_button;
    public Button transactions_button;
    public Button logout_button;
    public Button transfer_money_button;
    public Button account_details_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
        logout_button.setOnAction(event -> onLogOut());
    }
    private void addListeners(){
        home_button.setOnAction(event -> onHome());
        transfer_money_button.setOnAction(event -> onTransfer());
        account_details_button.setOnAction(event -> onAccountDetails());
        transactions_button.setOnAction(event -> onTransactionHistory());
    }
    private void onLogOut(){
        Stage stage = (Stage)logout_button.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLogInWindow();
    }
    private void onHome(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set("Home");
    }
    private void onTransfer(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set("Transfer");
    }
    private void onAccountDetails(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set("Account Details");
    }
    private void onTransactionHistory(){
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().set("Transaction History");
    }
}