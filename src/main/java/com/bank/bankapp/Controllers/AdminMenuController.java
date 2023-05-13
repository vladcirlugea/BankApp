package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button home_button;
    public Button add_money_button;
    public Button search_button;
    public Button logout_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logout_button.setOnAction(event -> onLogOut());
    }
    private void onLogOut(){
        Stage stage = (Stage)logout_button.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLogInWindow();
    }
}
