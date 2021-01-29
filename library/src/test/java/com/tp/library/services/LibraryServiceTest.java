package com.tp.library.services;

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
class LibraryServiceTest {

    @Autowired
    LibraryService toTest;

    @BeforeEach
    public void setup() {
        List<Book> allBooks = toTest.dao.getAllBooks();

        try {
            for (Book toRemove : allBooks) {
                toTest.dao.deleteBook(toRemove.getId());
            }

            toTest.addBook("Ender's Game", Arrays.asList("Orson Scott Card"), 1985);
        } catch (InvalidIdException | InvalidTitleException | InvalidAuthorsException | InvalidYearException ex) {
            fail();
        }
    }

    @Test
    public void testAddBookGoldenPath() {
        try {
            int id = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002).getId();

            assertEquals(2, id);

            int nextId = toTest.addBook("IT", Arrays.asList("Stephen King"), 1986).getId();

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
            Book book = toTest.addBook(null, Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002);
            fail();
        } catch (InvalidAuthorsException | InvalidYearException ex) {
            fail();
        } catch (InvalidTitleException ex) {
            try {
                Book book = toTest.addBook("", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002);
                fail();
            } catch (InvalidAuthorsException | InvalidYearException ex2) {
                fail();
            } catch (InvalidTitleException ex2) {

            }
        }
    }

    @Test
    public void testAddBookNullAuthorList() {
        try {
            Book book = toTest.addBook("The Bearenstein Bears", null, 2002);
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
            Book book = toTest.addBook("The Bearenstein Bears", Arrays.asList(), 2002);
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
            Book book = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", null, "Jan Bearenstein"), 2002);
            fail();
        } catch (InvalidTitleException | InvalidYearException ex) {
            fail();
        } catch (InvalidAuthorsException ex) {
            try {
                Book book = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "", "Jan Bearenstein"), 2002);
                fail();
            } catch (InvalidTitleException | InvalidYearException ex2) {
                fail();
            } catch (InvalidAuthorsException ex2) {

            }
        }
    }

    @Test
    public void testAddBookNullYear() {
        try {
            Book book = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), null);
            fail();
        } catch (InvalidTitleException | InvalidAuthorsException ex) {
            fail();
        } catch (InvalidYearException ex) {

        }
    }

    @Test
    public void testAddBookFutureYear() {
        try {
            Book book = toTest.addBook("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2077);
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
    public void testGetBooksByCriteriaGoldenPath() {
        try {
            List<Book> books = toTest.getBooksByCriteria("title", "ender");
            assertEquals("Ender's Game", books.get(0).getTitle());

            books = toTest.getBooksByCriteria("author", "orson");
            assertEquals("Ender's Game", books.get(0).getTitle());

            books = toTest.getBooksByCriteria("year", "1985");
            assertEquals("Ender's Game", books.get(0).getTitle());

        } catch (InvalidQueryException | InvalidCriteriaException ex) {
            fail();
        }
    }

    @Test
    public void testGetBooksByCriteriaNullCriteria() {
        try {
            List<Book> books = toTest.getBooksByCriteria(null, "ender");
            fail();
        } catch (InvalidQueryException ex) {
            fail();
        } catch (InvalidCriteriaException ex) {
            try {
                List<Book> books = toTest.getBooksByCriteria("", "ender");
                fail();
            } catch (InvalidQueryException ex2) {
                fail();
            } catch (InvalidCriteriaException ex2) {

            }
        }
    }

    @Test
    public void testGetBooksByCriteriaNullSearchData() {
        try {
            List<Book> books = toTest.getBooksByCriteria("title", null);
            fail();
        } catch (InvalidCriteriaException ex) {
            fail();
        } catch (InvalidQueryException ex) {
            try {
                List<Book> books = toTest.getBooksByCriteria("title", "");
                fail();
            } catch (InvalidCriteriaException ex2) {
                fail();
            } catch (InvalidQueryException ex2) {

            }
        }
    }

    @Test
    public void testGetBooksByCriteriaInvalidCriteria() {
        try {
            List<Book> books = toTest.getBooksByCriteria("test", "test");
            fail();
        } catch (InvalidQueryException ex) {
            fail();
        } catch (InvalidCriteriaException ex) {

        }
    }

    @Test
    public void testGetBooksByCriteriaNonIntYear() {
        try {
            List<Book> books = toTest.getBooksByCriteria("year", "test");
            fail();
        } catch (InvalidCriteriaException ex) {
            fail();
        } catch (InvalidQueryException ex) {

        }
    }

}