package com.gd.intern.dawidlibrarytest.test.orders;

import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.util.CreateUserDB;
import com.gd.intern.dawidlibrarytest.util.CreateOrderDB;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GiftBook {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/orders/gift";

        //create users
        CreateUserDB.createUser("Test", "Order - Gift", "testordergift1@meil.com",
                "testordergift1", "RATHER_NOT_SAY", "password", 99, 3000.00);
        CreateUserDB.createUser("Test", "Order - Gift", "testordergift2@meil.com",
                "testordergift2", "RATHER_NOT_SAY", "password", 99, 25.00);

        //create orders
        CreateOrderDB.createOrder("9781478965008", "testordergift1");
        CreateOrderDB.createOrder("9781974267767", "testordergift1");
        CreateOrderDB.createOrder("9781974267767", "testordergift2");

    }

    @DataProvider(name = "giftBook")
    public Object[][] giftBookData() {
        return new Object[][]{
                {"9781478965008", "testordergift1", "testordergift2",  200}, //good
                {"9788375780741", "testordergift1", "testordergift2",  404}, //user who give don't have it
                {"9781974267767", "testordergift1", "testordergift2",  400}, //user to be gifted have book
                {"9781974267767", "testordergift2", "testordergift2",  400}, //give book to yourself
                {"9781974267761", "testordergift2", "testordergift1",  404}, //book don't exist //todo wrong message?
                {"978197426776111111", "testordergift2", "testordergift1",  400}, //ISBN too long
                {"9781974267767", "testorderg", "testordergift1",  404}, //username don't exist

        };
    }


    @Test(dataProvider = "giftBook")
    public void giftBookTest(String isbn, String user1, String user2, int status) {
        Order gift = new Order(isbn, user1);
        given().queryParam("to", user2).contentType("application/json").body(gift)
                .when().post()
                .then().statusCode(status);
    }

    @Test
    public void giftBookTest_withoutUsername() {
        Map<String, String> gift = new HashMap<>();
        gift.put("isbn", "9781974267767");
        given().queryParam("to", "testordergift2").contentType("application/json").body(gift)
                .when().post()
                .then().statusCode(400);
    }

    @Test
    public void giftBookTest_withoutISBN() {
        Map<String, String> gift = new HashMap<>();
        gift.put("username", "testordergift2");
        given().queryParam("to", "testordergift2").contentType("application/json").body(gift)
                .when().post()
                .then().statusCode(400);
    }


}
