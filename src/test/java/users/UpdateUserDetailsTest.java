package users;

import com.gd.intern.dawidlibrarytest.service.UserService;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Feature("Update User Details")
public class UpdateUserDetailsTest {
    @Test(description = "Update user test with proper values")
    public void updateUserTest() {
        //create user and get publicUserId
        String id = UserService.createUserAndGetId("Test", "Update", "testupdate1@mail.com",
                "testupdate1", RATHER_NOT_SAY, "password", 99, 3000.00);


        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("age", 30);
        userUpdate.put("firstName", "TestName");
        userUpdate.put("lastName", "TestLastName");
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


    @Test(description = "Update user test with proper values - last name")
    public void updateUserTest_updateLastName() {

        //create user and get publicUserId
        String id = UserService.createUserAndGetId("Test", "Update", "testupdate3@mail.com",
                "testupdate3", RATHER_NOT_SAY, "password", 99, 3000.00);

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("lastName", "newlastname");

        //update user
        given().contentType("application/json").body(userUpdate).pathParam("id", id)
                .when().put("http://localhost:8080/virtual-library-ws/users/{id}")
                .then().statusCode(200)
                .body("lastName", equalTo(userUpdate.get("lastName")),
                        "age", equalTo(99),
                        "firstName", equalTo("Test"),
                        "gender", equalTo("RATHER_NOT_SAY"),
                        "email", equalTo("testupdate3@mail.com"));
    }

    @DataProvider(name = "updateOneParameter")
    public Object[][] updateOneParameter() {


        return new Object[][]{ //String id, String key, Object value
                //update email exist
                {UserService.createUserAndGetId("Test", "Update", "testupdate4@mail.com",
                        "testupdate4", RATHER_NOT_SAY, "password", 99, 3000.00),
                        "email",
                        "ilya@email.com"},
                //wrong age
                {UserService.createUserAndGetId("Test", "Update", "testupdate5@mail.com",
                        "testupdate5", RATHER_NOT_SAY, "password", 99, 3000.00),
                        "age", -99}
        };
    }

    @Step("UserDetailsRequestModel id: [0], key to be update: [1], value to be update: [2]")
    @Test(dataProvider = "updateOneParameter", description = "Update user test - update one value")
    public void updateUserTest_updateOneParameter(String id, String key, Object value) {
        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put(key, value);

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


    @Step("UserDetailsRequestModel id: [0]")
    @Test(dataProvider = "wrongUserId", description = "Update user test - wrong id")
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
