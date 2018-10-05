package users;

import com.gd.intern.dawidlibrarytest.util.OrderService;
import com.gd.intern.dawidlibrarytest.util.UserService;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.equalTo;

@Feature("List of user's favourite genres")
public class FavouriteGenresTest {

    @BeforeClass
    public void setup() {
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE));

        //create users
        UserService.createUser("Test", "Genres", "testgenres@meil.com",
                "testgenres", "RATHER_NOT_SAY", "password", 20, 3000.00);

        //create orders
        OrderService.createOrder("9781478965008", "testgenres");
        OrderService.createOrder("9781974267767", "testgenres");

    }

    @DataProvider(name = "username")
    public Object[] username() {
        return new Object[]{
                "ilya", "dgabka"
        };
    }

    @DataProvider(name = "wrongUsername")
    public Object[] wrongUsername() {
        return new Object[]{
                "kot", "leczo", "1234"
        };
    }

    @Step("Username of user: [0]")
    @Test(dataProvider = "username", description = "Getting list of favourite genres - proper username")
    public void properUsernameTest(String username) {
        given().pathParam("username", username).when().get("http://localhost:8080/virtual-library-ws/users/{username}/favorites").then()
                .statusCode(200);
    }

    @Step("Username of user: [0]")
    @Test(dataProvider = "wrongUsername", description="Getting list of favourite genres - incorrect username")
    public void incorrectUsernameTest(String username) {
        given().pathParam("username", username).when().get("http://localhost:8080/virtual-library-ws/users/{username}/favorites").then()
                .statusCode(404);
    }

    @Test(description="Getting list of favourite genres of existing user")
    public void getFavouriteGenresTest_userIlya() {
        given().pathParam("username", "ilya").when().get("http://localhost:8080/virtual-library-ws/users/{username}/favorites").then()
                .contentType("application/json")
                .statusCode(200)
                .body("'short stories collection'", equalTo(1),
                        "fantasy", equalTo(3),
                        "novel", equalTo(2));
    }

    @Test(description="Getting list of favourite genres of existing user")
    public void getFavouriteGenresTest_userTestgenres() {
        given().pathParam("username", "testgenres").when().get("http://localhost:8080/virtual-library-ws/users/{username}/favorites").then()
                .contentType("application/json")
                .statusCode(200)
                .body("'short story'", equalTo(1),
                        "fantasy", equalTo(1),
                        "horror", equalTo(1),
                        "novel", equalTo(1));
    }


}
