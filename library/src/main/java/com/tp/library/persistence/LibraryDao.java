package com.tp.library.persistence;

import com.tp.library.controllers.BookRequest;

public interface LibraryDao {
    int addBook(BookRequest request) throws InvalidTitleException;
}
