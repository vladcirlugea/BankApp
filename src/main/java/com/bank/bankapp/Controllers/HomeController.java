package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Transaction;
import com.bank.bankapp.Models.UserSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import com.mongodb.client.MongoClients;
import org.bson.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label balance;
    public Label income_label;
    public Label expense_label;
    private final int MAX_TRANSACTIONS = 3;
    public ListView<Transaction> transaction_list;
    private ObservableList<Transaction> transactions;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetData();
        String accEmail = UserSession.getInstance().getUserEmail();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = currentDate.format(formatter);
        login_date.setText(formattedDate);

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        try (var mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("BankAppCollection");
            MongoCollection<Document> transactionsCollection = database.getCollection("Transactions");

            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            Document userDocument = collection.find(new Document("Email", accEmail)).first();
            balance.setText(decimalFormat.format((userDocument.getDouble("AccountBalance"))) + "$");
            user_name.setText("Hello, " + userDocument.getString("FirstName") + "!");

            double totalIncome = 0.0;
            double totalExpenses = 0.0;
            income_label.setText(decimalFormat.format(totalIncome) + "$");
            expense_label.setText(decimalFormat.format(totalExpenses) + "$");

            Document queryIncome = new Document("Receiver", accEmail);
            List<Document> incomeDocuments = transactionsCollection.find(queryIncome).into(new ArrayList<>());
            for (Document document : incomeDocuments) {
                double amount = document.getDouble("Amount");
                totalIncome += amount;
            }
            Document queryExpenses = new Document("Sender", accEmail);
            List<Document> expenseDocuments = transactionsCollection.find(queryExpenses).into(new ArrayList<>());
            for (Document document : expenseDocuments) {
                double amount = document.getDouble("Amount");
                totalExpenses += amount;
            }
            income_label.setText(decimalFormat.format(totalIncome));
            expense_label.setText(decimalFormat.format(totalExpenses));

            transactions = FXCollections.observableArrayList();
            ObservableList<Transaction> transactionDetailsList = FXCollections.observableArrayList();
            transactionDetailsList.addAll(transactions);
            transaction_list.setItems(transactionDetailsList);
            List<Transaction> latestTransactions = getLatestTransactions(accEmail);
            transactions.addAll(latestTransactions);
            transaction_list.setItems(transactions);
            transaction_list.setCellFactory(new Callback<>() {
                @Override
                public ListCell<Transaction> call(ListView<Transaction> param) {
                    return new ListCell<>() {
                        @Override
                        protected void updateItem(Transaction item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
                                setGraphic(null);
                            } else {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bank/bankapp/FXML/TransactionDetails.fxml"));
                                    AnchorPane transactionDetailsNode = loader.load();
                                    TransactionDetailsController controller = loader.getController();

                                    controller.setTransactionDetails(item);

                                    setGraphic(transactionDetailsNode);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }
            });
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void resetData() {
        user_name.setText("");
        balance.setText("");
        income_label.setText("");
        expense_label.setText("");
        transaction_list.getItems().clear();
    }
    private List<Transaction> getLatestTransactions(String accEmail) {
        List<Transaction> transactions = new ArrayList<>();

        String connectionString = "mongodb+srv://test:proiectfis@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority";
        try (var mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("BankApp");
            MongoCollection<Document> collection = database.getCollection("Transactions");

            List<Document> transactionDocuments = collection.aggregate(
                    List.of(
                            new Document("$match",
                                    new Document("$or",
                                            List.of(
                                                    new Document("Sender", accEmail),
                                                    new Document("Receiver", accEmail)
                                            )
                                    )
                            ),
                            new Document("$sort", new Document("Date", 1)),
                            new Document("$limit", MAX_TRANSACTIONS)
                    )
            ).into(new ArrayList<>());

            for (Document document : transactionDocuments) {
                String sender = document.getString("Sender");
                String receiver = document.getString("Receiver");
                Double amount = document.getDouble("Amount");
                LocalDate date = document.getDate("Date").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String transactionId = document.getString("TransactionId");

                Transaction transaction = new Transaction(sender, receiver, amount, date, transactionId);
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
}