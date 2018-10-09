package orders;

import com.gd.intern.dawidlibrarytest.model.Order;
import com.gd.intern.dawidlibrarytest.model.OrderRest;
import com.gd.intern.dawidlibrarytest.model.UserRest;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static com.gd.intern.dawidlibrarytest.service.OrderService.*;
import static com.gd.intern.dawidlibrarytest.service.UserService.createUser;
import static io.restassured.RestAssured.given;

@Feature("Gift Book")
public class GiftBookTest {


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";

        //create users
        createUser("Test", "Order - Gift", "testordergift1@meil.com",
                "testordergift1", RATHER_NOT_SAY, "password", 99, 3000.00);
        createUser("Test", "Order - Gift", "testordergift2@meil.com",
                "testordergift2", RATHER_NOT_SAY, "password", 99, 25.00);

        //create orders
        createOrder("9781478965008", "testordergift1");
        createOrder("9781974267767", "testordergift1");
        createOrder("9781974267767", "testordergift2");

    }

    @DataProvider(name = "giftBookIncorrect")
    public Object[][] giftBookDataIncorrect() {
        return new Object[][]{
                {"9788375780741", "testordergift1", "testordergift2", 404}, //user who give don't have it
                {"9781974267767", "testordergift1", "testordergift2", 400}, //user to be gifted have book
                {"9781974267767", "testordergift2", "testordergift2", 400}, //give book to yourself
                {"9781974267761", "testordergift2", "testordergift1", 404}, //book don't exist
                {"978197426776111111", "testordergift2", "testordergift1", 400}, //ISBN too long
                {"9781974267767", "testorderg", "testordergift1", 404}, //username don't exist
        };
    }


    @Test(description = "Gift book test with correct data")
    public void testGiftBook_correctValues() {
        UserRest user1 = createUser("Test", "Order - Gift", "testordergift3@meil.com",
                "testordergift3", RATHER_NOT_SAY, "password", 99, 3000.00);
        UserRest user2 = createUser("Test", "Order - Gift", "testordergift4@meil.com",
                "testordergift4", RATHER_NOT_SAY, "password", 99, 3000.00);
        OrderRest order = createOrder("9781478965008", "testordergift3");
        OrderRest gift = giftBook(order.getBookRest().getIsbn(), user1.getUsername(), user2.getUsername(), new Order(order.getBookRest().getIsbn(), user2.getUsername()));
        orderAssertEquals(order, order.getBookRest(), user2);

    }

    @Test(dataProvider = "giftBookIncorrect", description = "Gift book test with incorrect values")
    public void testGiftBook_incorrectValues(String isbn, String user1, String user2, int status) {
        giftBook_incorrectValues(isbn, user1, user2, status);
    }

    @Test(description = "Gift book test , when username is not added in the body")
    public void testGiftBook_withoutUsername() {
        Map<String, String> gift = new HashMap<>();
        gift.put("isbn", "9781974267767");
        given().queryParam("to", "testordergift2").contentType("application/json").body(gift)
                .when().post()
                .then().statusCode(400);
    }

    @Test(description = "Gift book test, when isbn is not added in the body")
    public void testGiftBook_withoutISBN() {
        Map<String, String> gift = new HashMap<>();
        gift.put("username", "testordergift2");
        given().queryParam("to", "testordergift2").contentType("application/json").body(gift)
                .when().post()
                .then().statusCode(400);
    }

}
