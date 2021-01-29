package com.tp.library.services;

import com.tp.library.controllers.BookRequest;
import com.tp.library.exceptions.InvalidAuthorsException;
import com.tp.library.exceptions.InvalidIdException;
import com.tp.library.exceptions.InvalidTitleException;
import com.tp.library.exceptions.InvalidYearException;
import com.tp.library.models.Book;
import com.tp.library.persistence.LibraryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibraryService {

    @Autowired
    LibraryDao dao;

    public Book addBook(String title, List<String> authors, Integer year) throws InvalidTitleException, InvalidAuthorsException, InvalidYearException {
        int newId = dao.addBook(title, authors, year);

        return dao.getBookById(newId);
    }

    public List<Book> getAllBooks() {
        return dao.getAllBooks();
    }

    public Book getBookById(Integer id) {
        return dao.getBookById(id);
    }

    public void deleteBook(Integer id) throws InvalidIdException {
        dao.deleteBook(id);
    }
}
