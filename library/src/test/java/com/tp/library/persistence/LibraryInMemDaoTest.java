package com.tp.library.persistence;

import com.tp.library.exceptions.*;
import com.tp.library.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryInMemDaoTest {

    @Autowired
    LibraryInMemDao toTest;

    @BeforeEach
    public void setup() throws InvalidIdException, InvalidAuthorsException, InvalidTitleException, InvalidYearException {
        List<Book> allBooks = toTest.getAllBooks();

        for (Book toRemove : allBooks) {
            toTest.deleteBook(toRemove.getId());
        }

        Book firstBook = new Book("Ender's Game", Arrays.asList("Orson Scott Card"), 1985);
        toTest.addBook(firstBook);
    }

    @Test
    public void testAddBookGoldenPath() {
        try {
            Book testBook = new Book("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002);
            int id = toTest.addBook(testBook);

            assertEquals(2, id);

            Book validation = toTest.getBookById(2);
            assertEquals("The Bearenstein Bears", validation.getTitle());

            List<String> validationAuthors = validation.getAuthors();
            assertEquals(2, validationAuthors.size());
            assertEquals("Stan Bearenstein", validationAuthors.get(0));
            assertEquals("Jan Bearenstein", validationAuthors.get(1));

            assertEquals(2002, validation.getYear());

            assertEquals(2, validation.getId());

            Book testBook2 = new Book ("IT", Arrays.asList("Stephen King", "King Stephen"), 1986);
            int nextId = toTest.addBook(testBook2);

            assertEquals(3, nextId);

            Book validation2 = toTest.getBookById(3);
            assertEquals("IT", validation2.getTitle());

            List<String> validationAuthors2 = validation2.getAuthors();
            assertEquals(2, validationAuthors2.size());
            assertEquals("Stephen King", validationAuthors2.get(0));
            assertEquals("King Stephen", validationAuthors2.get(1));

            assertEquals(1986, validation2.getYear());

            assertEquals(3, validation2.getId());
        } catch (InvalidAuthorsException | InvalidTitleException | InvalidYearException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testAddBookNullTitle() {
        assertThrows(InvalidTitleException.class, () -> toTest.addBook(new Book (null, Arrays.asList("Test"), 2000)));
    }

    // authors = null
    @Test
    public void testAddBookNullAuthors() {
        assertThrows(InvalidAuthorsException.class, () -> toTest.addBook(new Book ("Test", null, 2000)));
    }

    // authors = [name1, null, name2]
    @Test
    public void testAddBookAuthorListWithNullAuthors() {
        assertThrows(InvalidAuthorsException.class, () -> toTest.addBook(new Book ("Test", Arrays.asList("Test", null), 2000)));
    }

    @Test
    public void testAddBookNullYear() {
        assertThrows(InvalidYearException.class, () -> toTest.addBook(new Book ("Test", Arrays.asList("Test"), null)));
    }

    @Test
    public void testGetAllBooksGoldenPath() {
        List<Book> copy = toTest.getAllBooks();

        assertEquals(1, copy.size());
        assertEquals("Ender's Game", copy.get(0).getTitle());
        assertEquals(1, copy.get(0).getAuthors().size());
        assertEquals("Orson Scott Card", copy.get(0).getAuthors().get(0));
        assertEquals(1985, copy.get(0).getYear());
        assertEquals(1, copy.get(0).getId());
    }

    @Test
    public void testGetBookByIdGoldenPath() {
        try {
            Book book = toTest.getBookById(1);

            assertEquals("Ender's Game", book.getTitle());
            assertEquals(1, book.getAuthors().size());
            assertEquals("Orson Scott Card", book.getAuthors().get(0));
            assertEquals(1985, book.getYear());
            assertEquals(1, book.getId());
        } catch (InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testGetBookByIdNullId() {
        assertThrows(InvalidIdException.class, () -> toTest.getBookById(null));
    }

    @Test
    public void testGetBookByIdInvalidId() {
        try {
            Book book = toTest.getBookById(-1);

            assertEquals(null, book);
        } catch (InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteBookGoldenPath() {
        try {
            assertNotEquals(null, toTest.getBookById(1));
            toTest.deleteBook(1);
            assertEquals(null, toTest.getBookById(1));
        } catch (InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteBookNullId() {
        assertThrows(InvalidIdException.class, () -> toTest.deleteBook(null));
    }

    @Test
    public void testDeleteBookInvalidId() {
        assertThrows(InvalidIdException.class, () -> toTest.deleteBook(-1));
    }

    @Test
    public void testGetBooksByTitleGoldenPath() {
        try {
            toTest.addBook(new Book("The End of History and the Last Man", Arrays.asList("Francis Fukuyama"), 1992));
            toTest.addBook(new Book("TEST", Arrays.asList("TEST"), 1992));

            List<Book> endBooks = toTest.getBooksByTitle("end");
            assertEquals(2, endBooks.size());

            Book validation = endBooks.get(0);
            assertEquals("Ender's Game", validation.getTitle());
            assertEquals("Orson Scott Card", validation.getAuthors().get(0));
            assertEquals(1985, validation.getYear());
            assertEquals(1, validation.getId());

            Book validation2 = endBooks.get(1);
            assertEquals("The End of History and the Last Man", validation2.getTitle());
            assertEquals("Francis Fukuyama", validation2.getAuthors().get(0));
            assertEquals(1992, validation2.getYear());
            assertEquals(2, validation2.getId());

            endBooks = toTest.getBooksByTitle("zebra");
            assertEquals(0, endBooks.size());
        } catch (InvalidAuthorsException | InvalidTitleException | InvalidYearException | InvalidQueryException ex) {
            fail();
        }
    }

    @Test
    public void testGetBooksByTitleNullTitle() {
        assertThrows(InvalidQueryException.class, () -> toTest.getBooksByTitle(null));
    }

    @Test
    public void testGetBooksByAuthorGoldenPath() {
        try {
            toTest.addBook(new Book("Speaker for the Dead", Arrays.asList("Orson Scott Card"), 1986));
            toTest.addBook(new Book("TEST", Arrays.asList("TEST"), 1992));

            List<Book> endBooks = toTest.getBooksByAuthor("Card");
            assertEquals(2, endBooks.size());

            Book validation = endBooks.get(0);
            assertEquals("Ender's Game", validation.getTitle());
            assertEquals("Orson Scott Card", validation.getAuthors().get(0));
            assertEquals(1985, validation.getYear());
            assertEquals(1, validation.getId());

            Book validation2 = endBooks.get(1);
            assertEquals("Speaker for the Dead", validation2.getTitle());
            assertEquals("Orson Scott Card", validation2.getAuthors().get(0));
            assertEquals(1986, validation2.getYear());
            assertEquals(2, validation2.getId());

            endBooks = toTest.getBooksByAuthor("zebra");
            assertEquals(0, endBooks.size());
        } catch (InvalidAuthorsException | InvalidTitleException | InvalidYearException | InvalidQueryException ex) {
            fail();
        }
    }

    @Test
    public void testGetBooksByAuthorNullAuthor() {
        assertThrows(InvalidQueryException.class, () -> toTest.getBooksByAuthor(null));
    }

    @Test
    public void testGetBooksByYearGoldenPath() {
        try {
            toTest.addBook(new Book("Speaker for the Dead", Arrays.asList("Orson Scott Card"), 1986));
            toTest.addBook(new Book("TEST", Arrays.asList("TEST"), 1985));

            List<Book> endBooks = toTest.getBooksByYear(1985);
            assertEquals(2, endBooks.size());

            Book validation = endBooks.get(0);
            assertEquals("Ender's Game", validation.getTitle());
            assertEquals("Orson Scott Card", validation.getAuthors().get(0));
            assertEquals(1985, validation.getYear());
            assertEquals(1, validation.getId());

            Book validation2 = endBooks.get(1);
            assertEquals("TEST", validation2.getTitle());
            assertEquals("TEST", validation2.getAuthors().get(0));
            assertEquals(1985, validation2.getYear());
            assertEquals(3, validation2.getId());

            endBooks = toTest.getBooksByYear(1000);
            assertEquals(0, endBooks.size());
        } catch (InvalidAuthorsException | InvalidTitleException | InvalidYearException | InvalidQueryException ex) {
            fail();
        }
    }

    @Test
    public void testGetBooksByYearNullYear() {
        assertThrows(InvalidQueryException.class, () -> toTest.getBooksByYear(null));
    }
}