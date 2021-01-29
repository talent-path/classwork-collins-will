package com.tp.library.persistence;

import com.tp.library.controllers.BookRequest;
import com.tp.library.exceptions.InvalidAuthorsException;
import com.tp.library.exceptions.InvalidIdException;
import com.tp.library.exceptions.InvalidTitleException;
import com.tp.library.exceptions.InvalidYearException;
import com.tp.library.models.Book;

import java.util.List;

public interface LibraryDao {
    int addBook(String title, List<String> authors, Integer year) throws InvalidTitleException, InvalidAuthorsException, InvalidYearException;

    List<Book> getAllBooks();

    Book getBookById(Integer id);

    void deleteBook(Integer id) throws InvalidIdException;
}
