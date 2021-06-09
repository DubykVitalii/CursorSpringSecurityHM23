package com.cursor.library;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepo bookRepo;

    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book createBook(
            final String name,
            final String author,
            final Integer year,
            final String genre
    ) throws CreateBookException {
        if (name == null) {
            throw new CreateBookException("Name field cannot be null");
        }
        if (year == null || year > 2021) {
            throw new CreateBookException("Illegal year argument");
        }
        return bookRepo.saveBook(
                name.trim(),
                author == null ? null : author.trim(),
                year,
                genre == null ? null : genre.trim());
    }

    public List<Book> getAll() {
        return bookRepo.getAll();
    }

    public List<Book> getAll(Integer offset, Integer limit) {
        return bookRepo.getAll(offset, limit);
    }

    public Book findById(String bookId) {
        return bookRepo.findById(bookId);
    }

    public List<Book> getSortedBooks(String sorted) {
        return bookRepo.getSortedBooks(sorted);
    }

    public List<Book> findAllByAuthor(String bookAuthor) {
        return bookRepo.findAllByAuthor(bookAuthor);
    }

    public Book updateBook(String bookId, String name, String author, Integer year, String genre) throws UpdateBookException {
        if (name == null) {
            throw new UpdateBookException("Name field cannot be null");
        }
        if (year == null || year > 2021) {
            throw new UpdateBookException("Illegal year argument");
        }
        return bookRepo.updateBook(
                bookId,
                name.trim(),
                author == null ? null : author.trim(),
                year,
                genre == null ? null : genre.trim());
    }
}
