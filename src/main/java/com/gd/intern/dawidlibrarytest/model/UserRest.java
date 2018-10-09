package com.gd.intern.dawidlibrarytest.model;

import java.util.Objects;

public class UserRest {

    private String publicUserId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private Gender gender;
    private double accountBalance;

    public String getPublicUserId() {
        return publicUserId;
    }

    public void setPublicUserId(String publicUserId) {
        this.publicUserId = publicUserId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRest userRest = (UserRest) o;
        return age == userRest.age &&
                Double.compare(userRest.accountBalance, accountBalance) == 0 &&
                Objects.equals(publicUserId, userRest.publicUserId) &&
                Objects.equals(username, userRest.username) &&
                Objects.equals(firstName, userRest.firstName) &&
                Objects.equals(lastName, userRest.lastName) &&
                Objects.equals(email, userRest.email) &&
                gender == userRest.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicUserId, username, firstName, lastName, email, age, gender, accountBalance);
    }

    @Override
    public String toString() {
        return "UserRest{" +
                "publicUserId='" + publicUserId + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
