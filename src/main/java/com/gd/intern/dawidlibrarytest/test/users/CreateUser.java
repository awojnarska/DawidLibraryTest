package com.gd.intern.dawidlibrarytest.test.users;

import com.gd.intern.dawidlibrarytest.model.User;
import com.gd.intern.dawidlibrarytest.model.UserBasic;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CreateUser {
    //todo wrong user data

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/users";
    }

    @DataProvider(name = "userData")
    public Object[] userData() {
        return new Object[]{
                new User("Test", "User", "usertest@mail.com", "usertest", "FEMALE", "password", 10, 100.0),
        };
    }

    @DataProvider(name = "userBasicData")
    public Object[] userBasicData() {
        return new Object[]{
                new UserBasic("usertest@mail.com", "testuser", "password")
        };
    }

    @DataProvider(name = "wrongUserData")
    public Object[] wrongUserData() {
        return new Object[]{

        };
    }


    @Test(dataProvider = "userData")
    public void createUser_test(User user) {
        given().contentType("application/json").body(user).when().post()
                .then().statusCode(200)
                .body("accountBalance", equalTo((float)user.getAccountBalance()),
                        "age", equalTo(user.getAge()),
                        "email", equalTo(user.getEmail()),
                        "firstName", equalTo(user.getFirstName()),
                        "gender", equalTo(user.getGender()),
                        "lastName", equalTo(user.getLastName()),
                        "username", equalTo(user.getUsername()));
    }

    @Test(dataProvider = "userBasicData")
    public void createUser_test(UserBasic user) {
        given().contentType("application/json").body(user).when().post()
                .then().statusCode(200)
                .body("accountBalance", equalTo(0.0f),
                        "age", equalTo(0),
                        "email", equalTo(user.getEmail()),
                        "firstName", equalTo(null),
                        "gender", equalTo("RATHER_NOT_SAY"),
                        "lastName", equalTo(null),
                        "username", equalTo(user.getUsername()));
    }


    @Test(dataProvider = "UserExist")
    public void createUser_userExist(User user) {
        given().contentType("application/json").body(user).when().post()
                .then().statusCode(400);
    }

}
