package users;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class FavouriteGenres {

    @DataProvider(name = "username")
    public Object[] username() {
        return new Object[]{
                "ilya", "dgabka", "string"
        };
    }

    @DataProvider(name = "wrongUsername")
    public Object[] wrongUsername() {
        return new Object[]{
                "kot", "leczo", "1234"
        };
    }

    @DataProvider(name = "usernameWithGenres")
    public Object[][] getFavouriteGenres() {
        Map<String, Integer> nikiMap = new HashMap<>();
        nikiMap.put("short stories collection", 1);
        nikiMap.put("fantasy", 1);
        nikiMap.put("horror", 1);
        nikiMap.put("novel", 1);

        return new Object[][]{{"niki", nikiMap}};
    }


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

   /* @Test(dataProvider = "usernameWithGenres")
    public void getFavouriteGenresTest(String username, Map<String, Integer> map) {
        given().pathParam("username", username).when().get("http://localhost:8080/virtual-library-ws/users/{username}/favorites").then()
                .contentType("application/json")
                .statusCode(200)
                .body()
        ;
    }*/


}
