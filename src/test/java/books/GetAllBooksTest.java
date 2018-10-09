package books;

import com.gd.intern.dawidlibrarytest.model.BookRest;
import com.gd.intern.dawidlibrarytest.util.JsonToJava;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.gd.intern.dawidlibrarytest.service.BookService.findAllBooks;
import static com.gd.intern.dawidlibrarytest.util.CountElementLimit.countElementsOnPage;
import static org.testng.Assert.assertTrue;

@Feature("Get all books")
public class GetAllBooksTest {


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";
    }

    @DataProvider(name = "pageAndLimit")
    public Object[] pageAndNumber() {
        return new Object[][]{
                {0, 8}, {0, 5}, {0, 2}, {1, 8}, {10, 3}, {2, 3}, {4, 3}
        };
    }

    @Test(description = "Find all book test")
    public void testGetListOfAllBooks() throws IOException {
        List<BookRest> books = findAllBooks(0, 1000);
        assertTrue(books.retainAll(JsonToJava.getListOfBookFromJson("books.json")));
    }

    @Test(dataProvider = "pageAndLimit", description = "Find all book test - count elements")
    public void testGetListOfAllBooks_countElementsOfList(int page, int limit) throws IOException {
        List<BookRest> books = findAllBooks(page, limit);
        assertTrue(books.size() == countElementsOnPage(page, limit, JsonToJava.getListOfBookFromJson("books.json").size()));
    }
}
