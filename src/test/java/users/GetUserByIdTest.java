package users;

import com.gd.intern.dawidlibrarytest.model.User;
import com.gd.intern.dawidlibrarytest.util.CreateUserDB;
import io.qameta.allure.Feature;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Feature("Get user by id")
public class GetUserByIdTest {

    @DataProvider(name = "publicUserId")
    public Object[][] publicUserId() {
        return new Object[][]{
                {"8CnHLxNh06ZfmtfPBoV1c6slRU0Dk3", "ilya"},
                {"MmVhfTO044WdTacJWXbqWkHbuuwJxT", "dgabka"},
        };
    }

    @DataProvider(name = "incorrectPublicUserId")
    public Object[] incorrectPublicUserId() {
        return new Object[]{"8CnHLxNh06ZfmtfPBoV1cf6slahnjkl", "aaabavahbahjkjabjhadhjkashkasjh", "ghjhbacjhbcjsahcjajhcbacjsh"};
    }


    @Test(dataProvider = "publicUserId", description = "Getting user by id - existing user")
    public void properPublicUserId(String id, String login) {
        given().pathParam("id", id).when().get("http://localhost:8080/virtual-library-ws/users/{id}").then()
                .statusCode(200)
                .body("username", equalTo(login));
    }

    @Test(description = "Getting user by id - first create new user")
    public void getUserById_Test() {

        //create user
        String id = CreateUserDB.createUserAndGetId("Test", "GetUser", "getuserbyid@mail.com",
                "getuserbyid", "RATHER_NOT_SAY", "password", 100, 3000.00);

        given().pathParam("id", id).when().get("http://localhost:8080/virtual-library-ws/users/{id}").then()
                .statusCode(200)
                .body("age", equalTo(100),
                        "firstName", equalTo("Test"),
                        "lastName", equalTo("GetUser"),
                        "gender", equalTo("RATHER_NOT_SAY"),
                        "email", equalTo("getuserbyid@mail.com"));
    }


    @Test(dataProvider = "incorrectPublicUserId", description = "Getting user by id - incorrect public user id")
    public void incorrectPublicUserId(String id) {
        given().pathParam("id", id).when().get("http://localhost:8080/virtual-library-ws/users/{id}").then()
                .statusCode(404);
    }

}


