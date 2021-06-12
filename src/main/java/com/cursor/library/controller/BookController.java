package com.cursor.library.controller;

import com.cursor.library.CreateBookDto;
import com.cursor.library.exception.CreateBookException;
import com.cursor.library.exception.UpdateBookException;
import com.cursor.library.models.Book;
import com.cursor.library.service.BookService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured({"ROLE_ADMIN", "ROLE_WRITE", "ROLE_READ"})
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_WRITE"})
    @GetMapping(
            value = "/books",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Book>> getBooks(
            @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) Integer offset
    ) {
        final List<Book> result = bookService.getAll(offset, limit);
        HttpHeaders headers = new HttpHeaders();
        headers.add("limit", limit + "");
        headers.add("offset", offset + "");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(
            value = "/books/admin",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Book>> getBooksAdminExample(
            @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) Integer offset
    ) {
        final List<Book> result = bookService.getAll(offset, limit);
        HttpHeaders headers = new HttpHeaders();
        headers.add("limit", limit + "");
        headers.add("offset", offset + "");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }
    @Secured({"ROLE_READ", "ROLE_WRITE"})
    @GetMapping(
            value = "/books/user",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Book>> getBooksUserExample(
            @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
            @RequestParam(value = "offset", defaultValue = "0", required = false) Integer offset
    ) {
        final List<Book> result = bookService.getAll(offset, limit);
        HttpHeaders headers = new HttpHeaders();
        headers.add("limit", limit + "");
        headers.add("offset", offset + "");
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @PostMapping(
            value = "/books",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> createBook(
            @RequestBody final CreateBookDto createBookDto
    ) throws CreateBookException {
        final Book book = bookService.createBook(createBookDto.getName(), createBookDto.getAuthor(), createBookDto.getYear(), createBookDto.getGenre());
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/books/{bookId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> getBook(@PathVariable("bookId") String bookId) {
        final Book result = bookService.findById(bookId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping(
            value = "/books/sorted",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getSortedBooks(
            @RequestParam(value = "sorted", defaultValue = "name", required = false) String sorted
    ) {
        return new ResponseEntity<>(bookService.getSortedBooks(sorted), HttpStatus.OK);
    }

    @GetMapping(
            value = "/books/author/{bookAuthor}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Book>> getAllBooksByAuthor(@PathVariable("bookAuthor") String bookAuthor) {
        final List<Book> result = bookService.findAllByAuthor(bookAuthor);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping(
            value = "/books/update/{bookId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Book> updateBook(
            @PathVariable("bookId") String bookId,
            @RequestBody final CreateBookDto createBookDto
    ) throws UpdateBookException {
        final Book book = bookService.updateBook(bookId, createBookDto.getName(), createBookDto.getAuthor(), createBookDto.getYear(), createBookDto.getGenre());
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}
