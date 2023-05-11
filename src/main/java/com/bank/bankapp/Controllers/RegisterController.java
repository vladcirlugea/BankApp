package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public Label first_name_label;
    public TextField first_name_text_field;
    public Label last_name;
    public TextField last_name_text_field;
    public Label phone_label;
    public TextField phone_text_field;
    public Label email_label;
    public TextField email_text_field;
    public DatePicker datePicker;
    public Button back_button;
    public Button register_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back_button.setOnAction(event -> onBack());
        register_button.setOnAction(event -> onRegister());
    }
    private void onBack(){
        Stage stage = (Stage)back_button.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showLogInWindow();
    }
    private void onRegister(){
        Stage stage = (Stage)register_button.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }
}
