package users;

import com.gd.intern.dawidlibrarytest.model.rest.UserRest;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.gd.intern.dawidlibrarytest.model.Gender.RATHER_NOT_SAY;
import static com.gd.intern.dawidlibrarytest.service.UserService.*;
import static io.restassured.RestAssured.given;

@Feature("Update User Details")
public class UpdateUserDetailsTest {

    @Step("Determine baseURI")
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";
    }


    @Test(description = "Update user test with proper values")
    public void testUpdateUser() {
        //create user
        UserRest user = createUser("Test", "Update", "testupdate1@mail.com",
                "testupdate1", RATHER_NOT_SAY, "password", 99, 3000.00);


        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("age", 30);
        userUpdate.put("firstName", "TestName");
        userUpdate.put("lastName", "TestLastName");
        userUpdate.put("email", "newtestupdate1@mail.com");

        UserRest newUser = updateUser(userUpdate, user.getPublicUserId());
        userAssertEquals(newUser, (String) userUpdate.get("firstName"), (String) userUpdate.get("lastName"), (String) userUpdate.get("email"), user.getUsername(), user.getGender(), (int) userUpdate.get("age"), user.getAccountBalance());

    }


    @Test(description = "Update user test with proper values - last name")
    public void testUpdateUser_updateLastName() {

        //create user
        UserRest user = createUser("Test", "Update", "testupdate2@mail.com",
                "testupdate2", RATHER_NOT_SAY, "password", 99, 3000.00);

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("lastName", "newlastname");

        //update user
        UserRest newUser = updateUser(userUpdate, user.getPublicUserId());
        userAssertEquals(newUser, user.getFirstName(), (String) userUpdate.get("lastName"), user.getEmail(), user.getUsername(), user.getGender(), user.getAge(), user.getAccountBalance());
    }

    @DataProvider(name = "updateOneParameter")
    public Object[][] dataUpdateOneParameter() {


        return new Object[][]{
                //update email exist
                {createUser("Test", "Update", "testupdate4@mail.com",
                        "testupdate4", RATHER_NOT_SAY, "password", 99, 3000.00),
                        "email",
                        "ilya@email.com"},
                //wrong age
                {createUser("Test", "Update", "testupdate5@mail.com",
                        "testupdate5", RATHER_NOT_SAY, "password", 99, 3000.00),
                        "age", -99}
        };
    }

    @Test(dataProvider = "updateOneParameter", description = "Update user test - update one value")
    public void testUpdateUser_updateOneParameter_incorrectValue(UserRest user, String key, Object value) {
        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put(key, value);

        //update user
        updateUser_incorrectValue(userUpdate, user.getPublicUserId(), 400);
    }


    @DataProvider(name = "wrongUserId")
    public Object[] dataWrongUserId() {
        return new Object[]{
                "abdefgh", "1223123456789098765434567890987654"
        };
    }


    @Test(dataProvider = "wrongUserId", description = "Update user test - wrong id")
    public void testUpdateUser_wrongId(String id) {

        //update values
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("username", "newtestupdate3");

        updateUser_incorrectValue(userUpdate, id, 404);

    }


}
