package com.bank.bankapp.Controllers;

import com.bank.bankapp.Checkers.RegisterChecker;
import com.bank.bankapp.Models.Model;
import com.bank.bankapp.Models.UserSession;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    public ChoiceBox acc_selector;
    public TextField email_field;
    public PasswordField password_field;
    public Button login_button;
    public Label error_label;
    public Hyperlink notAClient_link;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        acc_selector.getItems().add("Client");
        acc_selector.getItems().add("Admin");
        login_button.setOnAction(event -> onLogIn());
        notAClient_link.setOnAction(event -> onRegister());
        error_label.setVisible(false);
    }
    private void onLogIn(){
        String email = email_field.getText();
        String password = password_field.getText();

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        try(var mongoClient = MongoClients.create(connectionString)){
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");

            if(acc_selector.getValue() != null) {
                Document query = new Document("Email", email);
                Document userDocument = collection.find(query).first();
                if (userDocument != null) {
                    String hashedPassword = userDocument.getString("Password");
                    if (hashedPassword.equals(getHashedPassword(password))) {
                        if (acc_selector.getValue().equals("Client")) {
                            UserSession.getInstance().setUserEmail(email);
                            Stage stage = (Stage) login_button.getScene().getWindow();
                            Model.getInstance().getViewFactory().closeStage(stage);
                            Model.getInstance().getViewFactory().showClientWindow();
                        } else if (acc_selector.getValue().equals("Admin")) {
                            if (RegisterChecker.isBankEmail(email)) {
                                Stage stage = (Stage) login_button.getScene().getWindow();
                                Model.getInstance().getViewFactory().closeStage(stage);
                                Model.getInstance().getViewFactory().showAdminWindow();
                            } else {
                                RegisterChecker.Alert("Not an Admin E-Mail", "Please enter a valid Admin E-Mail");
                                return;
                            }
                        }
                    } else {
                        error_label.setVisible(true);
                        return;
                    }
                } else {
                    error_label.setVisible(true);
                    return;
                }
            }else{
                RegisterChecker.Alert("No Account Type", "Please select an account type");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private void onRegister(){
        Stage stage = (Stage)notAClient_link.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showRegisterWindow();
    }
    private String getHashedPassword(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
