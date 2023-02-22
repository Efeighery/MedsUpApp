package com.example.medsupapp;

public class ContactItem {

    String cID, name, email, phone;


    // An empty constructor is needed for the instantiable class
    public ContactItem() {
    }

    // Another constructor is used to initialise the variables needed
    public ContactItem(String cID, String name, String email, String phone) {
        this.cID = cID;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getter and setter methods for all 4 of the variables
    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
