package com.gd.intern.dawidlibrarytest.util;

import com.gd.intern.dawidlibrarytest.model.User;

import static io.restassured.RestAssured.given;


public class CreateUserDB {

    public static void createUser(String firstName, String lastName, String email, String username, String gender, String password, int age, double accountBalance) {

        User user = new User(firstName, lastName, email, username, gender, password, age, accountBalance);
        given().contentType("application/json").body(user).when().post("http://localhost:8080/virtual-library-ws/users");

    }
}
