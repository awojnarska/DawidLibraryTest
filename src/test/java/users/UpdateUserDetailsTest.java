package users;

import com.gd.intern.dawidlibrarytest.model.User;
import com.gd.intern.dawidlibrarytest.util.CreateUserDB;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserDetailsTest {

    //todo dataprovider with map?


    @Test
    public void updateUserTest() {
        //create user and get publicUserId
        Object id = CreateUserDB.createUserAndGetId("Test", "Update", "testupdate1@mail.com",
                "testupdate1", "RATHER_NOT_SAY", "password", 99, 3000.00);


        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("age", 30);
        userUpdate.put("firstName", "TestName");
        userUpdate.put("lastName", "TestLastName");
        userUpdate.put("gender", "MALE");
        userUpdate.put("username", "newtestupdate1");
        userUpdate.put("email", "newtestupdate1@mail.com");

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(200)
                .body("age", equalTo(userUpdate.get("age")),
                        "firstName", equalTo(userUpdate.get("firstName")),
                        "lastName", equalTo(userUpdate.get("lastName")),
                        "email", equalTo(userUpdate.get("email")));
    }

    @Test
    public void updateUserTest_updateAccountBalance() {

        //create user and get publicUserId
        Object id = CreateUserDB.createUserAndGetId("Test", "Update", "testupdate2@mail.com",
                "testupdate2", "RATHER_NOT_SAY", "password", 99, 3000.00);

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("accountBalance", 3500.00);

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(200)
                .body("accountBalance", equalTo(userUpdate.get("accountBalance")));

    }

    @Test
    public void updateUserTest_updateUsername() {

        //create user and get publicUserId
        Object id = CreateUserDB.createUserAndGetId("Test", "Update", "testupdate3@mail.com",
                "testupdate3", "RATHER_NOT_SAY", "password", 99, 3000.00);

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("username", "newtestupdate3");

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(200)
                .body("username", equalTo(userUpdate.get("username")));
    }

    @DataProvider(name = "updateOneParameter")
    public Object[][] updateOneParameter() {


        return new Object[][]{
                {CreateUserDB.createUserAndGetId("Test", "Update", "testupdate4134@mail.com",
                "testupdate4134", "RATHER_NOT_SAY", "password", 99, 3000.00),
                        "email",
                        "ilya@email.com"} //updateEmailExist

        };
    }


    @Test(dataProvider = "updateOneParameter")
    public void updateUserTest_updateEmailExist(Object id, String key, Object value) {
        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put(key, value);

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(400);
    }


    @Test
    public void updateUserTest_wrongEmail() {

        //create user and get publicUserId
        Object id = CreateUserDB.createUserAndGetId("Test", "Update", "testupdate513@mail.com",
                "testupdate513", "RATHER_NOT_SAY", "password", 99, 3000.00);

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("email", "com");

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(400);
    }

    @Test
    public void updateUserTest_wrongGender() {

        //create user and get publicUserId
        Object id = CreateUserDB.createUserAndGetId("Test", "Update", "testupdate6@mail.com",
                "testupdate6", "RATHER_NOT_SAY", "password", 99, 3000.00);

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("gender", "ama");

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(400);
    }

    @Test
    public void updateUserTest_wrongAge() {

        //create user and get publicUserId
        Object id = CreateUserDB.createUserAndGetId("Test", "Update", "testupdate7@mail.com",
                "testupdate7", "RATHER_NOT_SAY", "password", 99, 3000.00);

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("age", -99);

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(400);
    }


    @DataProvider(name = "wrongUserId")
    public Object[] wrongUserId() {
        return new Object[]{
                "abdefgh", "1223123456789098765434567890987654"
        };
    }


    @Test(dataProvider = "wrongUserId")
    public void updateUserTest_wrongId(String id) {

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("username", "newtestupdate3");

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(404);

    }


}
