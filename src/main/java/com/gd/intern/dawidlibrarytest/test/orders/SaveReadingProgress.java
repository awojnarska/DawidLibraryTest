package com.gd.intern.dawidlibrarytest.test.orders;

import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.util.CreateOrderDB;
import com.gd.intern.dawidlibrarytest.util.CreateUserDB;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SaveReadingProgress {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/orders/read";

        //create users
        CreateUserDB.createUser("Test", "Order - Read", "testorderread1@mail.com",
                "testorderread1", "RATHER_NOT_SAY", "password", 20, 3000.00);

        //create orders
        CreateOrderDB.createOrder("9781478965008", "testorderread1");
        CreateOrderDB.createOrder("9781974267767", "testorderread1");
    }

    @DataProvider(name = "properData")
    public Object[][] saveReadingData() {
        return new Object[][]{
                {"9781478965008", "testorderread1", 0},
                {"9781478965008", "testorderread1", 50},
                {"9781478965008", "testorderread1", 100},
                {"9781478965008", "testorderread1", 464}
        };
    }

    @DataProvider(name = "incorrectData")
    public Object[][] saveReadingData_incorrectPages() {
        return new Object[][]{
                {"9781478965008", "testorderread1", -1, 400}, //too small no pages
                {"9781478965008", "testorderread1", -100, 400}, //too small no pages
                {"9781478965008", "testorderread1", 900, 400}, //too big no pages
                {"9781478965008", "testorderread1", 465, 400}, //too big no pages
                {"9781478965008", "testorderread", 100, 404}, //username don't exist
                {"9781478965025", "testorderread1", 100, 404}, //wrong ISBN
                {"9781478965008", "testorderread", 100, 404}, //wrong isbn & username
        };
    }


    @Test(dataProvider = "properData")
    public void saveReadingProgress_correctPageNumber(String isbn, String username, int pages) {
        Order order = new Order(isbn, username);
        given().queryParam("no", pages).contentType("application/json").body(order)
                .when().put()
                .then()
                .statusCode(200);
    }

    @Test(dataProvider = "incorrectData")
    public void saveReadingProgress_incorrectData(String isbn, String username, int pages, int status) {
        Order order = new Order(isbn, username);
        given().queryParam("no", pages).contentType("application/json").body(order)
                .when().put()
                .then()
                .statusCode(status);
    }


}
