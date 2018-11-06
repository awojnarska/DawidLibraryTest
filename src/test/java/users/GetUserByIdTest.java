package users;

import com.gd.intern.dawidlibrarytest.model.Gender;
import com.gd.intern.dawidlibrarytest.model.rest.UserRest;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static com.gd.intern.dawidlibrarytest.service.UserService.*;
import static com.gd.intern.dawidlibrarytest.util.ConfigurationRestAssured.baseUri;
import static org.testng.Assert.assertEquals;

@Feature("Get user by id")
public class GetUserByIdTest {

    @Step("Determine baseURI")
    @BeforeClass
    public void setup() {
        baseUri();
    }

    @DataProvider(name = "publicUserId")
    public Object[][] dataPublicUserId() {
        return new Object[][]{
                {"8CnHLxNh06ZfmtfPBoV1c6slRU0Dk3", "ilya"},
                {"MmVhfTO044WdTacJWXbqWkHbuuwJxT", "dgabka"},
        };
    }

    @DataProvider(name = "incorrectPublicUserId")
    public Object[] dataIncorrectPublicUserId() {
        return new Object[]{"8CnHLxNh06ZfmtfPBoV1cf6slahnjkl", "aaabavahbahjkjabjhadhjkashkasjh", "ghjhbacjhbcjsahcjajhcbacjsh"};
    }

    @DataProvider(name = "userData")
    public Object[][] dataUser() {
        return new Object[][]{
                {"Test", "GetUser", "getuserbyid3@mail.com",
                        "getuserbyid3", RATHER_NOT_SAY, "password", 100, 3000.00},
        };
    }


    @Test(dataProvider = "publicUserId", description = "Getting user by id test - user from database")
    public void testGetUserById_userFromDatabase(String id, String login) {
        UserRest userById = getUserById(id);
        assertEquals(userById.getUsername(), login);
    }

    @Test(dataProvider = "userData", description = "Getting user by id test - first create new user")
    public void testGetUserById_newUser(String firstName, String lastName, String email, String username, Gender gender, String password, int age, double accountBalance) {

        UserRest user = createUser(firstName, lastName, email, username, gender, password, age, accountBalance);
        UserRest userById = getUserById(user.getPublicUserId());
        userAssertEquals(userById, firstName, lastName, email, username, gender, age, accountBalance);

    }


    @Test(dataProvider = "incorrectPublicUserId", description = "Getting user by id test - incorrect public user id")
    public void testGetUserById_incorrectPublicUserId(String id) {
        getUserById_userNotFound(id);
    }


}


