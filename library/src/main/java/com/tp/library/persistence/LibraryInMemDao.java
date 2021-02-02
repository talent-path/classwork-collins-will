package com.tp.library.persistence;

import com.tp.library.exceptions.*;
import com.tp.library.models.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryInMemDao implements LibraryDao{

    private List<Book> allBooks = new ArrayList<>();

    @Override
    public int addBook(Book book) throws InvalidTitleException, InvalidAuthorsException, InvalidYearException {

        if (book.getTitle() == null) {
            throw new InvalidTitleException("Title cannot be null.");
        }

        if (book.getAuthors() == null) {
            throw new InvalidAuthorsException("Author list cannot be null.");
        }

        if (book.getAuthors().size() == 0) {
            throw new InvalidAuthorsException("All books must have at least one author.");
        }

        for (String author : book.getAuthors()) {
            if (author == null) {
                throw new InvalidAuthorsException("Authors cannot be null.");
            }
        }

        if (book.getYear() == null) {
            throw new InvalidYearException("Publication year cannot be null.");
        }

        int newId = 0;
        for (Book toCheck : allBooks) {
            if (toCheck.getId() > newId) {
                newId = toCheck.getId();
            }
        }

        newId++;

        book.setId(newId);

        allBooks.add(book);
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
    public Book getBookById(Integer id) throws InvalidIdException {
        if (id == null) {
            throw new InvalidIdException("Book id cannot be null.");
        }

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
        if (id == null) {
            throw new InvalidIdException("Id cannot be null.");
        }

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

    @Override
    public List<Book> getBooksByTitle(String titleToFind) throws InvalidQueryException {
        if (titleToFind == null || titleToFind == "") {
            throw new InvalidQueryException("Query cannot be empty.");
        }

        List<Book> toReturn = new ArrayList<>();

        for (Book book : allBooks) {
            if (book.getTitle().toLowerCase().contains(titleToFind.toLowerCase())) {
                toReturn.add(new Book(book));
            }
        }

        return toReturn;
    }

    @Override
    public List<Book> getBooksByAuthor(String authorToFind) throws InvalidQueryException {
        if (authorToFind == null || authorToFind == "") {
            throw new InvalidQueryException("Query cannot be null.");
        }

        List<Book> toReturn = new ArrayList<>();

        for (Book book : allBooks) {
            for (String author : book.getAuthors()) {
                if (author.toLowerCase().contains(authorToFind.toLowerCase())) {
                    toReturn.add(new Book(book));
                    break;
                }
            }
        }

        return toReturn;
    }

    @Override
    public List<Book> getBooksByYear(Integer yearToFind) throws InvalidQueryException{
        if (yearToFind > java.time.YearMonth.now().getYear()) {
            throw new InvalidQueryException("Query cannot be in the future.");
        }

        List<Book> toReturn = new ArrayList<>();

        for (Book book : allBooks) {
            if (book.getYear() == yearToFind) {
                toReturn.add(new Book(book));
            }
        }

        return toReturn;
    }

    @Override
    public void updateBook(Book book) {
        for (int i = 0; i < allBooks.size(); i++) {
            if (allBooks.get(i).getId().equals(book.getId())) {
                allBooks.set(i, new Book(book));
            }
        }
    }
}
