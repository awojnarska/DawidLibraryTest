package com.gd.intern.dawidlibrarytest.util;

import com.gd.intern.dawidlibrarytest.model.Order;

import static io.restassured.RestAssured.given;

public class CreateOrderDB {

    public static void createOrder(String isbn, String username) {
        Order order = new Order(isbn, username);
        given().contentType("application/json").body(order).when()
                .post("http://localhost:8080/virtual-library-ws/orders");
    }
}
