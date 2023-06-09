package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button home_button;
    public Button add_money;
    public Button search_button;
    public Button deposit_button;
    public Button logout_button;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
        logout_button.setOnAction(event -> onLogOut());
    }
    private void addListeners(){
        home_button.setOnAction(event -> onHome());
        add_money.setOnAction(event -> onAddMoney());
        deposit_button.setOnAction(actionEvent -> onDeposit());
    }
    private void onHome(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Home");
    }
    private void onAddMoney(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Add Money to a Client");
    }
    private void onDeposit(){
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Search Client");
    }
    private void onLogOut(){
        Stage stage = (Stage)logout_button.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLogInWindow();
    }


}