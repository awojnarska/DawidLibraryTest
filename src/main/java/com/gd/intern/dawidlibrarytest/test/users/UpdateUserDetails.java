package com.gd.intern.dawidlibrarytest.test.users;

import com.gd.intern.dawidlibrarytest.model.User;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserDetails {
    //todo wartości, których nie można dodać
    //todo publicuserid, który nie istnieje
    //todo poprawić, że pierwsze tworzę usera, a potem go update, wyjść z założenia, że tworze usera do testów

    @DataProvider(name = "publicUserId")
    public Object[][] publicUserId() {
        return new Object[][]{
                {"8CnHLxNh06ZfmtfPBoV1c6slRU0Dk3", "ilya"},
                {"MmVhfTO044WdTacJWXbqWkHbuuwJxT", "dgabka"},
                {"FoLCMTWJrNjObVkIM5zGgquNb5LI9b", "string"}
        };
    }


    @Test
    public void updateUser() {

        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("age", 30);
        userUpdate.put("firstName", "Marcin");
        userUpdate.put("accountBalance", 3000.0);
        /*User user = new User("updatetest@mail.pl", "updatetest",
                "RATHER_NOT_SAY", "password");

            String publicUserId = given().contentType("application/json").body(user)
                .when().post("http://localhost:8080/virtual-library-ws/users")
                .then().body("username", equalTo(user.getUsername())).extract().path("publicUserId");
*/
        String publicUserId = "9K5geeh2JF3KLJcHDGp0PZNpjjEQvq";
        given().contentType("application/json").body(userUpdate).pathParam("id", publicUserId)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(200)
                .body("age", equalTo(userUpdate.get("age")),
                        "firstName", equalTo(userUpdate.get("firstName")),
                        "accountBalance", equalTo(userUpdate.get("accountBalance")));
    }




}
