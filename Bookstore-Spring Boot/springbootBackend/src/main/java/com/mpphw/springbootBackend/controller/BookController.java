package com.mpphw.springbootBackend.controller;

import com.mpphw.springbootBackend.model.Book;
import com.mpphw.springbootBackend.model.BookReview;
import com.mpphw.springbootBackend.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<Object> getBooks()
    {
        return new ResponseEntity<>(this.bookService.getBooks(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addBook(@Valid @RequestBody Book addedBook, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseBody = new HashMap<>();

        if (bindingResult.hasErrors()) {
            responseBody.put("error", "Validation failed");
            responseBody.put("details", bindingResult.getAllErrors());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        this.bookService.addBook(addedBook);
        responseBody.put("message", "Add operation was done successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/details/{title}")
    public ResponseEntity<Object> getDetailsBook(@PathVariable(value = "title") String title) throws Exception {
        return new ResponseEntity<>(this.bookService.getDetailsBook(title), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{title}")
    public ResponseEntity<Object> deleteBook(@PathVariable(value = "title") String title) throws Exception {
        Map<String, Object> responseBody = new HashMap<>();
        this.bookService.deleteBook(title);
        responseBody.put("message", "Delete operation was done successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/update/{title}")
    public ResponseEntity<Object> updateBook(@PathVariable(value = "title") String title, @Valid @RequestBody Book updatedBook, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseBody = new HashMap<>();

        if (bindingResult.hasErrors()) {
            responseBody.put("error", "Validation failed");
            responseBody.put("details", bindingResult.getAllErrors());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        this.bookService.updateBook(title, updatedBook);
        responseBody.put("message", "Update operation was done successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/allReviews/{title}")
    public ResponseEntity<Object> getBookReviews(@PathVariable(value = "title") String title) throws Exception {
        return new ResponseEntity<>(this.bookService.getBookReviews(title), HttpStatus.OK);
    }

    @PostMapping("/addReview/{title}")
    public ResponseEntity<Object> addBookReview(@PathVariable(value = "title") String title, @Valid @RequestBody BookReview addedBookReview, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseBody = new HashMap<>();

        if (bindingResult.hasErrors()) {
            responseBody.put("error", "Validation failed");
            responseBody.put("details", bindingResult.getAllErrors());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        this.bookService.addBookReview(title, addedBookReview);
        responseBody.put("message", "Add operation was done successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/detailsReview/{id}")
    public ResponseEntity<Object> getDetailsBookReview(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<>(this.bookService.getDetailsBookReview(id), HttpStatus.OK);
    }

    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<Object> deleteBookReview(@PathVariable(value = "id") Integer id) throws Exception {
        Map<String, Object> responseBody = new HashMap<>();
        this.bookService.deleteBookReview(id);
        responseBody.put("message", "Delete operation was done successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PutMapping("/updateReview/{id}")
    public ResponseEntity<Object> updateBookReview(@PathVariable(value = "id") Integer id, @Valid @RequestBody BookReview updatedBookReview, BindingResult bindingResult) throws Exception {
        Map<String, Object> responseBody = new HashMap<>();

        if (bindingResult.hasErrors()) {
            responseBody.put("error", "Validation failed");
            responseBody.put("details", bindingResult.getAllErrors());
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }

        this.bookService.updateBookReview(id, updatedBookReview);
        responseBody.put("message", "Update operation was done successfully");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
