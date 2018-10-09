package orders;

import com.gd.intern.dawidlibrarytest.model.BookRest;
import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.model.OrderRest;
import com.gd.intern.dawidlibrarytest.model.UserRest;
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

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE));

        createUser("Test", "Order", "testorder2171321551@meil.com",
                "testorder2511315127", RATHER_NOT_SAY, "password", 20, 10.00);
    }


    @DataProvider(name = "postOrderIncorrect")
    public Object[][] createOrderData() {
        return new Object[][]{
                {"9781974267767", "dgabka", 400}, //existOrder
                {"9788375780741", "ilya", 400}, //existOrder
                {"9781974267767", "testorder2", 400}, //notEnoughMoney
                {"9781478965008", "testorderr", 404}, //wrong username
                {"9781478965025", "testorder2", 404}, //wrong isbn
                {"9781478965008", "testorderr", 404}, //wrong isbn & username
        };
    }


    @Test(dataProvider = "postOrderIncorrect", description = "Check status code, when some parameters are wrong")
    public void testCreateOrder_incorrectData(String isbn, String username, int status) {
        given().contentType("application/json").body(new Order(isbn, username))
                .when().post()
                .then().statusCode(status);
    }

    @Test(description = "Check status code with proper parameters")
    public void testCreateOrder_CorrectValues() {
        UserRest user = createUser("Test", "Order", "testorder311317521@meil.com",
                "testorder11113211553", RATHER_NOT_SAY, "password", 99, 3000.00);
        BookRest book = getBookByISBN("9781974267767");
        OrderRest order = createOrder(book.getIsbn(), user.getUsername());
        user.setAccountBalance(user.getAccountBalance() - book.getPrice());
        orderAssertEquals(order, book, user);
    }


}
