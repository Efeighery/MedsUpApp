package com.example.medsupapp;

// This is needed to both save user account details and also to retrieve the details to display them in the user profile
public class User {

    // These variables are needed to save a user account in the users table
    String name, age, sex, email, password;

    // An empty constructor and a full one is used to initialise the variables
    public User() {
    }

    public User(String name, String age, String sex, String email, String password) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }

    // Getter and setter methods for each of the variables
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
