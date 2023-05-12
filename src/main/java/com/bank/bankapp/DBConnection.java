package com.bank.bankapp;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBConnection {
    private MongoDatabase db;
    private MongoCollection collection;
    private MongoClient client;
    public void startDB(){
        client = MongoClients.create("mongodb+srv://<admin>:<admin>@cluster.e4scrjt.mongodb.net/?retryWrites=true&w=majority");
        this.db = client.getDatabase("BankApp");
        this.collection = db.getCollection("BankAppCollection");
    }
    public void closeDB(){
        client.close();
    }
}
