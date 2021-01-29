package com.tp.library.controllers;

import com.tp.library.exceptions.InvalidAuthorsException;
import com.tp.library.exceptions.InvalidTitleException;
import com.tp.library.exceptions.InvalidYearException;
import com.tp.library.models.Book;
import com.tp.library.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    LibraryService service;

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody BookRequest request) {
        Book toReturn = null;

        System.out.println(request.getAuthors().getClass());

        try {
            toReturn = service.addBook(request.getTitle(), request.getAuthors(), request.getYear());
        } catch (InvalidTitleException | InvalidAuthorsException | InvalidYearException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(toReturn);
    }

    @GetMapping("/book")
    public List<Book> getAllGames() {
        return service.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity getBookById(@PathVariable Integer id) {
        Book toReturn = service.getBookById(id);

        if (toReturn == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No book with id " + id + " found.");
        } else {
            return ResponseEntity.ok(toReturn);
        }
    }

}
