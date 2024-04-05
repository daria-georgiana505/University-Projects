package com.mpphw.springbootBackend.controller;

import com.mpphw.springbootBackend.dto.Book;
import com.mpphw.springbootBackend.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

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
}
