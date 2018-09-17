package books;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FindBookByTitle {

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


    @Test(dataProvider = "properFragmentOfTitle")
    public void findBookByTitle_statusCodeTest(String find) {
        given().param("find", find).param("page", 0).param("limit", 10).when().get("http://localhost:8080/virtual-library-ws/books/").then().statusCode(200);
    }

    @Test(dataProvider = "properFragmentCaseSensitive")
    public void findBookByTitle_properFragmentCaseSensitiveStatusCodeTest(String find) {
        given().param("find", find).param("page", 0).param("limit", 10).when().get("http://localhost:8080/virtual-library-ws/books/").then().statusCode(200);
    }

    @Test(dataProvider = "incorrectFragmentOfTitle")
    public void findBookByTitle_incorrectParamStatusCodeTest(String find) {
        given().param("find", find).param("page", 0).param("limit", 10).when().get("http://localhost:8080/virtual-library-ws/books/").then().statusCode(404);
    }
}
