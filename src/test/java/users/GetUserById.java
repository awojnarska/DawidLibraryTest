package users;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetUserById {

    @DataProvider(name = "publicUserId")
    public Object[][] publicUserId() {
        return new Object[][]{
                {"8CnHLxNh06ZfmtfPBoV1c6slRU0Dk3", "ilya"},
                {"MmVhfTO044WdTacJWXbqWkHbuuwJxT", "dgabka"},
                {"FoLCMTWJrNjObVkIM5zGgquNb5LI9b", "string"}
        };
    }

    @DataProvider(name = "incorrectPublicUserId")
    public Object[] incorrectPublicUserId() {
        return new Object[]{"8CnHLxNh06ZfmtfPBoV1c6slahnjkl", "aaabavahbahjkjabjhadhjkashkasjh", "ghjhbacjhbcjsahcjajhcbacjsh"};
    }


    @Test(dataProvider = "publicUserId")
    public void properPublicUserId(String id, String login) {
        given().pathParam("id", id).when().get("http://localhost:8080/virtual-library-ws/users/{id}").then()
                .statusCode(200)
                .body("username", equalTo(login));
    }

    @Test(dataProvider = "incorrectPublicUserId")
    public void incorrectPublicUserId(String id) {
        given().pathParam("id", id).when().get("http://localhost:8080/virtual-library-ws/users/{id}").then()
                .statusCode(404);
    }

}


