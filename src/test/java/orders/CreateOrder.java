package orders;

import com.gd.intern.dawidlibrarytest.model.Order;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateOrder {

    @DataProvider(name = "Order")
    public Object[] order() {
        return new Object[]{new Order("9781974267767", "dgabka"), new Order("9788375780741", "ilya")
        };
    }

    @Test(dataProvider = "Order")
    public void postOrder_orderExist(Order order) {
        given().contentType("application/json").body(order).when().post("http://localhost:8080/virtual-library-ws/orders")
                .then().statusCode(400);
    }
}
