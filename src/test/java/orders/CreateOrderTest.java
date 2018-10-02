package orders;

import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.util.CreateUserDB;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

@Feature("Create Order")
public class CreateOrderTest {


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/orders";
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE));

        //create users
        CreateUserDB.createUser("Test", "Order", "testorder1@meil.com",
                "testorder1", "RATHER_NOT_SAY", "password", 99, 3000.00);
        CreateUserDB.createUser("Test", "Order", "testorder2@meil.com",
                "testorder2", "RATHER_NOT_SAY", "password", 20, 10.00);
    }


    @DataProvider(name = "postOrderIncorrect")
    public Object[][] createOrderData() {
        return new Object[][]{
                {"9781974267767", "dgabka", 400}, //existOrder
                {"9788375780741", "ilya", 400}, //existOrder
                {"9781974267767", "testorder2", 400}, //notEnoughMoney
                {"9781478965008", "testorderr", 404}, //wrong username
                {"9781478965025", "testorder1", 404}, //wrong isbn
                {"9781478965008", "testorderr", 404}, //wrong isbn & username
        };
    }

    @DataProvider(name = "postOrderCorrect")
    public Object[][] createOrderDataCorrect() {
        return new Object[][]{
                {"9781974267767", "testorder1", 16.99, 200}, //correctOrder
        };
    }

    @Step("isbn: [0], username: [1], status: [2]")
    @Test(dataProvider = "postOrderIncorrect", description = "Check status code, when some parameters are wrong")
    public void postOrder_statusCodeIncorrect(String isbn, String username, int status) {
        given().contentType("application/json").body(new Order(isbn, username))
                .when().post()
                .then().statusCode(status);
    }

    @Step("isbn: [0], username: [1], status: [2]")
    @Test(dataProvider = "postOrderCorrect", description = "Check status code with proper parameters")
    public void postOrder_CorrectValues(String isbn, String username, double price, int status) {
        given().contentType("application/json").body(new Order(isbn, username))
                .when().post()
                .then().statusCode(status)
                .body("bookRest.isbn", equalTo(isbn),
                        "userRest.username", equalTo(username),
                        "userRest.accountBalance", closeTo(3000 - price, 0.01));
    }


}
