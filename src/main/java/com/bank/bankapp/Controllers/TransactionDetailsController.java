package com.bank.bankapp.Controllers;

import com.bank.bankapp.Models.Transaction;
import javafx.scene.control.Label;

import java.time.format.DateTimeFormatter;

public class TransactionDetailsController {
    public Label transaction_id;
    public Label date_label;
    public Label sender_label;
    public Label receiver_label;
    public Label sum_label;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public void setTransactionDetails(Transaction transaction) {
        transaction_id.setText(transaction.getId());
        String formattedDate = transaction.getDate().format(dateFormatter);
        date_label.setText(formattedDate);
        sender_label.setText(transaction.getSender());
        receiver_label.setText(transaction.getReceiver());
        sum_label.setText(Double.toString(transaction.getAmount()));
    }
}
