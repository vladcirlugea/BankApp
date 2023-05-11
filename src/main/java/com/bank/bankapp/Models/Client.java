package com.bank.bankapp.Models;

import java.util.Date;

public class Client {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Date birthday;
    private String password;
    private Date dateCreated;
    public Client(String firstName, String lastName, String phoneNumber, String email, Date birthday, String password, Date dateCreated){
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
        this.dateCreated = dateCreated;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmail(){
        return email;
    }
    public Date getBirthday(){
        return birthday;
    }
    public Date getDateCreated(){
        return dateCreated;
    }
}
