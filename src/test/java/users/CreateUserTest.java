package users;

import com.gd.intern.dawidlibrarytest.model.Gender;
import com.gd.intern.dawidlibrarytest.model.rest.UserRest;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.gd.intern.dawidlibrarytest.model.Gender.FEMALE;
import static com.gd.intern.dawidlibrarytest.service.UserService.*;
import static com.gd.intern.dawidlibrarytest.util.ConfigurationRestAssured.baseUri;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.DOUBLE;

@Feature("Create User")
public class CreateUserTest {

    @BeforeClass
    public void setup() {
        baseUri();
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(DOUBLE));
    }

    @DataProvider(name = "userData")
    public Object[][] dataUser() {
        return new Object[][]{
                {"Test", "User", "usertest@mail.com", "usertest", FEMALE, "password", 10, 100.0},
        };
    }

    @DataProvider(name = "userBasicData")
    public Object[][] dataUserBasic() {
        return new Object[][]{
                {"usertest2@mail.com", "testuser", "password"}
        };
    }


    @DataProvider(name = "wrongBasicUserData")
    public Object[][] dataUserBasic_wrongParameters() {
        return new Object[][]{
                {"usertest@mail.com", "usertest2", "password"}, //email exist
                {"testwrongdata2@mail.com", "usertest", "password"}, //username exist
                {"testwrongdata3@mail.com", "us", "password"}, //username short
                {"testwrongdata4@mail.com", "ilaaawwertyuwertyuiolkjhgfxcvbnmnxncjsbvhjsdbjhbdsjbsdvhjbfvsdb", "password"}, //username long
                {"testwrongdata4", "testmail", "password"}, //wrong mail
                {"testwrongdata4.com", "testmail2", "password"}, //wrong mail
                {"testwrongdata4adsdfqwertyuiopsdygcbjdvbhjsbvhjdbsvbsahjvbjhasbvjhasbjvhabajvbsajhbsvajhasv@mail.com", "testmail3", "password"}, //wrong mail
                {"testwrongdata5@mail.com", "testmail5", "pas"}, //wrong password
                {"testwrongdata5@mail.com", "testmail5", "pas pkjkj"} //wrong password
        };
    }

    @DataProvider(name = "wrongUserData")
    public Object[][] dataUser_wrongUserDetails() {
        return new Object[][]{
                {"Testabahbabnabnabdbanmbfjhbfahjhabgjhbagjhbgjhgbjhagbhj", "User", "wronguserdatatest@mail.com", "usertestwrongdata", FEMALE, "password", 10, 100.0}, //firstName too long
                {"User", "Testabahbabnabnabdbanmbfjhbfahjhabgjhbagjhbgjhgbjhagbhj", "wronguserdatatest2@mail.com", "usertestwrongdata2", FEMALE, "password", 10, 100.0}, //lastName too long
                {"User", "Test", "wronguserdatatest4@mail.com", "usertestwrongdata3", FEMALE, "password", -10, 100.0}, //wrong age
                {"User", "Test", "wronguserdatatest5@mail.com", "usertestwrongdata4", FEMALE, "password", 10, -100.0} //wrong accountBalance
        };
    }

    @DataProvider(name = "oneParameterData")
    public Object[][] dataOneParameterUserDetails() {
        return new Object[][]{
                {"username", "usernameTest"},
                {"email", "usernametest@mail.com"}
        };
    }


    @Test(dataProvider = "userData", description = "Create user with correct data")
    public void testCreateUser(String firstName, String lastName, String email, String username, Gender gender, String password, int age, double accountBalance) {

        UserRest user = createUser(firstName, lastName, email, username, gender, password, age, accountBalance);
        userAssertEquals(user, firstName, lastName, email, username, gender, age, accountBalance);

    }

    @Test(dataProvider = "userBasicData", description = "Create user with correct basic data")
    public void testCreateUser_basicData(String email, String username, String password) {
        UserRest user = createUserBasicData(email, username, password);
        userAssertEquals(user, email, username);
    }


    @Test(dataProvider = "wrongBasicUserData", description = "Create user with incorrect basic data")
    public void testCreateUser_wrongBasicUserData(String email, String username, String password) {
        createUser_incorrectBasicData(email, username, password);
    }

    @Test(dataProvider = "wrongUserData", description = "Create user with incorrect data")
    public void testCreateUser_wrongUserData(String firstName, String lastName, String email, String username, Gender gender, String password, int age, double accountBalance) {
        createUser_incorrectData(firstName, lastName, email, username, gender, password, age, accountBalance);
    }

    @Test(dataProvider = "oneParameterData", description = "Create user using only one parameter")
    public void testCreateUser_oneParameter(String parameterName, Object parameterValue) {
        createUser_oneParameter(parameterName, parameterValue);
    }


}
