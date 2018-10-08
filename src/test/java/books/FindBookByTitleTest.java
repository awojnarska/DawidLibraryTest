package books;

import com.gd.intern.dawidlibrarytest.model.Book;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static com.gd.intern.dawidlibrarytest.service.BookService.findBookByFragmentOfTitle;
import static com.gd.intern.dawidlibrarytest.service.BookService.titleAssertEquals;
import static org.testng.Assert.assertTrue;


@Feature("Find Book by title")
public class FindBookByTitleTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";

    }

    @DataProvider(name = "properFragmentOfTitle")
    public Object[] dataFindBookByTitle_properFragment() {
        return new Object[]{
                "Blood of Elves", "Call of CTHULu", "Pet Sematary", "of", "HorRor",
        };
    }


    @DataProvider(name = "incorrectFragmentOfTitle")
    public Object[] dataFindBookByTitle_incorrectFragment() {
        return new Object[]{
                "12345", "jejejee", "abc", "fgh"
        };
    }


    @Test(dataProvider = "properFragmentOfTitle", description = "Check status code, when fragment of title exist")
    public void testFindBookByTitle(String find) {
        List<Book> books = findBookByFragmentOfTitle(find);
        titleAssertEquals(books, find);
    }



    @Test(dataProvider = "incorrectFragmentOfTitle", description = "Count elements, when given fragment is not on the list")
    public void testFindBookByTitle_fragmentNotExist(String find) {
        List<Book> books = findBookByFragmentOfTitle(find);
        assertTrue(books.size()==0);
    }
}
