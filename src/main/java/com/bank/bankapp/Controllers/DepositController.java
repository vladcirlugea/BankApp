package com.bank.bankapp.Controllers;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DepositController implements Initializable {
    public Button search_btn;
    public TextField eAddress_fld;
    public Label first_name;
    public Label last_name;
    public Label error_label;
    public Label account_balance;
    public Label email_label;
    public Label phone_number;
    public Label date_created;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_label.setVisible(false);
        search_btn.setOnAction(event -> onSearch());
    }
    private void onSearch() {
        String email = eAddress_fld.getText();

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        try (var mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");

            Document userDocument = collection.find(new Document("Email", email)).first();
            if (userDocument != null) {
                first_name.setText("First Name: " + userDocument.getString("FirstName"));
                last_name.setText("Last Name: " + userDocument.getString("LastName"));
                account_balance.setText("Account Balance: " + Double.toString(userDocument.getDouble("AccountBalance")));
                email_label.setText("E-mail: " + userDocument.getString("Email"));
                phone_number.setText("Phone Number: " + userDocument.getString("PhoneNumber"));
                Date date = userDocument.getDate("DateCreated");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(date);
                date_created.setText("Date Created: " + formattedDate);
                eAddress_fld.setText("");
            } else {
                error_label.setStyle("-fx-text-fill: #FE2E2E; -fx-font-family: 'Gill Sans MT'; -fx-font-weight: bold; -fx-font-size: 1.5em; -fx-alignment: center");
                error_label.setVisible(true);
                eAddress_fld.setText("");
                first_name.setText("");
                last_name.setText("");
                account_balance.setText("");
                email_label.setText("");
                phone_number.setText("");
                date_created.setText("");
            }
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
