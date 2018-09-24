package users;

import com.gd.intern.dawidlibrarytest.model.User;
import com.gd.intern.dawidlibrarytest.model.UserBasic;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CreateUserTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/users";
    }

    @DataProvider(name = "userData")
    public Object[] userData() {
        return new Object[]{
                new User("Test", "User", "usertest1@mail.com", "usertest", "FEMALE", "password", 10, 100.0),
        };
    }

    @DataProvider(name = "userBasicData")
    public Object[] userBasicData() {
        return new Object[]{
                new UserBasic("usertest2@mail.com", "testuser", "password")
        };
    }


    @DataProvider(name = "wrongBasicUserData")
    public Object[] userBasicData_wrongParameters() {
        return new Object[]{
                new UserBasic("dgbaka@email.com", "gabka", "password"), //email exist
                new UserBasic("testwrongdata2@mail.com", "ilya", "password"), //username exist
                new UserBasic("testwrongdata3@mail.com", "il", "password"), //username short
                new UserBasic("testwrongdata4@mail.com", "ilaaawwertyuwertyuiolkjhgfxcvbnmnxncjsbvhjsdbjhbdsjbsdvhjbfvsdb", "password"), //username long
                new UserBasic("testwrongdata4", "testmail", "password"), //wrong mail
                new UserBasic("testwrongdata4.com", "testmail2", "password"), //wrong mail
                new UserBasic("testwrongdata4adsdfqwertyuiopsdygcbjdvbhjsbvhjdbsvbsahjvbjhasbvjhasbjvhabajvbsajhbsvajhasv@mail.com", "testmail3", "password"), //wrong mail
                new UserBasic("testwrongdata5@mail.com", "testmail5", "pas"), //wrong password
                new UserBasic("testwrongdata5@mail.com", "testmail5", "pasqwertyuiopzcvhjbsjhbshbbjabjhasjhasjhcbsajhcbasjhacsbhjasbsacjhbscjasbjascbcjashbacsjbcsavsjhbvsjhsbhsabvbssvhabsvakhbahjksbfsakhbfskjbfskjfs"), //wrong password
        };
    }

    @DataProvider(name = "wrongUserData")
    public Object[] wrongUserData() {
        return new Object[]{
                new User("Testabahbabnabnabdbanmbfjhbfahjhabgjhbagjhbgjhgbjhagbhj", "User", "wronguserdatatest@mail.com", "usertestwrongdata", "FEMALE", "password", 10, 100.0), //firstName too long
                new User("User", "Testabahbabnabnabdbanmbfjhbfahjhabgjhbagjhbgjhgbjhagbhj", "wronguserdatatest2@mail.com", "usertestwrongdata2", "FEMALE", "password", 10, 100.0), //lastName too long
                new User("User", "Test", "wronguserdatatest3@mail.com", "usertestwrongdata3", "FEMALEE", "password", 10, 100.0), //wrong gender
                new User("User", "Test", "wronguserdatatest4@mail.com", "usertestwrongdata4", "FEMALE", "password", -10, 100.0), //wrong age
                new User("User", "Test", "wronguserdatatest5@mail.com", "usertestwrongdata5", "FEMALE", "password", 10, -100.0), //wrong accountBalance
        };
    }


    @Test(dataProvider = "userData")
    public void createUser_test(User user) {
        given().contentType("application/json").body(user).when().post()
                .then().statusCode(200)
                .body("accountBalance", equalTo((float) user.getAccountBalance()),
                        "age", equalTo(user.getAge()),
                        "email", equalTo(user.getEmail()),
                        "firstName", equalTo(user.getFirstName()),
                        "gender", equalTo(user.getGender()),
                        "lastName", equalTo(user.getLastName()),
                        "username", equalTo(user.getUsername()));
    }

    @Test(dataProvider = "userBasicData")
    public void createUser_test(UserBasic user) {
        given().contentType("application/json").body(user).when().post()
                .then().statusCode(200)
                .body("accountBalance", equalTo(0.0f),
                        "age", equalTo(0),
                        "email", equalTo(user.getEmail()),
                        "firstName", equalTo(null),
                        "gender", equalTo("RATHER_NOT_SAY"),
                        "lastName", equalTo(null),
                        "username", equalTo(user.getUsername()));
    }


    @Test(dataProvider = "wrongBasicUserData")
    public void createUser_wrongBasicUserData(UserBasic user) {
        given().contentType("application/json").body(user).when().post()
                .then().statusCode(400);
    }

    @Test(dataProvider = "wrongUserData")
    public void createUser_wrongUserData(User user) {
        given().contentType("application/json").body(user).when().post()
                .then().statusCode(400);
    }

    @Test
    public void createUser_oneParameterUsername() {
        Map<String, Object> createUser = new HashMap<>();
        createUser.put("username", "usernameTest");
        given().contentType("application/json").body(createUser).when().post()
                .then().statusCode(400);

    }

    @Test
    public void createUser_oneParameterEmail() {
        Map<String, Object> createUser = new HashMap<>();
        createUser.put("email", "usernametest@mail.com");
        given().contentType("application/json").body(createUser).when().post()
                .then().statusCode(400);

    }


}
