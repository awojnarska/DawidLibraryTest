package com.gd.intern.dawidlibrarytest.model.rest;

import java.util.Objects;

public class BookRest {

    private String title;
    private AuthorRest author;
    private String isbn;
    private int publicationYear;
    private int pages;
    private double price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AuthorRest getAuthor() {
        return author;
    }

    public void setAuthor(AuthorRest author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRest bookRest = (BookRest) o;
        return publicationYear == bookRest.publicationYear &&
                pages == bookRest.pages &&
                Double.compare(bookRest.price, price) == 0 &&
                Objects.equals(title, bookRest.title) &&
                Objects.equals(author, bookRest.author) &&
                Objects.equals(isbn, bookRest.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn, publicationYear, pages, price);
    }

    @Override
    public String toString() {
        return "BookRest{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", isbn='" + isbn + '\'' +
                ", publicationYear=" + publicationYear +
                ", pages=" + pages +
                ", price=" + price +
                '}';
    }
}
