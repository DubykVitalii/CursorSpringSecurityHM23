package com.cursor.library;

import com.cursor.library.controller.BookController;
import com.cursor.library.models.Book;
import com.cursor.library.repository.BookRepo;
import com.cursor.library.service.BookService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {BookController.class, BookService.class, BookRepo.class})
@WebMvcTest
class BookControllerTest {

    private final ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookService bookService;

    @Test
    void getAllBooksTest() throws Exception {
        String uri = "/books";
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void createBookTest() throws Exception {
        String uri = "/books";
        CreateBookDto createBookDto = new CreateBookDto();
        createBookDto.setName("new book name");
        createBookDto.setAuthor("new author name");
        createBookDto.setYear(2020);
        createBookDto.setGenre("");
        mockMvc.perform(MockMvcRequestBuilders
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(createBookDto))
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    void getByIdBookTest() throws Exception {
        List<Book> books = bookService.getAll();
        int randomIndex = new Random().nextInt(books.size());
        Book book = books.get(randomIndex);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/" + book.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getByAuthorBookTest() throws Exception {
        List<Book> books = bookService.getAll();
        int randomIndex = new Random().nextInt(books.size());
        Book book = books.get(randomIndex);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/author/" + book.getAuthor())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getByIdExpectNotFoundStatusForBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/" + UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    void getByAuthorNameExpectNotFoundStatusForBookTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/" + "some author name 232553525232"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    @Test
    void updateBooksTest() throws Exception {
        List<Book> books = bookService.getAll();
        int randomIndex = new Random().nextInt(books.size());
        Book book = books.get(randomIndex);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/books/update/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(book))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}

