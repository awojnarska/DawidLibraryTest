package com.gd.intern.dawidlibrarytest.test.books;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;


public class GetAllBooks {


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/books";

    }

    @DataProvider(name = "pageAndNumber")
    public Object[] pageAndNumber() {
        return new Object[][]{
                {0, 5}, {0, 2}, {0, 3}, {1, 3}, {2, 1}
        };
    }

    @Test
    public void getListOfAllBooks_statusCodeTest() {
        given().when().get().then().statusCode(200);
    }

    @Test
    public void getListOfAllBooks_countElementsOfList() {
        given().param("page", 0).param("limit", 10)
                .when().get()
                .then().contentType(ContentType.JSON).
                body("list.size()", is(7)); //only 7 books in database
    }

    @Test(dataProvider = "pageAndNumber")
    public void getListOfAllBooks_countElementsOfList_changeNumberResult(int page, int limit) {
        given().param("page", page).param("limit", limit)
                .when().get()
                .then().contentType(ContentType.JSON).
                body("list.size()", is(limit)); //only 7 books in database
    }
    //todo czy zawierają się elementy
}
