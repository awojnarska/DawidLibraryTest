package users;

import com.gd.intern.dawidlibrarytest.model.User;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateUser {

/*    @DataProvider(name = "User")
    public Object[] user() {
        return new Object[]{new User("Anna", "Mikolajczyk", "amikol@op.pl", "miki", "FEMALE", "password", 89, 1200.0),
                new User("Anna", "", "kol@op.pl", "niki", "FEMALE", "password", 24, 9010.0)
        };
    }*/
@DataProvider(name = "UserExist")
public Object[] user() {
    return new Object[]{new User("Anna", "Mikolajczyk", "amikol@op.pl", "miki", "FEMALE", "password", 89, 1200.0),
            new User("Anna", "", "kol@op.pl", "niki", "FEMALE", "password", 24, 9010.0),
            new User("", "", "kola@op.pl", "niki", "FEMALE", "password", 89, 2000.0),
            new User("", "", "", "", "", "", 0, 0),
            new User(),
            new User("maila@op.pl", "anna", "KOL", "password")
    };
}


/*    @Test(dataProvider = "User")
    public void postOrder_orderExist(User user) {
        given().contentType("application/json").body(user).when().post("http://localhost:8080/virtual-library-ws/users")
                .then().statusCode(200)
        .body("accountBalance", equalTo(user.getAccountBalance()),
                "age", equalTo(user.getAge()),
                "email", equalTo(user.getEmail()),
                "firstName", equalTo(user.getFirstName()),
                "gender", equalTo(user.getGender()),
                "lastName", equalTo(user.getLastName()),
                "username", equalTo(user.getUsername()));
    }*/

    @Test(dataProvider = "UserExist")
    public void createUser_userExist(User user) {
        given().contentType("application/json").body(user).when().post("http://localhost:8080/virtual-library-ws/users")
                .then().statusCode(400);
    }

}
