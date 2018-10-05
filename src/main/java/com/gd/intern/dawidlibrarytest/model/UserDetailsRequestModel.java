package com.gd.intern.dawidlibrarytest.model;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;

public class UserDetailsRequestModel {


    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Gender gender;
    private String password;
    private int age;
    private double accountBalance;

    public UserDetailsRequestModel(String firstName, String lastName, String email, String username, Gender gender, String password, int age, double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.password = password;
        this.age = age;
        this.accountBalance = accountBalance;
    }

    public UserDetailsRequestModel(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = RATHER_NOT_SAY;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "UserDetailsRequestModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
