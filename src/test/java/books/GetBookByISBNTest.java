package books;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@Feature("Get book by isbn")
public class GetBookByISBNTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/books/";

    }

    @DataProvider(name = "TitleByISBN")
    public Object[][] titleByISBN() {
        return new Object[][]{
                {"9788375780741", "Blood of Elves"},
                {"9781974267767", "Call of Cthulu"},
                {"9789544090838", "Pet Sematary"}
        };
    }

    @DataProvider(name = "WrongParameters")
    public Object[] wrongParam() {
        return new Object[]{"12345", "123", "abcdergh"};
    }


    @Test(dataProvider = "TitleByISBN", description = "Check status code and the correctness of title parameter")
    public void getBookByISBN_statusCodeTest(String isbn, String title) {
        given().pathParam("isbn", isbn).when().get("{isbn}")
                .then()
                .statusCode(200)
                .body("title", equalTo(title));
    }


    @Test(dataProvider = "WrongParameters", description = "Check status code, when isbn not exist")
    public void getBookByISBN_wrongParam(String isbn) {
        given().pathParam("isbn", isbn).when().get("{isbn}").then().statusCode(404);
    }


}
