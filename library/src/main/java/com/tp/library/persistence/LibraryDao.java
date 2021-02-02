package com.tp.library.persistence;

import com.tp.library.exceptions.*;
import com.tp.library.models.Book;

import java.util.List;

public interface LibraryDao {
    int addBook(Book book) throws InvalidTitleException, InvalidAuthorsException, InvalidYearException;

    List<Book> getAllBooks();

    Book getBookById(Integer id) throws InvalidIdException;

    void deleteBook(Integer id) throws InvalidIdException;

    List<Book> getBooksByTitle(String titleToFind) throws InvalidQueryException;

    List<Book> getBooksByAuthor(String authorToFind) throws InvalidQueryException;

    List<Book> getBooksByYear(Integer yearToFind) throws InvalidQueryException;

    void updateBook(Book book);
}
