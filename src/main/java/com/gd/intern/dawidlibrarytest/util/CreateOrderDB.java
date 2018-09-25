package com.gd.intern.dawidlibrarytest.util;

import com.gd.intern.dawidlibrarytest.model.Order;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class CreateOrderDB {

    @Step("Create new order to database")
    public static void createOrder(String isbn, String username) {
        Order order = new Order(isbn, username);
        given().contentType("application/json").body(order).when()
                .post("http://localhost:8080/virtual-library-ws/orders");
    }
}
