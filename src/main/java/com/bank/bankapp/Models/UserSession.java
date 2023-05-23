package com.bank.bankapp.Models;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Random;

public class UserSession {
    private static UserSession instance;
    private String userEmail;
    private UserSession(){

    }
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    public void setUserEmail(String email) {
        userEmail = email;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void clearUserSession() {
        userEmail = null;
    }
}
