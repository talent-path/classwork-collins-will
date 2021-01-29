package com.tp.library.persistence;

import com.tp.library.controllers.BookRequest;
import com.tp.library.exceptions.InvalidAuthorsException;
import com.tp.library.exceptions.InvalidIdException;
import com.tp.library.exceptions.InvalidTitleException;
import com.tp.library.exceptions.InvalidYearException;
import com.tp.library.models.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryInMemDao implements LibraryDao{

    private List<Book> allBooks = new ArrayList<>();

    @Override
    public int addBook(String title, List<String> authors, Integer year) throws InvalidTitleException, InvalidAuthorsException, InvalidYearException {

        if (title == null) {
            throw new InvalidTitleException("Title cannot be null.");
        }

        if (authors == null) {
            throw new InvalidAuthorsException("Author list cannot be null.");
        }

        if (authors.size() == 0) {
            throw new InvalidAuthorsException("All books must have at least one author.");
        }

        for (String author : authors) {
            if (author == null) {
                throw new InvalidAuthorsException("Authors cannot be null.");
            }
        }

        if (year == null) {
            throw new InvalidYearException("Publication year cannot be null.");
        }

        int newId = 0;
        for (Book book : allBooks) {
            if (book.getId() > newId) {
                newId = book.getId();
            }
        }

        newId++;

        Book toAdd = new Book(title, authors, year, newId);

        allBooks.add(toAdd);
        return newId;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> copyList = new ArrayList<>();

        for (Book toCopy : allBooks) {
            copyList.add(new Book(toCopy));
        }

        return copyList;
    }

    @Override
    public Book getBookById(Integer id) {
        Book toReturn = null;

        for (Book toCheck : allBooks) {
            if (toCheck.getId().equals(id)) {
                toReturn = new Book(toCheck);
                break;
            }
        }

        return toReturn;
    }

    @Override
    public void deleteBook(Integer id) throws InvalidIdException {
        int removeIndex = -1;

        for (int i = 0; i < allBooks.size(); i++) {
            if (allBooks.get(i).getId().equals(id)) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex != -1) {
            allBooks.remove(removeIndex);
        } else {
            throw new InvalidIdException("Could not find book with id " + id + " to delete.");
        }
    }

}
