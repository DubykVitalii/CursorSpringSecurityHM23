package com.cursor.library.repository;

import com.cursor.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookRepo {

    private final Map<String, Book> books = new HashMap<>();




    public BookRepo() {
        final String id1 = UUID.randomUUID().toString();
        books.put(id1, new Book(id1, "1984", "George Orwell", 1949, "Novel"));
        final String id2 = UUID.randomUUID().toString();
        books.put(id2, new Book(id2, "To Kill a Mockingbird", "Harper Lee", 1960, "Novel"));
        final String id3 = UUID.randomUUID().toString();
        books.put(id3, new Book(id3, "Harry Potter and the Philosopher’s Stone", "J.K. Rowling", 1997, "Fantasy"));
        final String id4 = UUID.randomUUID().toString();
        books.put(id4, new Book(id4, "The Lord of the Rings", "J.R.R. Tolkien", 1964, "Adventure"));
        final String id5 = UUID.randomUUID().toString();
        books.put(id5, new Book(id5, "The Great Gatsby", "F. Scott Fitzgerald", 1925, "Novel"));
    }

    public List<Book> getAll(Integer offset, Integer limit) {
        if(limit>=books.size()) {
            limit=books.size();
        }

        return new ArrayList<>(books.values()).subList(offset, limit);
    }
    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }

    public Book saveBook(
            final String name,
            final String author,
            final Integer year,
            final String genre
    ) {
        final Book newBook = new Book(UUID.randomUUID().toString(), name, author, year, genre);
        return books.put(newBook.getId(), newBook);
    }

    public Book findById(String bookId) {
        return books.get(bookId);
    }

    public List<Book> getSortedBooks(String sorted) {
        ArrayList<Book> listBooks = new ArrayList<>(books.values());
        if(sorted.equals("name"))
        {
           listBooks.sort(Comparator.comparing(Book::getName).thenComparing(Book::getYear));
        } else if (sorted.equals("year")) {
            listBooks.sort(Comparator.comparing(Book::getYear).thenComparing(Book::getName));
        }
        return listBooks;
    }

    public List<Book> findAllByAuthor(String bookAuthor) {
        ArrayList<Book> listBooks = new ArrayList<>(books.values());
        List<Book> result = listBooks.stream().filter(book -> book.getAuthor().equals(bookAuthor)).collect(Collectors.toList());
        if (result.size() == 0) {
            return null;
        }
        return result;
    }

    public Book updateBook(
            final String bookId,
            final String name,
            final String author,
            final Integer year,
            final String genre) {
        books.get(bookId).setName(name);
        books.get(bookId).setAuthor(author);
        books.get(bookId).setYear(year);
        books.get(bookId).setGenre(genre);
        return books.get(bookId);
    }
}
