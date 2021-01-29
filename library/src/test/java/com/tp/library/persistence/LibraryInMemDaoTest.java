package com.tp.library.persistence;

import com.tp.library.controllers.BookRequest;
import com.tp.library.exceptions.InvalidAuthorsException;
import com.tp.library.exceptions.InvalidIdException;
import com.tp.library.exceptions.InvalidTitleException;
import com.tp.library.exceptions.InvalidYearException;
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

    @Test public void testAddBookNullYear() {
        try {
            int id = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), null);
            fail();
        } catch (InvalidTitleException | InvalidAuthorsException ex) {
            fail();
        } catch (InvalidYearException ex) {

        }
    }
}