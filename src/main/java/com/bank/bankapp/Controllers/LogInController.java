package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    public ChoiceBox acc_selector;
    public Label email_label;
    public TextField email_field;
    public TextField password_field;
    public Button login_button;
    public Label error_label;
    public Hyperlink notAClient_link;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_button.setOnAction(event -> onLogIn());
        notAClient_link.setOnAction(event -> onRegister());
    }
    private void onLogIn(){
        Stage stage = (Stage)login_button.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }
    private void onRegister(){
        Stage stage = (Stage)notAClient_link.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showRegisterWindow();
    }
}
