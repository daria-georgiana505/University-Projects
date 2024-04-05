package com.mpphw.springbootBackend.repository;

import com.mpphw.springbootBackend.dto.Book;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class BookRepository {

    private List<Book> listOfBooks;

    public BookRepository() {
        this.listOfBooks = new ArrayList<>();
    }

    @PostConstruct
    public void populateRepository()
    {
        this.listOfBooks.add(new Book("Percy Jackson and The Lightning Thief", "Rick Riordan", "Fantasy", 384));
        this.listOfBooks.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "Fantasy", 352));
        this.listOfBooks.add(new Book("The Fall Of The Roman Empire", "Peter Heather", "History", 576));
        this.listOfBooks.add(new Book("Figure Drawing without a Model", "Ron Tiner", "Art", 160));
        this.listOfBooks.add(new Book("The Power of Regret", "Daniel H. Pink", "Psychology", 256));
        this.listOfBooks.add(new Book("Haikyu!! - Volume 1", "Haruichi Furudate", "Manga", 192));
        this.listOfBooks.add(new Book("Dune", "Frank Herbert", "SF", 896));
        this.listOfBooks.add(new Book("Demon Slayer: Kimetsu no Yaiba - Volume 1", "Koyoharu Gotouge", "Manga", 192));
        this.listOfBooks.add(new Book("Secret Wars", "Jonathan Hickman", "Comic", 312));
        this.listOfBooks.add(new Book("Miles Morales: Spider-man", "Brian Michael Bendis", "Comic", 248));
        this.listOfBooks.add(new Book("The Lost Hero", "Rick Riordan", "Fantasy", 576));
    }

    public List<Book> getBooks()
    {
        return this.listOfBooks;
    }

    public void addBook(Book newBook) throws Exception {
        boolean search = this.listOfBooks.stream()
                .noneMatch(p -> Objects.equals(p.getTitle(), newBook.getTitle()));
        if(!search)
            throw new Exception("Book with the same title already in the table");

        boolean result = this.listOfBooks.add(newBook);
        if(!result)
            throw new Exception("Add could not be performed");
    }

    public Book getDetailsBook(String title) throws Exception {
        return this.listOfBooks.stream()
                .filter(p -> Objects.equals(p.getTitle(), title))
                .findFirst()
                .orElseThrow(() -> new Exception("Book title not found"));
    }

    public void deleteBook(String title) throws Exception {
        this.listOfBooks.stream()
                .filter(p -> Objects.equals(p.getTitle(), title))
                .findFirst()
                .orElseThrow(() -> new Exception("Book title not found"));

        boolean result = this.listOfBooks.removeIf(p -> Objects.equals(p.getTitle(), title));
        if(!result)
            throw new Exception("Delete could not be performed");
    }

    public void updateBook(String title, Book updatedBook) throws Exception {
        this.listOfBooks.stream()
                .filter(p -> Objects.equals(p.getTitle(), title))
                .findFirst()
                .orElseThrow(() -> new Exception("Book title not found"));

        boolean search = this.listOfBooks.stream()
                .noneMatch(p -> (Objects.equals(p.getTitle(), updatedBook.getTitle()) && !Objects.equals(p.getTitle(), title)));
        if(!search)
            throw new Exception("Book with the same title already in the table");

        this.listOfBooks = this.listOfBooks.stream()
            .map(p -> {
                if (Objects.equals(p.getTitle(), title))
                    return updatedBook;
                else
                    return p;
            })
            .collect(Collectors.toList());
    }
}
