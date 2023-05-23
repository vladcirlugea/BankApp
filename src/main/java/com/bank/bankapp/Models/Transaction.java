package com.bank.bankapp.Models;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.time.LocalDate;
import java.util.Random;

public class Transaction {
    private static Transaction instance;
    private String id;
    private LocalDate date;
    private String sender;
    private String receiver;
    private double amount;

    // Constructor
    public Transaction(String sender, String receiver, double amount, LocalDate date, String id) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
        this.id = id;
    }
    public static Transaction getInstance() {
        if (instance == null) {
            instance = new Transaction("", "", 0, LocalDate.now(), "");
        }
        return instance;
    }
    public String getId(){
        return id;
    }
    public LocalDate getDate(){
        return date;
    }
    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public double getAmount() {
        return amount;
    }
    public String generateUniqueTransactionId(MongoCollection<Document> transactionsCollection) {
        String transactionId;
        Random random = new Random();
        long count;
        do {
            int randomNumber = random.nextInt(900000) + 100000;
            transactionId = "0" + randomNumber;
            count = transactionsCollection.countDocuments(new Document("TransactionId", transactionId));
        } while (count > 0);
        return transactionId;
    }
}
