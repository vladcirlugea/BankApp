package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Client;
import com.bank.bankapp.Models.Model;
import com.bank.bankapp.Models.UserSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label balance;
    public Label income_label;
    public Label expense_label;
    public ListView transaction_list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String accEmail = UserSession.getInstance().getUserEmail();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        login_date.setText(formattedDate);

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        try(var mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");

            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            Document userDocument = collection.find(new Document("Email", accEmail)).first();
            balance.setText(decimalFormat.format((userDocument.getDouble("AccountBalance"))) + "$");
            user_name.setText("Hello, " + userDocument.getString("FirstName") + "!");

            mongoClient.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
