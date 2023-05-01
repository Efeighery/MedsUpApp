package com.example.medsupapp;

/*
 *  Class name: User.java
 *
 *  Version: Revision 1
 *
 *  Date e.g. 08/02/2023
 *
 * @author Eoghan Feighery, x19413886
 *
 */

/*
 *
 * @reference: https://www.youtube.com/watch?v=-plgl1EQ21Q&list=PL65Ccv9j4eZJ_bg0TlmxA7ZNbS8IMyl5i&index=7/User.java
 *
 */


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
