package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;
public class AdminController implements Initializable {
    public BorderPane admin_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case "Add Money to a Client" -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAddMoneyView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactory().getAdminHomeView());
            }
        });
    }
}