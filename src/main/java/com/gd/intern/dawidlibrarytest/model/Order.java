package com.gd.intern.dawidlibrarytest.model;

public class Order {

    String isbn;
    String username;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Order(String isbn, String username) {
        this.isbn = isbn;
        this.username = username;
    }
}
