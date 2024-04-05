package com.mpphw.springbootBackend.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class Book {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    @Pattern(regexp = "^[a-zA-Z .]+$", message = "Author must contain only letters and spaces")
    private String author;

    @NotBlank(message = "Genre is required")
    @Pattern(regexp = "^[a-zA-Z .]+$", message = "Genre must contain only letters and spaces")
    private String genre;

    @NotNull(message = "Number of pages is required")
    @Positive(message = "Number of pages must be a positive number")
    private int nr_pages;

    public Book(String title, String author, String genre, int nr_pages) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.nr_pages = nr_pages;
    }

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
}
