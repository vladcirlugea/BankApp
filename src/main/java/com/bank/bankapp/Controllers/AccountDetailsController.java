package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.UserSession;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import org.bson.Document;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class AccountDetailsController implements Initializable {
    public Text first_name;
    public Text last_name;
    public Text email_label;
    public Text phone_number;
    public Text date_created;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String email = UserSession.getInstance().getUserEmail();

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();

        try(var mongoClient = MongoClients.create(settings)) {
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");

            Document filter = new Document("Email", email);
            Document userDocument = collection.find(filter).first();

            if (userDocument != null) {
                String firstName = userDocument.getString("FirstName");
                String lastName = userDocument.getString("LastName");
                String phoneNumber = userDocument.getString("PhoneNumber");
                Date dateCreated = userDocument.getDate("DateCreated");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = dateFormat.format(dateCreated);

                first_name.setText("First Name: " + firstName);
                last_name.setText("Last Name: " + lastName);
                email_label.setText("E-Mail: " + email);
                phone_number.setText("Phone Number: " + phoneNumber);
                date_created.setText("Date Created: " + formattedDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
