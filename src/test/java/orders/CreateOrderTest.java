package orders;

import com.gd.intern.dawidlibrarytest.model.rest.BookRest;
import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.model.rest.OrderRest;
import com.gd.intern.dawidlibrarytest.model.rest.UserRest;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static com.gd.intern.dawidlibrarytest.service.BookService.getBookByISBN;
import static com.gd.intern.dawidlibrarytest.service.OrderService.createOrder;
import static com.gd.intern.dawidlibrarytest.service.OrderService.orderAssertEquals;
import static com.gd.intern.dawidlibrarytest.service.UserService.createUser;
import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;

@Feature("Create Order")
public class CreateOrderTest {

    private String bookIsbn = "9781478965008";
    private String username1 = "testorder1";
    private String email1 = "testorder1@mail.com";
    private String username2 = "testorder2";
    private String email2 = "testorder2@mail.com";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE));

        createUser("Test", "Order", email1,
                username1, RATHER_NOT_SAY, "password", 20, 10.00);

        createUser("Test", "Order", email2,
                username2, RATHER_NOT_SAY, "password", 20, 10000.00);
    }


    @DataProvider(name = "postOrderIncorrect")
    public Object[][] dataCreateOrder() {
        return new Object[][]{
                {"9781974267767", "dgabka", 400}, //existOrder
                {"9788375780741", "ilya", 400}, //existOrder
                {bookIsbn, username1, 400}, //notEnoughMoney
                {bookIsbn, "testorderr", 404}, //wrong username
                {"9781478965025", username2, 404}, //wrong isbn
                {"9781478965008", "testorderr", 404}, //wrong isbn & username
        };
    }


    @Test(dataProvider = "postOrderIncorrect", description = "Check status code, when some parameters are wrong")
    public void testCreateOrder_incorrectData(String isbn, String username, int status) {
        given().contentType("application/json").body(new Order(isbn, username))
                .when().post("orders")
                .then().statusCode(status);
    }

    @Test(description = "Check status code with proper parameters")
    public void testCreateOrder_CorrectValues() {
        UserRest user = createUser("Test", "Order", "testorder3@meil.com",
                "testorder3", RATHER_NOT_SAY, "password", 99, 3000.00);
        BookRest book = getBookByISBN("9781974267767");
        OrderRest order = createOrder(book.getIsbn(), user.getUsername());
        user.setAccountBalance(user.getAccountBalance() - book.getPrice());
        orderAssertEquals(order, book, user);
    }


}
