package orders;

import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.model.OrderRest;

import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static com.gd.intern.dawidlibrarytest.service.OrderService.createOrder;
import static com.gd.intern.dawidlibrarytest.service.OrderService.saveReadingProgress;
import static com.gd.intern.dawidlibrarytest.service.OrderService.saveReadingProgress_incorrectData;
import static com.gd.intern.dawidlibrarytest.service.UserService.createUser;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.closeTo;

import static org.testng.Assert.assertEquals;

@Feature("Save reading progress")
public class SaveReadingProgressTest {

    private String bookIsbn = "9781478965008";
    private String username = "testorderread1";
    private String email = "testorderread1@mail.com";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE));

        //create users
        createUser("Test", "Order - Read", email, username, RATHER_NOT_SAY, "password", 20, 3000.00);

        //create orders
        createOrder(bookIsbn, username);
    }

    @DataProvider(name = "properData")
    public Object[][] dataSaveReadingData() {
        return new Object[][]{
                {bookIsbn, username, 0},
                {bookIsbn, username, 50},
                {bookIsbn, username, 100},
                {bookIsbn, username, 464}
        };
    }

    @DataProvider(name = "incorrectData")
    public Object[][] dataSaveReadingData_incorrectPages() {
        return new Object[][]{
                {bookIsbn, username, -1, 400}, //too small number of pages
                {bookIsbn, username, -100, 400}, //too small number of  pages
                {bookIsbn, username, 900, 400}, //too big number of pages
                {bookIsbn, username, 465, 400}, //too big number of  pages
                {bookIsbn, "testorderread", 100, 404}, //username don't exist
                {"9781478965025", username, 100, 404}, //wrong ISBN
                {"9781478965008", "testorderread", 100, 404}, //wrong isbn & username
        };
    }


    @Test(dataProvider = "properData", description = "Save reading progress test  with correct data")
    public void testSaveReadingProgress_correctPageNumber(String isbn, String username, int pages) {
        OrderRest order = saveReadingProgress(isbn, username, pages);
        assertEquals(order.getReadingProgress(), closeTo((((double) pages / order.getBookRest().getPages()) * 100), 0.01d));
    }

    @Test(dataProvider = "incorrectData", description = "Save reading progress with incorrect data")
    public void testSaveReadingProgress_incorrectData(String isbn, String username, int pages, int status) {
        saveReadingProgress_incorrectData(isbn, username, pages, status);
    }


}
