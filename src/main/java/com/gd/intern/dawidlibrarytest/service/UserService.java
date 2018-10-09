package com.gd.intern.dawidlibrarytest.service;

import com.gd.intern.dawidlibrarytest.model.Gender;
import com.gd.intern.dawidlibrarytest.model.UserDetailsRequestModel;
import com.gd.intern.dawidlibrarytest.model.UserRest;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;


public abstract class UserService {

    @Step("Create new user")
    public static UserRest createUser(String firstName, String lastName, String email, String username, Gender gender, String password, int age, double accountBalance) {

        UserDetailsRequestModel user = new UserDetailsRequestModel(firstName, lastName, email, username, gender, password, age, accountBalance);
        return given().contentType("application/json").body(user)
                .when().post("users")
                .then().statusCode(200)
                .extract().as(UserRest.class);

    }

    @Step("Create new user with basic data")
    public static UserRest createUserBasicData(String email, String username, String password) {

        UserDetailsRequestModel user = new UserDetailsRequestModel(email, username, password);
        return given().contentType("application/json").body(user)
                .when().post("users")
                .then()
                .statusCode(200)
                .extract().as(UserRest.class);

    }

    @Step("Get user by public user id")
    public static UserRest getUserById(String id) {

        return given()
                .pathParam("id", id)
                .when().get("users/{id}")
                .then()
                .statusCode(200)
                .extract().as(UserRest.class);

    }


    @Step("Try to create new user with incorrect data")
    public static void createUser_incorrectData(String firstName, String lastName, String email, String username, Gender gender, String password, int age, double accountBalance) {

        UserDetailsRequestModel user = new UserDetailsRequestModel(firstName, lastName, email, username, gender, password, age, accountBalance);
        createUser_badRequest(user);

    }

    @Step("Try to create new user with incorrect basic data")
    public static void createUser_incorrectBasicData(String email, String username, String password) {

        UserDetailsRequestModel user = new UserDetailsRequestModel(email, username, password);
        createUser_badRequest(user);

    }

    @Step("Get status code, when create new user - bad request")
    public static void createUser_badRequest(Object user) {
        given().contentType("application/json").body(user)
                .when().post("users")
                .then().statusCode(400);
    }

    @Step("Get user by public user id - user not found")
    public static void getUserById_userNotFound(String id) {

        given()
                .pathParam("id", id)
                .when().get("users/{id}")
                .then()
                .statusCode(404);

    }


    @Step("Create new user to database and return his publicUserId")
    public static String createUserAndGetId(String firstName, String lastName, String email, String username, Gender gender, String password, int age, double accountBalance) {

        UserDetailsRequestModel user = new UserDetailsRequestModel(firstName, lastName, email, username, gender, password, age, accountBalance);
        return given().contentType("application/json").body(user).when().post("users").jsonPath().get("publicUserId");
    }


    @Step("Try to create new user using one parameter")
    public static void createUser_oneParameter(String parameterName, Object parameterValue) {
        Map<String, Object> user = new HashMap<>();
        user.put(parameterName, parameterValue);
        createUser_badRequest(user);
    }


    @Step("Check if the user assert equals ")
    public static void userAssertEquals(UserRest user, String firstName, String lastName, String email, String username, Gender gender, int age, double accountBalance) {
        assertEquals(user.getAccountBalance(), accountBalance);
        assertEquals(user.getAge(), age);
        assertEquals(user.getEmail(), email);
        assertEquals(user.getFirstName(), firstName);
        assertEquals(user.getGender(), gender);
        assertEquals(user.getLastName(), lastName);
        assertEquals(user.getUsername(), username);
    }

    @Step("Check if the user assert equals ")
    public static void userAssertEquals(UserRest user, String email, String username) {
        assertEquals(user.getAccountBalance(), 0.00);
        assertEquals(user.getAge(), 0);
        assertEquals(user.getEmail(), email);
        assertNull(user.getFirstName());
        assertEquals(user.getGender(), RATHER_NOT_SAY);
        assertNull(user.getLastName());
        assertEquals(user.getUsername(), username);
    }


}
