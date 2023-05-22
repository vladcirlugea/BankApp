package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.UserSession;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.InsertOneOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.bson.Document;
import org.w3c.dom.events.DocumentEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
public class TransferMoneyController implements Initializable {
    public TextField email_text;
    public TextField amount_text;
    public Button send_money_button;
    public Label confirmation_label;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmation_label.setVisible(false);
        send_money_button.setOnAction(event -> onSend());
    }
    private String generateUniqueTransactionId(MongoCollection<Document> transactionsCollection) {
        String transactionId;
        Random random = new Random();
        long count;
        do {
            // Generate a random 6-digit ID starting with 0
            int randomNumber = random.nextInt(900000) + 100000;
            transactionId = "0" + randomNumber;
            // Check if the generated ID already exists in the transactions collection
            count = transactionsCollection.countDocuments(new Document("TransactionId", transactionId));
            // Repeat the process until a unique ID is generated
        } while (count > 0);
        return transactionId;
    }
    public void onSend(){
        String email = email_text.getText();
        double amount = Double.parseDouble(amount_text.getText());
        String senderEmail = UserSession.getInstance().getUserEmail();

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();

        try(var mongoClient = MongoClients.create(settings)){
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");
            MongoCollection<Document> transactionsCollection = database.getCollection("Transactions");

            String transactionId = generateUniqueTransactionId(transactionsCollection);
            LocalDate currentDate = LocalDate.now();

            Document filter = new Document("Email", email);
            Document senderFilter = new Document("Email", senderEmail);

            Document senderDocument = collection.find(senderFilter).first();
            Document receiverDocument = collection.find(filter).first();
            double senderBalance = senderDocument.getDouble("AccountBalance");

            long count = collection.countDocuments(filter);
            if(count == 0){
                confirmation_label.setText("Email not found!");
                confirmation_label.setStyle("-fx-text-fill: #E72525; -fx-font-family: 'Gill Sans MT'; -fx-font-weight: bold; -fx-font-size: 1.5em; -fx-alignment: center");
                confirmation_label.setVisible(true);
                return;
            }

            if(senderBalance < amount){
                confirmation_label.setText("Insufficient funds!");
                confirmation_label.setStyle("-fx-text-fill: #E72525; -fx-font-family: 'Gill Sans MT'; -fx-font-weight: bold; -fx-font-size: 1.5em; -fx-alignment: center");
                confirmation_label.setVisible(true);
                return;
            }

            Document updateSender = new Document("$inc", new Document("AccountBalance", -amount));
            collection.updateOne(senderFilter, updateSender);
            Document update = new Document("$inc", new Document("AccountBalance", amount));
            collection.updateOne(filter, update);

            Document transaction = new Document();
            transaction.append("TransactionId", transactionId);
            transaction.append("Sender", senderEmail);
            transaction.append("Receiver", email);
            transaction.append("Amount", amount);
            transaction.append("Date", currentDate);
            transactionsCollection.insertOne(transaction);
            transactionsCollection.insertOne(transaction, new InsertOneOptions().bypassDocumentValidation(true));
            transactionsCollection.createIndex(Indexes.descending("Date"));

            confirmation_label.setText("Money sent successfully!");
            confirmation_label.setStyle("-fx-text-fill: #32E320; -fx-font-family: 'Gill Sans MT'; -fx-font-weight: bold; -fx-font-size: 1.5em; -fx-alignment: center");
            confirmation_label.setVisible(true);
            email_text.setText("");
            amount_text.setText("");

            mongoClient.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
