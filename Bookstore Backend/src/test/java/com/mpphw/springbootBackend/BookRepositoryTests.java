package com.mpphw.springbootBackend;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mpphw.springbootBackend.repository.BookRepository;
import com.mpphw.springbootBackend.dto.Book;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookRepositoryTests {

    private BookRepository bookRepository;
    private List<Book> initialBooks;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepository();

        initialBooks = new ArrayList<>();
        initialBooks.add(new Book("First Title", "First Author", "First Genre", 100));
        initialBooks.add(new Book("Second Title", "Second Author", "Second Genre", 200));
        initialBooks.add(new Book("Third Title", "Third Author", "Third Genre", 300));

        for (Book book : initialBooks) {
            bookRepository.getBooks().add(book);
        }
    }

    @Test
    void testGetBooks() {
        List<Book> actualBooks = bookRepository.getBooks();

        assertNotNull(actualBooks);
        assertEquals(3, actualBooks.size());
        assertEquals(initialBooks, actualBooks);
    }

    @Test
    void testAddBook() throws Exception {
        Book newBook = new Book("New Title", "New Author", "New Genre", 300);
        bookRepository.addBook(newBook);
        assertTrue(bookRepository.getBooks().contains(newBook));
    }

    @Test
    void testGetDetailsBook() throws Exception {
        Book existingBook = initialBooks.get(0);
        Book retrievedBook = bookRepository.getDetailsBook(existingBook.getTitle());
        assertEquals(existingBook, retrievedBook);
    }

    @Test
    void testDeleteBook() throws Exception {
        Book bookToDelete = initialBooks.get(0);
        bookRepository.deleteBook(bookToDelete.getTitle());
        assertFalse(bookRepository.getBooks().contains(bookToDelete));
    }

    @Test
    void testUpdateBook() throws Exception {
        Book bookToUpdate = initialBooks.get(0);
        Book updatedBook = new Book("Updated Title", "Updated Author", "Updated Genre", 400);
        bookRepository.updateBook(bookToUpdate.getTitle(), updatedBook);
        Book retrievedBook = bookRepository.getDetailsBook(updatedBook.getTitle());
        assertEquals(updatedBook, retrievedBook);
    }
}

