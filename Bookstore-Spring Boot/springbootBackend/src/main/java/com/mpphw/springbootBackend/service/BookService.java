package com.mpphw.springbootBackend.service;

import com.mpphw.springbootBackend.model.Book;
import com.mpphw.springbootBackend.model.BookReview;
import com.mpphw.springbootBackend.repository.BookRepository;
import com.mpphw.springbootBackend.repository.BookReviewRepository;
import com.mpphw.springbootBackend.sanitizers.InputSanitizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;
    private BookReviewRepository bookReviewRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BookReviewRepository bookReviewRepository) {
        this.bookRepository = bookRepository;
        this.bookReviewRepository = bookReviewRepository;
    }

    public List<Book> getBooks()
    {
        return this.bookRepository.findAll();
    }

    public void addBook(Book newBook) throws Exception {
        String bookTitle = newBook.getTitle();
        bookTitle = InputSanitizer.sanitizeHtml(bookTitle);
        bookTitle = InputSanitizer.sanitizeCommandLine(bookTitle);

        String bookAuthor = newBook.getAuthor();
        bookAuthor = InputSanitizer.sanitizeHtml(bookAuthor);
        bookAuthor = InputSanitizer.sanitizeCommandLine(bookAuthor);

        String bookGenre = newBook.getGenre();
        bookGenre = InputSanitizer.sanitizeHtml(bookGenre);
        bookGenre = InputSanitizer.sanitizeCommandLine(bookGenre);

        if(!this.bookRepository.existsById(bookTitle))
        {
            this.bookRepository.save(newBook);
        }
        else
            throw new Exception("Book title already in the database");
    }

    public Book getDetailsBook(String title) throws Exception {
        title = InputSanitizer.sanitizeHtml(title);
        title = InputSanitizer.sanitizeCommandLine(title);

        return this.bookRepository.findById(title).orElseThrow(() -> new Exception("Book title not found"));
    }

    public void deleteBook(String title) throws Exception {
        title = InputSanitizer.sanitizeHtml(title);
        title = InputSanitizer.sanitizeCommandLine(title);

        if(this.bookRepository.existsById(title))
        {
            this.bookRepository.deleteById(title);
        }
        else
            throw  new Exception("Book title not found");
    }

    public void updateBook(String title, Book updatedBook) throws Exception {
        title = InputSanitizer.sanitizeHtml(title);
        title = InputSanitizer.sanitizeCommandLine(title);

        String updatedBookTitle = updatedBook.getTitle();
        updatedBookTitle = InputSanitizer.sanitizeHtml(updatedBookTitle);
        updatedBookTitle = InputSanitizer.sanitizeCommandLine(updatedBookTitle);

        String updatedBookAuthor = updatedBook.getAuthor();
        updatedBookAuthor = InputSanitizer.sanitizeHtml(updatedBookAuthor);
        updatedBookAuthor = InputSanitizer.sanitizeCommandLine(updatedBookAuthor);

        String updatedBookGenre = updatedBook.getGenre();
        updatedBookGenre = InputSanitizer.sanitizeHtml(updatedBookGenre);
        updatedBookGenre = InputSanitizer.sanitizeCommandLine(updatedBookGenre);

        if(!this.bookRepository.existsById(title))
            throw new Exception("Book title not found");

        if(this.bookRepository.existsById(updatedBookTitle) && !title.equals(updatedBookTitle))
            throw new Exception("The updated title of the book is already in the table");

        Book newBook = new Book(updatedBookTitle, updatedBookAuthor, updatedBookGenre, updatedBook.getNrPages());
        newBook.setReviews(this.bookRepository.findById(title).get().getReviews());
        this.bookRepository.deleteById(title);
        this.bookRepository.save(newBook);
    }

    public List<BookReview> getBookReviews(String bookTitle) throws Exception
    {
        bookTitle = InputSanitizer.sanitizeHtml(bookTitle);
        bookTitle = InputSanitizer.sanitizeCommandLine(bookTitle);

        if(this.bookRepository.existsById(bookTitle))
        {
            Book book = this.bookRepository.findById(bookTitle).get();
            return book.getReviews();
        }
        else
            throw new Exception("Book title not found");
    }

    public void addBookReview(String bookTitle, BookReview newBookReview) throws Exception {
        bookTitle = InputSanitizer.sanitizeHtml(bookTitle);
        bookTitle = InputSanitizer.sanitizeCommandLine(bookTitle);

        String bookReviewReviewer = newBookReview.getReviewerName();
        bookReviewReviewer = InputSanitizer.sanitizeHtml(bookReviewReviewer);
        bookReviewReviewer = InputSanitizer.sanitizeCommandLine(bookReviewReviewer);

        String bookReviewText = newBookReview.getReview();
        bookReviewText = InputSanitizer.sanitizeHtml(bookReviewText);
        bookReviewText = InputSanitizer.sanitizeCommandLine(bookReviewText);

        if(!this.bookRepository.existsById(bookTitle))
            throw new Exception("Book title not found");
        newBookReview.setBook(this.bookRepository.findById(bookTitle).get());

        this.bookReviewRepository.save(newBookReview);
    }

    public BookReview getDetailsBookReview(int id) throws Exception {
        return this.bookReviewRepository.findById(id).orElseThrow(() -> new Exception("Book review not found"));
    }

    public void deleteBookReview(int id) throws Exception {
        if(this.bookReviewRepository.existsById(id))
        {
            this.bookReviewRepository.deleteById(id);
        }
        else
            throw new Exception("Book review not found");
    }

    public void updateBookReview(int id, BookReview updatedBookReview) throws Exception {
        String bookReviewReviewer = updatedBookReview.getReviewerName();
        bookReviewReviewer = InputSanitizer.sanitizeHtml(bookReviewReviewer);
        bookReviewReviewer = InputSanitizer.sanitizeCommandLine(bookReviewReviewer);

        String bookReviewText = updatedBookReview.getReview();
        bookReviewText = InputSanitizer.sanitizeHtml(bookReviewText);
        bookReviewText = InputSanitizer.sanitizeCommandLine(bookReviewText);

        BookReview foundBookReview = this.bookReviewRepository.findById(id).orElseThrow(() -> new Exception("Book review not found"));

        foundBookReview.setReviewerName(bookReviewReviewer);
        foundBookReview.setReview(bookReviewText);

        this.bookReviewRepository.save(foundBookReview);
    }
}
