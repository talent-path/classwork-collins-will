/*
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

            toTest.addBook(new Book("Ender's Game", Arrays.asList("Orson Scott Card"), 1985));
        } catch (InvalidIdException | InvalidTitleException | InvalidAuthorsException | InvalidYearException ex) {
            fail();
        }
    }

    @Test
    public void testAddBookGoldenPath() {
        try {
            Book testBook = new Book("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002);
            Book newBook = toTest.addBook(testBook);

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
            Book newBook2 = toTest.addBook(testBook2);

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

    @Test
    public void testEditBookTitleGoldenPath() {
        try {
            Book book = toTest.editBookTitle(1, "Success");

            assertEquals("Success", book.getTitle());
            assertEquals("Success", toTest.getBookById(1).getTitle());
        } catch (InvalidTitleException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testEditBookTitleNullTitle() {
        try {
            Book book = toTest.editBookTitle(1, null);
            fail();
        } catch (InvalidIdException ex) {
            fail();
        } catch (InvalidTitleException ex) {
            try {
                Book book = toTest.editBookTitle(1, "");
                fail();
            } catch (InvalidIdException ex2) {
                fail();
            } catch (InvalidTitleException ex2) {

            }
        }
    }

    @Test
    public void testEditBookTitleInvalidId() {
        try {
            Book book = toTest.editBookTitle(-1, "Success");
            fail();
        } catch (InvalidTitleException ex) {
            fail();
        } catch (InvalidIdException ex) {

        }
    }

    @Test
    public void testEditBookAuthorsGoldenPath() {
        try {
            Book book = toTest.editBookAuthors(1, Arrays.asList("Success"));

            assertEquals("Success", book.getAuthors().get(0));
            assertEquals("Success", toTest.getBookById(1).getAuthors().get(0));
        } catch (InvalidAuthorsException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testEditBookAuthorsNullAuthorList() {
        try {
            Book book = toTest.editBookAuthors(1, null);
            fail();
        } catch (InvalidIdException ex) {
            fail();
        } catch (InvalidAuthorsException ex) {
            try {
                Book book = toTest.editBookAuthors(1, Arrays.asList());
            } catch (InvalidIdException ex2) {
                fail();
            } catch (InvalidAuthorsException ex2) {

            }
        }
    }

    @Test
    public void testEditBookAuthorListWithNullAuthors() {
        try {
            Book book = toTest.editBookAuthors(1, Arrays.asList("Failure", null));
            fail();
        } catch (InvalidIdException ex) {
            fail();
        } catch (InvalidAuthorsException ex) {
            try {
                Book book = toTest.editBookAuthors(1, Arrays.asList(""));
            } catch (InvalidIdException ex2) {
                fail();
            } catch (InvalidAuthorsException ex2) {

            }
        }
    }

    @Test
    public void testEditBookAuthorsInvalidId() {
        try {
            Book book = toTest.editBookAuthors(-1, Arrays.asList("Success"));
            fail();
        } catch (InvalidAuthorsException ex) {
            fail();
        } catch (InvalidIdException ex) {

        }
    }

    @Test
    public void testEditBookYearGoldenPath() {
        try {
            Book book = toTest.editBookYear(1, 2000);

            assertEquals(2000, book.getYear());
            assertEquals(2000, toTest.getBookById(1).getYear());
        } catch (InvalidYearException | InvalidIdException ex) {
            fail();
        }
    }

    @Test
    public void testEditBookYearFutureYear() {
        try {
            Book book = toTest.editBookYear(1, 3000);
            fail();
        } catch (InvalidIdException ex) {
            fail();
        } catch (InvalidYearException ex) {

        }
    }

    @Test
    public void testEditBookYearInvalidId() {
        try {
            Book book = toTest.editBookYear(-1, 2000);
            fail();
        } catch (InvalidYearException ex) {
            fail();
        } catch (InvalidIdException ex) {

        }
    }

}*/
