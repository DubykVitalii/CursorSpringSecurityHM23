package com.cursor.library;

import com.cursor.library.BookRepo;
import com.cursor.library.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;


public class BookServiceTest {

    private final BookRepo bookRepoMock = Mockito.mock(BookRepo.class);
    private final BookService bookService = new BookService(bookRepoMock);

    @BeforeEach
    void setUp() {
        Mockito.reset(bookRepoMock);
    }

    @Test
    void createBookTestIncorrectName(){
        Assertions.assertThrows(
                CreateBookException.class,
                () -> bookService.createBook(null, "cool author", 2000, "Fantacy"),
                "Create book exception should fail when passing null value for book name"
        );
    }

    @ParameterizedTest
    @CsvSource(
            {
                    "sdsf ,  book   ,1999,  Comedy,sdsf,book,1999,Comedy",
                    "sdsf ,  book   ,1999,  null,sdsf,book,1999,null",
            }
            )
    void createBookTestExcpectedScenario(
            String name,
            String author,
            Integer year,
            String genre,
            String excpectedName,
            String excpectedAuthor,
            Integer excpectedYear,
            String excpectedGenre
    ) throws CreateBookException {
        bookService.createBook(name, author, year, genre);

        Mockito.verify(bookRepoMock).saveBook(excpectedName, excpectedAuthor, excpectedYear, excpectedGenre);
    }
}
