package com.gd.intern.dawidlibrarytest.service;

import com.gd.intern.dawidlibrarytest.model.rest.BookRest;
import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.model.rest.OrderRest;
import com.gd.intern.dawidlibrarytest.model.rest.UserRest;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class OrderService {

    @Step("Create new order")
    public static OrderRest createOrder(String isbn, String username) {
        Order order = new Order(isbn, username);
        return given().contentType("application/json")
                .body(order)
                .when()
                .post("orders")
                .then().statusCode(200)
                .contentType("application/json")
                .extract().as(OrderRest.class);
    }


    @Step("Check if the order assert equals ")
    public static void orderAssertEquals(OrderRest order, BookRest book, UserRest user) {
        assertEquals(order.getBookRest(), book);
        assertEquals(order.getUserRest(), user);

    }

    @Step("Gift book to another User")
    public static OrderRest giftBook(String isbn, String user1, String user2) {
        Order gift = new Order(isbn, user1);
        return given().queryParam("to", user2).contentType("application/json").body(gift)
                .when().post("orders/gift")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().as(OrderRest.class);
    }

    @Step("Gift book to another User with incorrect values")
    public static void giftBook_incorrectValues(String isbn, String user1, String user2, int status) {
        Order gift = new Order(isbn, user1);
        given().queryParam("to", user2).contentType("application/json").body(gift)
                .when().post("orders/gift")
                .then().statusCode(status);
    }

    @Step("Save Reading Progress")
    public static OrderRest saveReadingProgress(String isbn, String username, int pages) {
        Order order = new Order(isbn, username);
        return given().queryParam("no", pages).contentType("application/json").body(order)
                .when().put("orders/read")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().as(OrderRest.class);
    }

    @Step("Save reading progress with incorrect values")
    public static void saveReadingProgress_incorrectData(String isbn, String username, int pages, int status) {
        Order order = new Order(isbn, username);
        given().queryParam("no", pages).contentType("application/json").body(order)
                .when().put("orders/read")
                .then()
                .statusCode(status);
    }

    @Step("Get percent of read book")
    public static double getReadPercent(int readPages, int bookPages) {
        return ((double) readPages / bookPages) * 100;
    }


}
