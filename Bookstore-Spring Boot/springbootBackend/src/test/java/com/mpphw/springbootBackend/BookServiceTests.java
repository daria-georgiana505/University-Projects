package com.mpphw.springbootBackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.mpphw.springbootBackend.model.Book;
import com.mpphw.springbootBackend.model.BookReview;
import com.mpphw.springbootBackend.repository.BookRepository;
import com.mpphw.springbootBackend.repository.BookReviewRepository;
import com.mpphw.springbootBackend.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class BookServiceTests {

    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private BookReviewRepository bookReviewRepository;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository, bookReviewRepository);
    }

    @Test
    void testGetBooks() {
        List<Book> expectedBooks = Arrays.asList(
                new Book("First Title", "First Author", "First Genre", 100),
                new Book("Second Title", "Second Author", "Second Genre", 200),
                new Book("Third Title", "Third Author", "Third Genre", 300));

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooks();

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void testAddBook() throws Exception {
        Book newBook = new Book("New Title", "New Author", "New Genre", 300);

        when(bookRepository.existsById(newBook.getTitle())).thenReturn(false);

        bookService.addBook(newBook);

        verify(bookRepository).save(newBook);
    }

    @Test
    void testAddExistingBook() throws Exception {
        Book newBook = new Book("New Title", "New Author", "New Genre", 300);

        when(bookRepository.existsById(newBook.getTitle())).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> {
            bookService.addBook(newBook);
        });

        assertEquals("Book title already in the database", exception.getMessage());
        verify(bookRepository, never()).save(newBook);
    }

    @Test
    void testGetDetailsBook() throws Exception {
        String title = "Title";
        Book expectedBook = new Book("Title", "Author", "Genre", 200);

        when(bookRepository.findById(title)).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookService.getDetailsBook(title);

        assertEquals(expectedBook, actualBook);
    }

    @Test
    void testGetDetailsBookException() throws Exception {
        String title = "Title";

        when(bookRepository.findById(title)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            Book actualBook = bookService.getDetailsBook(title);
        });

        assertEquals("Book title not found", exception.getMessage());
    }

    @Test
    void testDeleteBook() throws Exception {
        String title = "Title";

        when(bookRepository.existsById(title)).thenReturn(true);

        bookService.deleteBook(title);

        verify(bookRepository).deleteById(title);
    }

    @Test
    void testDeleteBookException() throws Exception {
        String title = "Title";

        when(bookRepository.existsById(title)).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> {
            bookService.deleteBook(title);
        });

        assertEquals("Book title not found", exception.getMessage());

        verify(bookRepository, never()).deleteById(title);
    }

    @Test
    void testUpdateBook() throws Exception {
        String title = "Title";
        Book updatedBook = new Book("Updated Title", "Updated Author", "Updated Genre", 300);

        when(bookRepository.existsById(title)).thenReturn(true);
        when(bookRepository.existsById(updatedBook.getTitle())).thenReturn(false);
        when(bookRepository.findById(title)).thenReturn(java.util.Optional.of(new Book()));

        bookService.updateBook(title, updatedBook);

        verify(bookRepository).deleteById(title);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testUpdateBookExceptionTitleNotFound() throws Exception {
        String title = "Title";
        Book updatedBook = new Book("Updated Title", "Updated Author", "Updated Genre", 300);

        when(bookRepository.existsById(title)).thenReturn(false);
        when(bookRepository.existsById(updatedBook.getTitle())).thenReturn(false);
        when(bookRepository.findById(title)).thenReturn(java.util.Optional.of(new Book()));

        Exception exception = assertThrows(Exception.class, () -> {
            bookService.updateBook(title, updatedBook);
        });

        assertEquals("Book title not found", exception.getMessage());

        verify(bookRepository, never()).deleteById(title);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testUpdateBookExceptionBookAlreadyInTable() throws Exception {
        String title = "Title";
        Book updatedBook = new Book("Updated Title", "Updated Author", "Updated Genre", 300);

        when(bookRepository.existsById(title)).thenReturn(true);
        when(bookRepository.existsById(updatedBook.getTitle())).thenReturn(true);
        when(bookRepository.findById(title)).thenReturn(java.util.Optional.of(new Book()));

        Exception exception = assertThrows(Exception.class, () -> {
            bookService.updateBook(title, updatedBook);
        });

        assertEquals("The updated title of the book is already in the table", exception.getMessage());

        verify(bookRepository, never()).deleteById(title);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testGetBookReviews() throws Exception {
        List<BookReview> expectedBookReviews = Arrays.asList(
                new BookReview("user1", "Amazing book"),
                new BookReview("user5", "I didn't enjoy it that much..."),
                new BookReview("user2", "I loved it!!!"));
        Book book = new Book("Title", "Author", "Genre", 200);
        book.setReviews(expectedBookReviews);

        when(bookRepository.existsById(book.getTitle())).thenReturn(true);
        when(bookRepository.findById(book.getTitle())).thenReturn(Optional.of(book));

        List<BookReview> actualBookReviews = bookService.getBookReviews(book.getTitle());

        assertEquals(expectedBookReviews, actualBookReviews);
    }


    @Test
    void testAddBookReview() throws Exception {
        Book book = new Book("Title", "Author", "Genre", 200);
        BookReview newBookReview = new BookReview("user0", "Not that great...");

        when(bookRepository.existsById(book.getTitle())).thenReturn(true);
        when(bookRepository.findById(book.getTitle())).thenReturn(Optional.of(book));

        bookService.addBookReview(book.getTitle(), newBookReview);

        verify(bookReviewRepository).save(newBookReview);
    }

    @Test
    void testGetDetailsBookReview() throws Exception {
        int id = 100;
        BookReview expectedBookReview = new BookReview("user0", "Not that great...");

        when(bookReviewRepository.findById(id)).thenReturn(Optional.of(expectedBookReview));

        BookReview actualBookReview = bookService.getDetailsBookReview(id);

        assertEquals(expectedBookReview, actualBookReview);
    }

    @Test
    void testDeleteBookReview() throws Exception {
        int id = 100;

        when(bookReviewRepository.existsById(id)).thenReturn(true);

        bookService.deleteBookReview(id);

        verify(bookReviewRepository).deleteById(id);
    }

    @Test
    void testUpdateBookReview() throws Exception {
        int id = 100;
        BookReview foundBookReview = new BookReview("user0", "Not that great...");
        BookReview updatedBookReview = new BookReview("user10", "AMAZING!");

        when(bookReviewRepository.findById(id)).thenReturn(java.util.Optional.of(foundBookReview));

        bookService.updateBookReview(id, updatedBookReview);

        verify(bookReviewRepository).save(foundBookReview);
    }
}
