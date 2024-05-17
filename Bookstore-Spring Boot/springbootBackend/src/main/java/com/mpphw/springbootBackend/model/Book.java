package com.mpphw.springbootBackend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.List;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @NotBlank(message = "Title is required")
    @Column(name = "title", unique = true)
    private String title;

    @NotBlank(message = "Author is required")
    @Pattern(regexp = "^[a-zA-Z .]+$", message = "Author must contain only letters and spaces")
    @Column(name = "author")
    private String author;

    @NotBlank(message = "Genre is required")
    @Pattern(regexp = "^[a-zA-Z .]+$", message = "Genre must contain only letters and spaces")
    @Column(name = "genre")
    private String genre;

    @NotNull(message = "Number of pages is required")
    @Positive(message = "Number of pages must be a positive number")
    @Column(name = "nr_pages")
    private int nr_pages;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookReview> reviews;

    public Book(String title, String author, String genre, int nr_pages) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.nr_pages = nr_pages;
    }

    public Book() { }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @JsonProperty("nr_pages")
    public int getNrPages() {
        return nr_pages;
    }

    @JsonProperty("nr_pages")
    public void setNrPages(int nr_pages) {
        this.nr_pages = nr_pages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", nr_pages=" + nr_pages +
                '}';
    }

    public List<BookReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReview> reviews) {
        this.reviews = reviews;
    }
}
