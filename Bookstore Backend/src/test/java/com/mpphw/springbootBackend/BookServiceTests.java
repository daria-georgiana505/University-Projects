package com.mpphw.springbootBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.mpphw.springbootBackend.dto.Book;
import com.mpphw.springbootBackend.repository.BookRepository;
import com.mpphw.springbootBackend.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BookServiceTests {

    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
    }

    @Test
    void testGetBooks() {
        List<Book> expectedBooks = Arrays.asList(
                new Book("First Title", "First Author", "First Genre", 100),
                new Book("Second Title", "Second Author", "Second Genre", 200),
                new Book("Third Title", "Third Author", "Third Genre", 300));

        when(bookRepository.getBooks()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooks();

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void testAddBook() throws Exception {
        Book newBook = new Book("New Title", "New Author", "New Genre", 300);

        bookService.addBook(newBook);

        verify(bookRepository).addBook(newBook);
    }

    @Test
    void testGetDetailsBook() throws Exception {
        String title = "Title";
        Book expectedBook = new Book("Title", "Author", "Genre", 200);

        when(bookRepository.getDetailsBook(title)).thenReturn(expectedBook);

        Book actualBook = bookService.getDetailsBook(title);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    void testDeleteBook() throws Exception {
        String title = "Title";

        bookService.deleteBook(title);

        verify(bookRepository).deleteBook(title);
    }

    @Test
    void testUpdateBook() throws Exception {
        String title = "Title";
        Book updatedBook = new Book("Updated Title", "Updated Author", "Updated Genre", 300);

        bookService.updateBook(title, updatedBook);

        verify(bookRepository).updateBook(title, updatedBook);
    }
}
