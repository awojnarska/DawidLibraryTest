package books;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class GetAllBooks {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/books";

    }

    @Test
    public void getListOfAllBooksStatusCodeTest() {
        given().when().get().then().statusCode(200);
    }

//    @Test
//    public void getListOfAllBooksWhenJsonResponseConformsToSchema() {
//        get().then().assertThat().body(matchesJsonSchemaInClasspath("/home/ania/Desktop/JavaPrograms/DawidLibraryTest/src/test/java/books/"));
//    }


}
