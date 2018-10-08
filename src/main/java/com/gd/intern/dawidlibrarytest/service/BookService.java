package com.gd.intern.dawidlibrarytest.service;

import com.gd.intern.dawidlibrarytest.model.Author;
import com.gd.intern.dawidlibrarytest.model.Book;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BookService {

    @Step("Get all books")
    public static List<Book> findAllBooks() {
        return new ArrayList<>(Arrays.asList(given().param("page", 0).param("limit", 10000)
                .when().get("books")
                .then()
                .statusCode(200).contentType("application/json").extract().as(Book[].class)));
    }


    @Step("Get Book by ISBN")
    public static Book getBookByISBN(String isbn) {

        return given().pathParam("isbn", isbn).when().get("books/{isbn}")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().as(Book.class);

    }

    @Step("Check if the book assert equals ")
    public static void bookAssertEquals(Book book, String title, String isbn, int pages, double price, int publicationYear, Author author) {
        assertEquals(book.getTitle(), title);
        assertEquals(book.getIsbn(), isbn);
        assertEquals(book.getPages(), pages);
        assertEquals(book.getPrice(), price);
        assertEquals(book.getPublicationYear(), publicationYear);
        assertEquals(book.getAuthor(), author);
    }

    @Step("Get Book by ISBN - book not found")
    public static void bookNotFound(String isbn) {
        given().pathParam("isbn", isbn)
                .when().get("books/{isbn}").then().statusCode(404);

    }

    @Step("Find Book by fragment of title")
    public static List<Book> findBookByFragmentOfTitle(String title) {
        return new ArrayList<>(Arrays.asList(given().param("find", title).param("page", 0).param("limit", 1000)
                .when().get("books/")
                .then().statusCode(200)
                .contentType("application/json")
                .extract().as(Book[].class)));
    }

    @Step("Check if the fragment of title assert equals")
    public static void titleAssertEquals(List<Book> books, String title) {
        for (Book b : books) {
            assertTrue(b.getTitle().toUpperCase().contains(title.toUpperCase()));
        }
    }


}
