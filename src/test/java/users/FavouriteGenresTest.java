package users;

import com.gd.intern.dawidlibrarytest.util.CreateOrderDB;
import com.gd.intern.dawidlibrarytest.util.CreateUserDB;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.equalTo;

public class FavouriteGenresTest {

    @BeforeClass
    public void setup() {
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE));

        //create users
        CreateUserDB.createUser("Test", "Genres", "testgenres@meil.com",
                "testgenres", "RATHER_NOT_SAY", "password", 20, 3000.00);

        //create orders
        CreateOrderDB.createOrder("9781478965008", "testgenres");
        CreateOrderDB.createOrder("9781974267767", "testgenres");

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

 /*   @DataProvider(name = "usernameWithGenres")
    public Object[][] getFavouriteGenres() {
        Map<String, Integer> ilyaGenres = new HashMap<>();
        ilyaGenres.put("short stories collection", 1);
        ilyaGenres.put("fantasy", 3);
        ilyaGenres.put("novel", 2);

        Map<String, Integer> dgabkaGenres = new HashMap<>();
        dgabkaGenres.put("short story", 2);
        dgabkaGenres.put("horror", 4);
        dgabkaGenres.put("novel", 2);

        Map<String, Integer> testgenresGenres = new HashMap<>();
        testgenresGenres.put("short story", 1);
        testgenresGenres.put("fantasy", 1);
        testgenresGenres.put("horror", 1);
        testgenresGenres.put("novel", 1);

        return new Object[][]{{"ilya", ilyaGenres}, {"dgabka", dgabkaGenres},{"testgenres", testgenresGenres} };
    }
*/

    @Test(dataProvider = "username")
    public void properUsernameTest(String username) {
        given().pathParam("username", username).when().get("http://localhost:8080/virtual-library-ws/users/{username}/favorites").then()
                .statusCode(200);
    }

    @Test(dataProvider = "wrongUsername")
    public void incorrectUsernameTest(String username) {
        given().pathParam("username", username).when().get("http://localhost:8080/virtual-library-ws/users/{username}/favorites").then()
                .statusCode(404);
    }

    @Test
    public void getFavouriteGenresTest_userIlya() {
        given().pathParam("username", "ilya").when().get("http://localhost:8080/virtual-library-ws/users/{username}/favorites").then()
                .contentType("application/json")
                .statusCode(200)
                .body("'short stories collection'", equalTo(1),
                        "fantasy", equalTo(3),
                        "novel", equalTo(2));
    }

    @Test
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
