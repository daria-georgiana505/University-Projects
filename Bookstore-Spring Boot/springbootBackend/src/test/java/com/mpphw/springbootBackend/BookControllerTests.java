package com.mpphw.springbootBackend;

import static org.mockito.Mockito.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpphw.springbootBackend.model.Book;
import com.mpphw.springbootBackend.model.BookReview;
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
import java.util.List;

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

    @Test
    public void testGetBookReviews() throws Exception {
        List<BookReview> expectedBookReviews = Arrays.asList(
                new BookReview("user1", "Amazing book"),
                new BookReview("user5", "I didn't enjoy it that much..."),
                new BookReview("user2", "I loved it!!!"));
        expectedBookReviews.get(0).setId(100);
        expectedBookReviews.get(1).setId(200);
        expectedBookReviews.get(2).setId(300);
        String title = "Title";

        when(bookService.getBookReviews(title)).thenReturn(expectedBookReviews);

        mockMvc.perform(MockMvcRequestBuilders.get("/allReviews/{title}", title))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reviewerName").value("user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].review").value("Amazing book"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].reviewerName").value("user5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].review").value("I didn't enjoy it that much..."))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].reviewerName").value("user2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].review").value("I loved it!!!"));
    }

    @Test
    public void testGetDetailsBookReview() throws Exception {
        int id = 100;
        BookReview bookReview = new BookReview("user0", "Not that great...");
        bookReview.setId(id);

        when(bookService.getDetailsBookReview(id)).thenReturn(bookReview);

        mockMvc.perform(MockMvcRequestBuilders.get("/detailsReview/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviewerName").value("user0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.review").value("Not that great..."));
    }

    @Test
    public void testAddBookReview() throws Exception {
        String title = "Title";
        BookReview bookReview = new BookReview("user0", "Not that great...");

        doNothing().when(bookService).addBookReview(any(String.class), any(BookReview.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/addReview/{title}", title)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonStringWithoutId(bookReview)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Add operation was done successfully"));
    }

    @Test
    public void testDeleteBookReview() throws Exception {
        Integer id = 100;

        doNothing().when(bookService).deleteBookReview(any(Integer.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/deleteReview/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Delete operation was done successfully"));
    }

    @Test
    public void testUpdateBookReview() throws Exception {
        Integer id = 100;
        BookReview bookReview = new BookReview("user0", "Not that great...");
        bookReview.setId(id);

        mockMvc.perform(MockMvcRequestBuilders.put("/updateReview/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonStringWithoutId(bookReview)))
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

    private String asJsonStringWithoutId(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.addMixIn(obj.getClass(), IgnoreIdMixin.class);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    abstract class IgnoreIdMixin {
        @JsonIgnore
        abstract Integer getId();
    }

}
