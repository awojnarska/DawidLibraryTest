package com.gd.intern.dawidlibrarytest.model.rest;

import java.sql.Timestamp;
import java.util.Objects;

public class OrderRest {
    private UserRest userRest;
    private BookRest bookRest;
    private Timestamp purchaseDate;
    private float readingProgress;

    public OrderRest() {
        this.userRest = new UserRest();
        this.bookRest = new BookRest();
    }

    public UserRest getUserRest() {
        return userRest;
    }

    public void setUserRest(UserRest userRest) {
        this.userRest = userRest;
    }

    public BookRest getBookRest() {
        return bookRest;
    }

    public void setBookRest(BookRest bookRest) {
        this.bookRest = bookRest;
    }

    public Timestamp getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Timestamp purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getReadingProgress() {
        return readingProgress;
    }

    public void setReadingProgress(float readingProgress) {
        this.readingProgress = readingProgress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRest orderRest = (OrderRest) o;
        return Float.compare(orderRest.readingProgress, readingProgress) == 0 &&
                Objects.equals(userRest, orderRest.userRest) &&
                Objects.equals(bookRest, orderRest.bookRest) &&
                Objects.equals(purchaseDate, orderRest.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRest, bookRest, purchaseDate, readingProgress);
    }

    @Override
    public String toString() {
        return "OrderRest{" +
                "userRest=" + userRest +
                ", bookRest=" + bookRest +
                ", purchaseDate=" + purchaseDate +
                ", readingProgress=" + readingProgress +
                '}';
    }
}
