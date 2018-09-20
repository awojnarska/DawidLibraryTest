package com.gd.intern.dawidlibrarytest.test.books;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

public class FindBookByTitle {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/books/";

    }

    @DataProvider(name = "properFragmentOfTitle")
    public Object[] properFragment() {
        return new Object[]{
                "Blood of Elves", "Call of Cthulu", "Pet Sematary", "of", "Horror"
        };
    }

    @DataProvider(name = "properFragmentCaseSensitive")
    public Object[] properFragmentCaseSensitive() {
        return new Object[]{
                "BLOOD", "CalL of cthulu", "PeT", "OF", "HorRor"
        };
    }

    @DataProvider(name = "incorrectFragmentOfTitle")
    public Object[] incorrectFragment() {
        return new Object[]{
                "12345", "jejejee", "abc", "fgh"
        };
    }


    @Test(dataProvider = "properFragmentOfTitle")
    public void findBookByTitle_statusCodeTest(String find) {
        given().param("find", find).param("page", 0).param("limit", 10)
                .when().get()
                .then().statusCode(200);
    }


    @Test(dataProvider = "properFragmentCaseSensitive")
    public void findBookByTitle_properFragmentCaseSensitiveStatusCodeTest(String find) {
        given().param("find", find).param("page", 0).param("limit", 10)
                .when().get().then().statusCode(200);
    }

    @Test(dataProvider = "incorrectFragmentOfTitle")
    public void findBookByTitle_incorrectParamStatusCodeTest(String find) {
        given().param("find", find).param("page", 0).param("limit", 10)
                .when().get().then().statusCode(404);
    }


    @Test(dataProvider = "incorrectFragmentOfTitle")
    public void findBookByTitle_incorrectFragmentCountElements(String find) {
        given().param("find", find).param("page", 0).param("limit", 10)
                .when().get()
                .then().contentType(ContentType.JSON).
                body("list.size()", is(0));
    }
}
