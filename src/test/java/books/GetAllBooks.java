package books;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAllBooks {

    @Test
    public void getListOfAllBooksStatusCodeTest() {
        given().when().get("virtual-library-ws/books").then().statusCode(200);
    }


}
