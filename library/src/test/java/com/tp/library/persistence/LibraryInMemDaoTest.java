package com.tp.library.persistence;

import com.tp.library.controllers.BookRequest;
import com.tp.library.exceptions.InvalidAuthorsException;
import com.tp.library.exceptions.InvalidTitleException;
import com.tp.library.exceptions.InvalidYearException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryInMemDaoTest {

    @Autowired
    LibraryInMemDao toTest;

    @Test
    public void testAddBookGoldenPath() {

    }

    @Test
    public void testAddBookNullTitle() {

    }

    // authors = null
    @Test
    public void testAddBookNullAuthors() {

    }

    // authors = []
    @Test
    public void testAddBookNoAuthors() {

    }

    @Test public void testAddBookNullYear() {

    }
}