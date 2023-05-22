package com.bank.bankapp.Models;

import java.time.LocalDate;

public class Transaction {
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
}
