package com.tp.library.services;

import com.tp.library.exceptions.*;
import com.tp.library.models.Book;
import com.tp.library.persistence.LibraryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LibraryService {

    @Autowired
    LibraryDao dao;

    public Book addBook(Book book) throws InvalidTitleException, InvalidAuthorsException, InvalidYearException, InvalidIdException {
        if (book.getTitle() == null || book.getTitle() == "") {
            throw new InvalidTitleException("Books must have a title.");
        }

        if (book.getAuthors() == null || book.getAuthors().size() == 0) {
            throw new InvalidAuthorsException("Books must have at least one author.");
        }

        for (String author : book.getAuthors()) {
            if (author == null || author == "") {
                throw new InvalidAuthorsException("Authors must have a name.");
            }
        }

        if (book.getYear() == null) {
            throw new InvalidYearException("Years cannot be null.");
        }

        if (book.getYear() > java.time.YearMonth.now().getYear()) {
            throw new InvalidYearException("Books cannot be from the future.");
        }

        int newId = dao.addBook(book);

        return dao.getBookById(newId);
    }

    public List<Book> getAllBooks() {
        return dao.getAllBooks();
    }

    public Book getBookById(Integer id) throws InvalidIdException {
        return dao.getBookById(id);
    }

    public void deleteBook(Integer id) throws InvalidIdException {
        if (id < 1) {
            throw new InvalidIdException("Id must be at least 1.");
        }

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
                    Integer searchYear = Integer.parseInt(searchData);
                    return dao.getBooksByYear(searchYear);
                } catch (NumberFormatException ex) {
                    throw new InvalidQueryException("Year queries must be an integer.");
                }
            default:
                throw new InvalidCriteriaException("Can only search books by title, author, or year.");
        }
    }

    public Book editBookTitle(Integer id, String newTitle) throws InvalidTitleException, InvalidIdException {
        if (newTitle == null) {
            throw new InvalidTitleException("Title cannot be null.");
        }

        Book toEdit = getBookById(id);

        if (toEdit == null) {
            throw new InvalidIdException("Book with id " + id + " cannot be found.");
        }

        toEdit.setTitle(newTitle);

        dao.updateBook(toEdit);

        return dao.getBookById(id);
    }

    public Book editBookAuthors(Integer id, List<String> newAuthors) throws InvalidAuthorsException, InvalidIdException {
        if (newAuthors == null || newAuthors.size() == 0) {
            throw new InvalidAuthorsException("All books must have at least one author.");
        }

        for (String author : newAuthors) {
            if (author == null || author == "") {
                throw new InvalidAuthorsException("All authors must have a name.");
            }
        }

        Book toEdit = getBookById(id);

        if (toEdit == null) {
            throw new InvalidIdException("Book with id " + id + " cannot be found.");
        }

        toEdit.setAuthors(newAuthors);

        dao.updateBook(toEdit);

        return dao.getBookById(id);
    }

    public Book editBookYear(Integer id, Integer newYear) throws InvalidYearException, InvalidIdException {
        if (newYear > java.time.YearMonth.now().getYear()) {
            throw new InvalidYearException("Books cannot be from the future.");
        }

        Book toEdit = getBookById(id);

        if (toEdit == null) {
            throw new InvalidIdException("Book with id " + id + " cannot be found.");
        }

        toEdit.setYear(newYear);

        dao.updateBook(toEdit);

        return dao.getBookById(id);
    }
}
