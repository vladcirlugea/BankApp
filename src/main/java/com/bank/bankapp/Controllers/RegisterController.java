package com.bank.bankapp.Controllers;

import com.bank.bankapp.Checkers.RegisterChecker;
import javafx.scene.control.*;
import com.bank.bankapp.DBConnection;
import com.bank.bankapp.Models.Model;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField first_name_text_field;
    public TextField last_name_text_field;
    public TextField phone_text_field;
    public TextField email_text_field;
    public DatePicker date_picker;
    public Button back_button;
    public Button register_button;
    public PasswordField password_field;
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
        if (!RegisterChecker.isFieldsCompleted(first_name_text_field, last_name_text_field, phone_text_field,
                email_text_field, password_field, date_picker)) {
            return;
        }

        String firstName = first_name_text_field.getText();
        String lastName = last_name_text_field.getText();
        String phoneNumber = phone_text_field.getText();
        String email = email_text_field.getText();
        String password = password_field.getText();
        LocalDate birthday = date_picker.getValue();

        ZonedDateTime zonedBirthday = birthday.atStartOfDay(ZoneId.systemDefault());
        Date birthdayDate = Date.from(zonedBirthday.toInstant());
        ZonedDateTime zonedDateCreated = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
        Date dateCreated = Date.from(zonedDateCreated.toInstant());

        if(!RegisterChecker.isEmailValid(email)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid E-mail format!");
            alert.showAndWait();
            return;
        }
        if(RegisterChecker.isBankEmail(email)){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unauthorized E-mail!");
            alert.showAndWait();
            return;
        }

        String hashedPassword = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashedPassword = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();

        try(var mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");

            Document doc = new Document("FirstName", firstName)
                    .append("LastName", lastName)
                    .append("PhoneNumber", phoneNumber)
                    .append("Email", email)
                    .append("Password", hashedPassword)
                    .append("Birthday", birthdayDate)
                    .append("DateCreated", dateCreated)
                    .append("AccountBalance", 0)
                    .append("AccountType", "Client");
            collection.insertOne(doc);
            mongoClient.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        Stage stage = (Stage)register_button.getScene().getWindow();
        Model.getInstance().getViewFactory().closeStage(stage);
        Model.getInstance().getViewFactory().showClientWindow();
    }
}