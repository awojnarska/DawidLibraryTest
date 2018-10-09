package users;

import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static com.gd.intern.dawidlibrarytest.service.OrderService.createOrder;
import static com.gd.intern.dawidlibrarytest.service.UserService.createUser;
import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.equalTo;

@Feature("List of user's favourite genres")
public class FavouriteGenresTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE));
    }

    @DataProvider(name = "wrongUsername")
    public Object[] dataWrongUsername() {
        return new Object[]{
                "kot", "leczo", "1234"
        };
    }

    @Test(dataProvider = "wrongUsername", description = "Getting list of favourite genres - incorrect username")
    public void testFavouriteGenres_incorrectUsername(String username) {
        given().pathParam("username", username).when().get("users/{username}/favorites").then()
                .statusCode(404);
    }


    @Test(description = "Getting list of favourite genres test")
    public void testFavouriteGenres() {
        //create user
        createUser("Test", "Genres", "testgenres@meil.com",
                "testgenres", RATHER_NOT_SAY, "password", 20, 3000.00);

        //create orders
        createOrder("9781478965008", "testgenres");
        createOrder("9781974267767", "testgenres");

        given().pathParam("username", "testgenres").when().get("users/{username}/favorites").then()
                .contentType("application/json")
                .statusCode(200)
                .body("'short story'", equalTo(1),
                        "fantasy", equalTo(1),
                        "horror", equalTo(1),
                        "novel", equalTo(1));
    }


}
