package books;

import com.gd.intern.dawidlibrarytest.model.rest.BookRest;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static com.gd.intern.dawidlibrarytest.service.BookService.bookAssertEquals;
import static com.gd.intern.dawidlibrarytest.service.BookService.bookNotFound;
import static com.gd.intern.dawidlibrarytest.service.BookService.getBookByISBN;
import static com.gd.intern.dawidlibrarytest.util.JsonToJava.getListOfBookFromJson;

@Feature("Get book by isbn")
public class GetBookByISBNTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/virtual-library-ws/";

    }

    @DataProvider(name = "TitleByISBN")
    public Object[] dataTitleByISBN() throws IOException {
        List<BookRest> books = getListOfBookFromJson("books.json");
        return books.toArray(new BookRest[books.size()]);
    }

    @DataProvider(name = "WrongParameters")
    public Object[] dataWrongParam() {
        return new Object[]{"12345", "123", "abcdergh"};
    }

    @Test(dataProvider = "TitleByISBN", description = "Check status code and the correctness of data")
    public void testGetBookByISBN(BookRest book) {
        BookRest newBook = getBookByISBN(book.getIsbn());
        bookAssertEquals(newBook, book.getTitle(), book.getIsbn(), book.getPages(), book.getPrice(), book.getPublicationYear(), book.getAuthor());
    }

    @Test(dataProvider = "WrongParameters", description = "Check status code, when isbn not exist")
    public void testGetBookByISBN_bookNotFound(String isbn) {
        bookNotFound(isbn);
    }


}
