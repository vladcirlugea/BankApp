package com.bank.bankapp.Controllers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMoneyController implements Initializable {
    public TextField email_text_field;
    public TextField amount_text_field;
    public Button send_money_button;
    public Label confirmation_label;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmation_label.setVisible(false);
        send_money_button.setOnAction(event -> onSend());
    }
    public void onSend(){
        String email = email_text_field.getText();
        double amount = Double.parseDouble(amount_text_field.getText());

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();

        try(var mongoClient = MongoClients.create(settings)){
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");

            Document filter = new Document("Email", email);
            long count = collection.countDocuments(filter);
            if(count == 0){
                confirmation_label.setText("Email not found!");
                confirmation_label.setStyle("-fx-text-fill: #E72525; -fx-font-family: 'Gill Sans MT'; -fx-font-weight: bold; -fx-font-size: 1.5em; -fx-alignment: center");
                confirmation_label.setVisible(true);
                return;
            }
            Document update = new Document("$inc", new Document("AccountBalance", amount));
            collection.updateOne(filter, update);
            confirmation_label.setText("Money sent successfully!");
            confirmation_label.setStyle("-fx-text-fill: #32E320; -fx-font-family: 'Gill Sans MT'; -fx-font-weight: bold; -fx-font-size: 1.5em; -fx-alignment: center");
            confirmation_label.setVisible(true);

            mongoClient.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
