package com.mpphw.springbootBackend.service;

import com.mpphw.springbootBackend.dto.Book;
import com.mpphw.springbootBackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks()
    {
        return this.bookRepository.getBooks();
    }

    public void addBook(Book newBook) throws Exception {
        this.bookRepository.addBook(newBook);
    }

    public Book getDetailsBook(String title) throws Exception {
        return this.bookRepository.getDetailsBook(title);
    }

    public void deleteBook(String title) throws Exception {
        this.bookRepository.deleteBook(title);
    }

    public void updateBook(String title, Book updatedBook) throws Exception {
        this.bookRepository.updateBook(title, updatedBook);
    }
}
