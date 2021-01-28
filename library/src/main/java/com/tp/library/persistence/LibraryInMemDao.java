package com.tp.library.persistence;

import com.tp.library.controllers.BookRequest;
import com.tp.library.models.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryInMemDao implements LibraryDao{

    private List<Book> allBooks = new ArrayList<>();

    public int addBook(BookRequest request) throws InvalidTitleException {

        if (request.getTitle() == null) {
            throw new InvalidTitleException("Title cannot be null.");
        }

        int newId = 0;
        for (Book book : allBooks) {
            if (book.getId() > newId) {
                newId = book.getId();
            }
        }

        newId++;

        allBooks.add(new Book(request.getTitle(), request.getAuthors(), request.getYear(), newId));
        return newId;
    }
}
