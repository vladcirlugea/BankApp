package com.bank.bankapp;

import com.bank.bankapp.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Bank extends Application {
    @Override
    public void start(Stage stage){
        Model.getInstance().getViewFactory().showLogInWindow();
    }
}
