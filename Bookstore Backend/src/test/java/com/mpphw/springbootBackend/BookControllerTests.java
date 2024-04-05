package com.mpphw.springbootBackend;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpphw.springbootBackend.dto.Book;
import com.mpphw.springbootBackend.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetBooks() throws Exception {
        when(bookService.getBooks()).thenReturn(Arrays.asList(
                new Book("First Title", "First Author", "First Genre", 100),
                new Book("Second Title", "Second Author", "Second Genre", 200),
                new Book("Third Title", "Third Author", "Third Genre", 300)));

        mockMvc.perform(MockMvcRequestBuilders.get("/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("First Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("First Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].genre").value("First Genre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nr_pages").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Second Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Second Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].genre").value("Second Genre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nr_pages").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].title").value("Third Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].author").value("Third Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].genre").value("Third Genre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].nr_pages").value(300));
    }

    @Test
    public void testAddBook() throws Exception {
        Book newBook = new Book("Title", "Author", "Genre", 200);

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newBook)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Add operation was done successfully"));
    }

    @Test
    public void testGetDetailsBook() throws Exception {
        String title = "Title";

        when(bookService.getDetailsBook(title)).thenReturn(new Book("Title", "Author", "Genre", 200));

        mockMvc.perform(MockMvcRequestBuilders.get("/details/{title}", title))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("Genre"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nr_pages").value(200));
    }

    @Test
    public void testDeleteBook() throws Exception {
        String title = "Title";

        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/{title}", title))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Delete operation was done successfully"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        String title = "Title";
        Book updatedBook = new Book("Updated Title", "Updated Author", "Updated Genre", 250);

        mockMvc.perform(MockMvcRequestBuilders.put("/update/{title}", title)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedBook)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Update operation was done successfully"));
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
