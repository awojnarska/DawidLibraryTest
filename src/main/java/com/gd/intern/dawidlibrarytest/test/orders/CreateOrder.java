package com.gd.intern.dawidlibrarytest.test.orders;

import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.util.CreateUserDB;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateOrder {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/orders";

        //create users
        CreateUserDB.createUser("Test", "Order", "testorder1@meil.com",
                "testorder1", "RATHER_NOT_SAY", "password", 99, 3000.00);
        CreateUserDB.createUser("Test", "Order", "testorder2@meil.com",
                "testorder2", "RATHER_NOT_SAY", "password", 20, 10.00);
    }



    @DataProvider(name = "postOrder")
    public Object[][] createOrderData() {
        return new Object[][]{
                {"9781974267767", "dgabka", 400}, //existOrder
                {"9788375780741", "ilya", 400}, //existOrder
                {"9781974267767", "testorder1", 200}, //correctOrder
                {"9781974267767", "testorder2", 400}, //notEnoughMoney
                {"9781478965008", "testorderr", 404}, //wrong username
                {"9781478965025", "testorder1", 404}, //wrong ISBN
                {"9781478965008", "testorderr", 404}, //wrong isbn & username
        };
    }

    @Test(dataProvider = "postOrder")
    public void postOrder_statusCode(String isbn, String username, int status) {
        given().contentType("application/json").body(new Order(isbn, username))
                .when().post()
                .then().statusCode(status);
    }

}
