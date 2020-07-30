package com.example.bookstore.test;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    private BookDTO currentBook;

    @MockBean
    BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        currentBook = new BookDTO();
        currentBook.setName("Book Name");
        currentBook.setId(3L);
        currentBook.setShelfLevel(2L);
        currentBook.setRackId(1L);
    }

    @Test
    public void whenRequestFindAll_thenStatusOk() throws Exception {
        Mockito.when(bookService.findAll()).thenReturn(Collections.singletonList(currentBook));
        mockMvc.perform(get("/bookstore"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
	public void givenNewBook_whenRequestSaveNewBook_thenStatusOk() throws Exception {
        Mockito.when(bookService.save(currentBook)).thenReturn(currentBook);
        mockMvc.perform(post("/bookstore/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(currentBook.toString()))
                .andDo(print())
                .andExpect(status().isOk());
	}

	@Test
	public void givenBookName_whenRequestFindByName_thenStatusOk() throws Exception {
        String name = "Book Name";
        Mockito.when(bookService.findByName(name)).thenReturn(currentBook);

        mockMvc.perform(get("/bookstore/books")
                .param("name", name)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

	@Test
	public void givenBookId_whenRequestFindById_thenStatusOk() throws Exception {
        Long bookId = 3L;
        Mockito.when(bookService.findById(bookId)).thenReturn(currentBook);

        mockMvc.perform(get("/bookstore/books")
                .param("id", bookId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

	@Test
	public void givenRackId_whenRequestFindBooksByRackId_thenStatusOk() throws Exception {
        Long rackId = 1L;
        Mockito.when(bookService.findByRackId(rackId)).thenReturn(Collections.singletonList(currentBook));

        mockMvc.perform(get("/bookstore/books")
                .param("rackId", rackId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

	@Test
	public void givenShelfLevel_whenRequestFindBooksByShelfLevel_thenStatusOk() throws Exception {
        Long shelfLevel = 2L;
        Mockito.when(bookService.findByShelfLevel(shelfLevel)).thenReturn(Collections.singletonList(currentBook));

        mockMvc.perform(get("/bookstore/books")
                .param("shelfLevel", shelfLevel.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenBookId_whenRequestDeleteBook_thenStatusOk() throws Exception {
        Long bookId = 3L;
        Mockito.doNothing().when(bookService).deleteById(bookId);
        mockMvc.perform(delete("/bookstore/books/" + bookId))
                .andDo(print());
    }

    @Test
    public void givenBookId_whenRequestReplaceBook_thenStatusOk() throws Exception {
        Long bookId = 3L;

        String newName = "The Hobbit";
        Long newShelfLevel = 3L;
        Long newRackId = 2L;
        BookDTO newBook = new BookDTO();
        newBook.setName(newName);
        newBook.setShelfLevel(newShelfLevel);
        newBook.setRackId(newRackId);

        Mockito.when(bookService.replaceBook(newBook, bookId)).thenReturn(newBook);

        mockMvc.perform(put("/bookstore/books/" + bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newBook.toString()))
                .andDo(print()).andExpect(status().isOk()).andReturn();
    }
}
