package com.tp.library.services;

import com.tp.library.controllers.BookRequest;
import com.tp.library.exceptions.*;
import com.tp.library.models.Book;
import com.tp.library.persistence.LibraryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.time.YearMonth;

@Component
public class LibraryService {

    @Autowired
    LibraryDao dao;

    public Book addBook(String title, List<String> authors, Integer year) throws InvalidTitleException, InvalidAuthorsException, InvalidYearException {
        if (title == "") {
            throw new InvalidTitleException("Books must have a title.");
        }

        if (authors.size() == 0) {
            throw new InvalidAuthorsException("Books must have at least one author.");
        }

        for (String author : authors) {
            if (author == "") {
                throw new InvalidAuthorsException("Authors must have a name.");
            }
        }

        if (year > java.time.YearMonth.now().getYear()) {
            throw new InvalidYearException("Books cannot be from the future.");
        }

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

    public List<Book> getBooksByCriteria(String criteria, String searchData) throws InvalidQueryException, InvalidCriteriaException {
        if (criteria == null || criteria == "") {
            throw new InvalidCriteriaException("Search criteria cannot be empty.");
        }

        if (searchData == null || searchData == "") {
            throw new InvalidQueryException("Search data cannot be empty.");
        }

        switch(criteria.toLowerCase()) {
            case "title":
                return dao.getBooksByTitle(searchData);
            case "author":
                return dao.getBooksByAuthor(searchData);
            case "year":
                try {
                    int searchYear = Integer.parseInt(searchData);
                    return dao.getBooksByYear(searchYear);
                } catch (NumberFormatException ex) {
                    throw new InvalidQueryException("Year queries must be an integer.");
                }
            default:
                throw new InvalidCriteriaException("Can only search books by title, author, or year.");
        }
    }
}
