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
            Book testBook = toTest.addBook(new Book("The Bearenstein Bears", Arrays.asList("Stan Bearenstein", "Jan Bearenstein"), 2002));

            assertEquals(2, testBook.getId());

            Book validation = toTest.getBookById(2);
            assertEquals("The Bearenstein Bears", validation.getTitle());

            List<String> validationAuthors = validation.getAuthors();
            assertEquals(2, validationAuthors.size());
            assertEquals("Stan Bearenstein", validationAuthors.get(0));
            assertEquals("Jan Bearenstein", validationAuthors.get(1));

            assertEquals(2002, validation.getYear());

            assertEquals(2, validation.getId());

            Book testBook2 = toTest.addBook(new Book ("IT", Arrays.asList("Stephen King", "King Stephen"), 1986));

            assertEquals(3, testBook2.getId());

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

    @Test
    public void testAddBookEmptyTitle() {
        assertThrows(InvalidTitleException.class, () -> toTest.addBook(new Book ("", Arrays.asList("Test"), 2000)));
    }

    @Test
    public void testAddBookBlankTitle() {
        assertThrows(InvalidTitleException.class, () -> toTest.addBook(new Book ("   ", Arrays.asList("Test"), 2000)));
    }

    // authors = null
    @Test
    public void testAddBookNullAuthors() {
        assertThrows(InvalidAuthorsException.class, () -> toTest.addBook(new Book ("Test", null, 2000)));
    }

    // authors = []
    @Test
    public void testAddBookEmptyAuthors() {
        assertThrows(InvalidAuthorsException.class, () -> toTest.addBook(new Book ("Test", Arrays.asList(), 2000)));
    }

    // authors = [name1, null, name2]
    @Test
    public void testAddBookAuthorListWithNullAuthors() {
        assertThrows(InvalidAuthorsException.class, () -> toTest.addBook(new Book ("Test", Arrays.asList("Test", null), 2000)));
    }

    @Test
    public void testAddBookAuthorListWithEmptyAuthors() {
        assertThrows(InvalidAuthorsException.class, () -> toTest.addBook(new Book ("Test", Arrays.asList("Test", ""), 2000)));
    }

    @Test
    public void testAddBookAuthorListWithBlankAuthors() {
        assertThrows(InvalidAuthorsException.class, () -> toTest.addBook(new Book ("Test", Arrays.asList("Test", "   "), 2000)));
    }

    @Test
    public void testAddBookNullYear() {
        assertThrows(InvalidYearException.class, () -> toTest.addBook(new Book ("Test", Arrays.asList("Test"), null)));
    }

    @Test
    public void testAddBookFutureYear() {
        assertThrows(InvalidYearException.class, () -> toTest.addBook(new Book ("Test", Arrays.asList("Test"), 2077)));
    }

    @Test
    public void testGetAllBooksGoldenPath() {
        List<Book> copy = toTest.getAllBooks();

        assertEquals(1, copy.size());
        assertEquals("Ender's Game", copy.get(0).getTitle());
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
            toTest.deleteBook(1);
        } catch (InvalidIdException ex) {
            fail();
        }
        assertThrows(InvalidIdException.class, () -> toTest.deleteBook(1));
    }

    @Test
    public void testDeleteBookGoldenPath() {
        try {
            assertNotEquals(null, toTest.getBookById(1));
            toTest.deleteBook(1);
            assertThrows(InvalidIdException.class, () -> toTest.getBookById(1));
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
    public void testGetBooksByCriteriaGoldenPath() {
        try {
            List<Book> books = toTest.getBooksByCriteria("title", "ender");
            assertEquals("Ender's Game", books.get(0).getTitle());
            assertEquals("Orson Scott Card", books.get(0).getAuthors().get(0));
            assertEquals(1985, books.get(0).getYear());
            assertEquals(1, books.get(0).getId());

            books = toTest.getBooksByCriteria("author", "orson");
            assertEquals("Ender's Game", books.get(0).getTitle());
            assertEquals("Orson Scott Card", books.get(0).getAuthors().get(0));
            assertEquals(1985, books.get(0).getYear());
            assertEquals(1, books.get(0).getId());

            books = toTest.getBooksByCriteria("year", "1985");
            assertEquals("Ender's Game", books.get(0).getTitle());
            assertEquals("Orson Scott Card", books.get(0).getAuthors().get(0));
            assertEquals(1985, books.get(0).getYear());
            assertEquals(1, books.get(0).getId());

        } catch (InvalidQueryException | InvalidCriteriaException ex) {
            fail();
        }
    }

    @Test
    public void testGetBooksByCriteriaNullCriteria() {
        assertThrows(InvalidCriteriaException.class, () -> toTest.getBooksByCriteria(null, "test"));
    }

    @Test
    public void testGetBooksByCriteriaEmptyCriteria() {
        assertThrows(InvalidCriteriaException.class, () -> toTest.getBooksByCriteria("", "test"));
    }

    @Test
    public void testGetBooksByCriteriaBlankCriteria() {
        assertThrows(InvalidCriteriaException.class, () -> toTest.getBooksByCriteria("   ", "test"));
    }

    @Test
    public void testGetBooksByCriteriaInvalidCriteria() {
        assertThrows(InvalidCriteriaException.class, () -> toTest.getBooksByCriteria("test", "test"));
    }

    @Test
    public void testGetBooksByCriteriaNullSearchData() {
        assertThrows(InvalidQueryException.class, () -> toTest.getBooksByCriteria("title", null));
    }

    @Test
    public void testGetBooksByCriteriaEmptySearchData() {
        assertThrows(InvalidQueryException.class, () -> toTest.getBooksByCriteria("title", ""));
    }

    @Test
    public void testGetBooksByCriteriaBlankSearchData() {
        assertThrows(InvalidQueryException.class, () -> toTest.getBooksByCriteria("title", "   "));
    }

    @Test
    public void testGetBooksByCriteriaNonIntYear() {
        assertThrows(InvalidQueryException.class, () -> toTest.getBooksByCriteria("year", "test"));
    }

    @Test
    public void testGetBooksByCriteriaFutureYear() {
        assertThrows(InvalidQueryException.class, () -> toTest.getBooksByCriteria("year", "2077"));
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

}
