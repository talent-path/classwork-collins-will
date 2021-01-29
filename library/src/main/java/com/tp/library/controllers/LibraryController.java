package com.tp.library.controllers;

import com.tp.library.exceptions.*;
import com.tp.library.models.Book;
import com.tp.library.services.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LibraryController {

    @Autowired
    LibraryService service;

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody BookRequest request) {
        Book toReturn = null;

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

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        try {
            service.deleteBook(id);
            return "Book " + id + " successfully deleted.";
        } catch (InvalidIdException ex) {
            return ex.getMessage();
        }
    }

    @PostMapping("/find")
    public ResponseEntity getBooksByCriteria(@RequestBody CriteriaRequest request) {
        List<Book> toReturn = new ArrayList<>();

        try {
            toReturn = service.getBooksByCriteria(request.getCriteria(), request.getSearchData());

        } catch (InvalidQueryException | InvalidCriteriaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        if (toReturn.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No books found with that criteria.");
        }

        return ResponseEntity.ok(toReturn);
    }

    @PutMapping("/edit/title/{id}")
    public ResponseEntity editBookTitle(@PathVariable Integer id, @RequestBody ChangeRequest request) {
        Book toReturn = null;

        try {
            toReturn = service.editBookTitle(id, request.getNewTitle());
        } catch (InvalidTitleException | InvalidIdException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(toReturn);
    }

    @PutMapping("/edit/author/{id}")
    public ResponseEntity editBookAuthors(@PathVariable Integer id, @RequestBody ChangeRequest authors) {
        Book toReturn = null;

        try {
            toReturn = service.editBookAuthors(id, authors.getNewAuthors());
        } catch (InvalidAuthorsException | InvalidIdException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(toReturn);
    }

    @PutMapping("/edit/year/{id}")
    public ResponseEntity editBookYear(@PathVariable Integer id, @RequestBody ChangeRequest request) {
        Book toReturn = null;

        try {
            toReturn = service.editBookYear(id, request.getNewYear());
        } catch (InvalidYearException | InvalidIdException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

        return ResponseEntity.ok(toReturn);
    }

}
