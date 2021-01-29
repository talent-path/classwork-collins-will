package com.tp.library.persistence;

import com.tp.library.controllers.BookRequest;
import com.tp.library.exceptions.*;
import com.tp.library.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        toTest.addBook("Ender's Game", Arrays.asList("Orson Scott Card"), 1985);
    }

    @Test
    public void testAddBookGoldenPath() {
        try {
            int id = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002);

            assertEquals(2, id);

            int nextId = toTest.addBook("IT", Arrays.asList("Stephen King"), 1986);

            assertEquals(3, nextId);

            Book validation = toTest.getBookById(3);
            assertEquals("IT", validation.getTitle());

            List<String> validationAuthors = validation.getAuthors();
            assertEquals(1, validationAuthors.size());
            assertEquals("Stephen King", validationAuthors.get(0));

            assertEquals(1986, validation.getYear());

            assertEquals(3, validation.getId());
        } catch (InvalidAuthorsException | InvalidTitleException | InvalidYearException ex) {
            fail();
        }
    }

    @Test
    public void testAddBookNullTitle() {
        try {
            int id = toTest.addBook(null, Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002);
            fail();
        } catch (InvalidAuthorsException | InvalidYearException ex) {
            fail();
        } catch (InvalidTitleException ex) {
            try {
                int id = toTest.addBook("", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002);
                fail();
            } catch (InvalidAuthorsException | InvalidYearException ex2) {
                fail();
            } catch (InvalidTitleException ex2) {

            }
        }
    }

    // authors = null
    @Test
    public void testAddBookNullAuthors() {
        try {
            int id = toTest.addBook("The Bearenstein Bears", null, 2002);
            fail();
        } catch (InvalidTitleException | InvalidYearException ex) {
            fail();
        } catch (InvalidAuthorsException ex) {

        }
    }

    // authors = []
    @Test
    public void testAddBookNoAuthors() {
        try {
            int id = toTest.addBook("The Bearenstein Bears", Arrays.asList(), 2002);
            fail();
        } catch (InvalidTitleException | InvalidYearException ex) {
            fail();
        } catch (InvalidAuthorsException ex) {

        }
    }

    // authors = [name1, null, name2]
    @Test
    public void testAddBookAuthorListWithNullAuthors() {
        try {
            int id = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", null, "Jan Bearenstein"), 2002);
            fail();
        } catch (InvalidTitleException | InvalidYearException ex) {
            fail();
        } catch (InvalidAuthorsException ex) {

        }
    }

    @Test
    public void testAddBookNullYear() {
        try {
            int id = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), null);
            fail();
        } catch (InvalidTitleException | InvalidAuthorsException ex) {
            fail();
        } catch (InvalidYearException ex) {

        }
    }

    @Test
    public void testAddBookFutureYear() {
        try {
            int id = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2077);
            fail();
        } catch (InvalidTitleException | InvalidAuthorsException ex) {
            fail();
        } catch (InvalidYearException ex) {

        }
    }

    @Test
    public void testGetAllBooksGoldenPath() {
        List<Book> copy = toTest.getAllBooks();

        assertEquals(1, copy.size());
        assertEquals("Ender's Game", copy.get(0).getTitle());
    }

    @Test
    public void testGetBookByIdGoldenPath() {
        Book book = toTest.getBookById(1);

        assertEquals("Ender's Game", book.getTitle());
        assertEquals("Orson Scott Card", book.getAuthors().get(0));
        assertEquals(1985, book.getYear());
    }

    @Test
    public void testGetBookByIdInvalidId() {
        Book book = toTest.getBookById(-1);

        assertEquals(null, book);
    }

    @Test
    public void testDeleteBookGoldenPath() {
        try {
            toTest.deleteBook(1);
            assertEquals(0, toTest.getAllBooks().size());
        } catch (InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testDeleteBookInvalidId() {
        try {
            toTest.deleteBook(1);
            toTest.deleteBook(1);
            fail();
        } catch (InvalidIdException ex) {

        }
    }

    @Test
    public void testGetBooksByTitleGoldenPath() {
        try {
            toTest.addBook("The End of History and the Last Man", Arrays.asList("Francis Fukuyama"), 1992);
            toTest.addBook("TEST", Arrays.asList("TEST"), 1992);

            List<Book> endBooks = toTest.getBooksByTitle("end");
            assertEquals(2, endBooks.size());

            endBooks = toTest.getBooksByTitle("zebra");
            assertEquals(0, endBooks.size());
        } catch (InvalidAuthorsException | InvalidTitleException | InvalidYearException | InvalidQueryException ex) {
            fail();
        }
    }

    @Test
    public void testGetBooksByTitleNullTitle() {
        try {
            List<Book> endBooks = toTest.getBooksByTitle(null);
            fail();
        } catch (InvalidQueryException ex) {
            try {
                List<Book> endBooks = toTest.getBooksByTitle("");
                fail();
            } catch (InvalidQueryException ex2) {

            }
        }
    }

    @Test
    public void testGetBooksByAuthorGoldenPath() {
        try {
            toTest.addBook("Speaker for the Dead", Arrays.asList("Orson Scott Card"), 1986);
            toTest.addBook("TEST", Arrays.asList("TEST"), 1992);

            List<Book> endBooks = toTest.getBooksByAuthor("Card");
            assertEquals(2, endBooks.size());

            endBooks = toTest.getBooksByAuthor("zebra");
            assertEquals(0, endBooks.size());
        } catch (InvalidAuthorsException | InvalidTitleException | InvalidYearException | InvalidQueryException ex) {
            fail();
        }
    }

    @Test
    public void testGetBooksByAuthorNullAuthor() {
        try {
            List<Book> endBooks = toTest.getBooksByAuthor(null);
            fail();
        } catch (InvalidQueryException ex) {
            try {
                List<Book> endBooks = toTest.getBooksByAuthor("");
                fail();
            } catch (InvalidQueryException ex2) {

            }
        }
    }

    @Test
    public void testGetBooksByYearGoldenPath() {
        try {
            toTest.addBook("Speaker for the Dead", Arrays.asList("Orson Scott Card"), 1985);
            toTest.addBook("TEST", Arrays.asList("TEST"), 1992);

            List<Book> endBooks = toTest.getBooksByYear(1985);
            assertEquals(2, endBooks.size());

            endBooks = toTest.getBooksByYear(0);
            assertEquals(0, endBooks.size());
        } catch (InvalidAuthorsException | InvalidTitleException | InvalidYearException | InvalidQueryException ex) {
            fail();
        }
    }

    @Test
    public void testGetBooksByYearFutureYear() {
        try {
            List<Book> endBooks = toTest.getBooksByYear(2077);
            fail();
        } catch (InvalidQueryException ex) {

        }
    }
}