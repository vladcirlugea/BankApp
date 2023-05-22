package com.bank.bankapp.Controllers;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HomeAdminController implements Initializable {
    public Text username_text;
    public Label date_label;
    public Text nrOfClients_text;
    public Text totalMoney_text;
    public Text nrOfAdmins_text;
    public Label nrOfClients_label;
    public Label nrOfAdmins_label;
    public Label totalMoney_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        date_label.setText(formattedDate);

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        try(var mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");
            double totalMoney = 0;

            long clients = collection.countDocuments(new Document("AccountType", "Client"));
            nrOfClients_label.setText(Long.toString(clients));

            long admins = collection.countDocuments(new Document("AccountType", "Admin"));
            nrOfAdmins_label.setText(Long.toString(admins));

            totalMoney = collection.aggregate(Arrays.asList(Aggregates.group(null, Accumulators.sum("total", "$AccountBalance")))).first().getDouble("total");
            totalMoney_label.setText(Double.toString(totalMoney) + "$");

            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            String formattedTotalMoney = decimalFormat.format(totalMoney);
            totalMoney_label.setText(formattedTotalMoney + "$");

            mongoClient.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
