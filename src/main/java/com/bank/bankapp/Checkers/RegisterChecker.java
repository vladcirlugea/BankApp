package com.bank.bankapp.Checkers;

import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterChecker {
    public static boolean isFieldsCompleted(TextField first_name_text_field, TextField last_name_text_field,
                                            TextField phone_text_field, TextField email_text_field,
                                            PasswordField password_field, DatePicker date_picker) {
        if (first_name_text_field.getText().isEmpty() ||
                last_name_text_field.getText().isEmpty() ||
                phone_text_field.getText().isEmpty() ||
                email_text_field.getText().isEmpty() ||
                password_field.getText().isEmpty() ||
                date_picker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incomplete fields");
            alert.setHeaderText("Please complete all fields.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    public static boolean isEmailValid(String email){
        return email.matches("^[\\w-_.+]*[\\w-_.]@[\\w]+([\\w-]+\\.)+[\\w-]{2,}$");
    }
    public static boolean isBankEmail(String email){
        return email.endsWith("@bank.com");
    }
}
