package com.gd.intern.dawidlibrarytest.model;

public class User {

    String firstName;
    String lastName;
    String email;
    String username;
    String gender;
    String password;
    int age;
    double accountBalance;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public User(String firstName, String lastName, String email, String username, String gender, String password, int age, double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.password = password;
        this.age = age;
        this.accountBalance = accountBalance;
    }

    public User(String email, String username, String gender, String password) {
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
