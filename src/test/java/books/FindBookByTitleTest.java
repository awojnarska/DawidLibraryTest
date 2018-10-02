package books;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;


@Feature("Find Book by title")
public class FindBookByTitleTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/books/";

    }

    @DataProvider(name = "properFragmentOfTitle")
    public Object[] properFragment() {
        return new Object[]{
                "Blood of Elves", "Call of Cthulu", "Pet Sematary", "of", "Horror"
        };
    }

    @DataProvider(name = "properFragmentCaseSensitive")
    public Object[] properFragmentCaseSensitive() {
        return new Object[]{
                "BLOOD", "CalL of cthulu", "PeT", "OF", "HorRor"
        };
    }

    @DataProvider(name = "incorrectFragmentOfTitle")
    public Object[] incorrectFragment() {
        return new Object[]{
                "12345", "jejejee", "abc", "fgh"
        };
    }


    @Step("fragment of title: [0]")
    @Test(dataProvider = "properFragmentOfTitle", description = "Check status code, when fragment of title exist")
    public void findBookByTitle_statusCodeTest(String find) {
        given().param("find", find).param("page", 0).param("limit", 10)
                .when().get()
                .then().statusCode(200);
    }


    @Step("fragment of title: [0]")
    @Test(dataProvider = "properFragmentCaseSensitive", description = "Check status code, when given fragment is case sensitive")
    public void findBookByTitle_properFragmentCaseSensitiveStatusCodeTest(String find) {
        given().param("find", find).param("page", 0).param("limit", 10)
                .when().get().then().statusCode(200);
    }


    @Step("fragment of title: [0]")
    @Test(dataProvider = "incorrectFragmentOfTitle", description = "Count elements, when given fragment is not on the list")
    public void findBookByTitle_incorrectFragmentCountElements(String find) {
        given().param("find", find).param("page", 0).param("limit", 10)
                .when().get()
                .then().contentType(ContentType.JSON).statusCode(200)
                .body("list.size()", is(0));
    }
}
