package orders;

import com.gd.intern.dawidlibrarytest.model.rest.OrderRest;
import com.gd.intern.dawidlibrarytest.model.rest.UserRest;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static com.gd.intern.dawidlibrarytest.service.OrderService.*;
import static com.gd.intern.dawidlibrarytest.service.UserService.createUser;
import static com.gd.intern.dawidlibrarytest.util.ConfigurationRestAssured.baseUri;
import static io.restassured.RestAssured.given;

@Feature("Gift Book")
public class GiftBookTest {

    private String bookIsbn = "9781478965008";
    private String bookIsbn2 = "9781974267767";
    private String username = "testordergift1";
    private String username2 = "testordergift2";
    private String email = "testordergift1@meil.com";
    private String email2 = "testordergift2@meil.com";


    @BeforeClass
    public void setup() {
        baseUri();
        //create users
        createUser("Test", "Order - Gift", email,
                username, RATHER_NOT_SAY, "password", 99, 3000.00);
        createUser("Test", "Order - Gift", email2,
                username2, RATHER_NOT_SAY, "password", 99, 25.00);

        //create orders
        createOrder(bookIsbn, username);
        createOrder(bookIsbn2, username);
        createOrder(bookIsbn2, username2);

    }

    @DataProvider(name = "giftBookIncorrect")
    public Object[][] dataGiftBookIncorrect() {
        return new Object[][]{
                {"9788375780741", username, username2, 404}, //user who give don't have it
                {bookIsbn2, username, username2, 400}, //user to be gifted have book
                {bookIsbn2, username2, username2, 400}, //give book to yourself
                {"9781974267761", username2, username, 404}, //book don't exist
                {"978197426776111111", username2, username, 400}, //ISBN too long
                {bookIsbn2, "testorderg", username, 404}, //username don't exist
        };
    }


    @Test(description = "Gift book test with correct data")
    public void testGiftBook_correctValues() {
        UserRest user1 = createUser("Test", "Order - Gift", "testordergift3@meil.com",
                "testordergift3", RATHER_NOT_SAY, "password", 99, 3000.00);
        UserRest user2 = createUser("Test", "Order - Gift", "testordergift4@meil.com",
                "testordergift4", RATHER_NOT_SAY, "password", 99, 3000.00);
        OrderRest order = createOrder("9781478965008", user1.getUsername());
        OrderRest gift = giftBook(order.getBookRest().getIsbn(), user1.getUsername(), user2.getUsername());
        orderAssertEquals(gift, gift.getBookRest(), user2);

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
                .when().post("orders/gift")
                .then().statusCode(400);
    }

    @Test(description = "Gift book test, when isbn is not added in the body")
    public void testGiftBook_withoutISBN() {
        Map<String, String> gift = new HashMap<>();
        gift.put("username", "testordergift2");
        given().queryParam("to", "testordergift2").contentType("application/json").body(gift)
                .when().post("orders/gift")
                .then().statusCode(400);
    }

}
