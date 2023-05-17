package com.bank.bankapp.Views;

import com.bank.bankapp.Controllers.AdminController;
import com.bank.bankapp.Controllers.ClientController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    //Client fields
    private AnchorPane homeView;
    //Admin Fields
    private final StringProperty adminSelectedMenuItem;
    private AnchorPane adminHomeView;
    private AnchorPane addMoneyView;
    public ViewFactory(){
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }
    public StringProperty getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }

    //Client Methods
    public AnchorPane getHomeView(){
        if(homeView == null){
            try{
                homeView = new FXMLLoader(getClass().getResource("/com/bank/bankapp/FXML/Home.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return homeView;
    }

    //Admin Methods
    public AnchorPane getAdminHomeView(){
        if(adminHomeView == null){
            try{
                adminHomeView = new FXMLLoader(getClass().getResource("/com/bank/bankapp/FXML/HomeAdmin.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return adminHomeView;
    }
    public AnchorPane getAddMoneyView() {
        if(addMoneyView == null){
            try{
                addMoneyView = new FXMLLoader(getClass().getResource("/com/bank/bankapp/FXML/AddMoney.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return addMoneyView;
    }
    public void showLogInWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/bankapp/FXML/LogIn.fxml"));
        createStage(loader);
    }
    public void showClientWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/bankapp/FXML/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }
    public void showAdminWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/bankapp/FXML/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);
    }
    public void showRegisterWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/bankapp/FXML/Register.fxml"));
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        } catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Revolut Fake");
        stage.show();
    }
    public void closeStage(Stage stage){
        stage.close();
    }
}