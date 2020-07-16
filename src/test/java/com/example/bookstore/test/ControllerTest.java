package com.example.bookstore.test;

import com.example.bookstore.controller.BookstoreController;
import static org.hamcrest.Matchers.containsString;

import com.example.bookstore.objects.*;
import com.example.bookstore.repo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    BookRepository repository;

    @Autowired
    private BookstoreController controller;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void showBooks() throws Exception {
        Iterable<BookEntity> books = repository.findAll();

        ArrayList<String> strBooks = new ArrayList<>();
        for( BookEntity b: books) {
            strBooks.add(b.toString());
        }

        String newString = String.join(",", strBooks);
        newString = String.format("[%s]", newString);

        MvcResult res = this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(res.getResponse().getContentAsString(), newString);
    }

    @Test
	public void addNewBook() throws Exception {
        BookEntity newBook = new BookEntity("New Book");

        this.mockMvc.perform(post("/addBook")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newBook)))
              .andExpect(status().isOk());

        assertThat(repository.findByName("New Book").orElse(new BookEntity("noname")).equals(newBook));
	}

	@Test
	public void findById() throws Exception {
        Long id = Long.parseLong("3");
        MvcResult res = this.mockMvc.perform(get("/books/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(res.getResponse().getContentAsString(),
                repository.findById(id).orElse(new BookEntity("No name")).toString());
    }

	@Test
	public void findByRackId() throws Exception {
        String id = "10";
        Iterable<BookEntity> books = repository.findByRackId(id);

        ArrayList<String> strBooks = new ArrayList<>();
        for( BookEntity b: books) {
            strBooks.add(b.toString());
        }

        String newString = String.join(",", strBooks);
        newString = String.format("[%s]", newString);

        MvcResult res = this.mockMvc.perform(get("/books/rack/" + id))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(res.getResponse().getContentAsString(), newString);
    }

	@Test
	public void findByShelfId() throws Exception {
        Long id = Long.parseLong("8");
        Iterable<BookEntity> books = repository.findByShelfId(id);

        ArrayList<String> strBooks = new ArrayList<>();
        for( BookEntity b: books) {
            strBooks.add(b.toString());
        }

        String newString = String.join(",", strBooks);
        newString = String.format("[%s]", newString);

        MvcResult res = this.mockMvc.perform(get("/books/shelf/" + id))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(res.getResponse().getContentAsString(), newString);
    }

	@Test
	public void findByRackAndShelfId() throws Exception {
        String rackId = "10";
        String shelfId = "14";
        Iterable<BookEntity> books = repository.findByRackIdAndShelfId(rackId, shelfId);

        ArrayList<String> strBooks = new ArrayList<>();
        for( BookEntity b: books) {
            strBooks.add(b.toString());
        }

        String newString = String.join(",", strBooks);
        newString = String.format("[%s]", newString);

        MvcResult res = this.mockMvc.perform(get("/books/rack_shelf/" + rackId + "/" + shelfId))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(res.getResponse().getContentAsString(), newString);
    }

	@Test
	public void findByName() throws Exception {
        String name = "1984";
        MvcResult res = this.mockMvc.perform(get("/books/name/" + name)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(res.getResponse().getContentAsString(),
                repository.findByName(name).orElse(new BookEntity("No name")).toString());
    }

    @Test
    public void deleteBook() throws Exception {
        String id = "17";
        MvcResult resBefore = this.mockMvc.perform(get("/books/" + id))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(resBefore.getResponse().getContentAsString(),
                repository.findById(Long.parseLong(id)).orElse(new BookEntity("No name")).toString());

        this.mockMvc.perform(delete("/books/" + id))
        .andDo(print());
        MvcResult res = this.mockMvc.perform(get("/books/" + id))
                .andDo(print())
                .andExpect(status().isNotFound()).andReturn();
        assertEquals(res.getResponse().getStatus(), 404);
    }

    @Test
    public void changeBook() throws Exception {
        String id = "13";
        String newName = "The Hobbit";
        Long newShelfId = Long.parseLong("2");
        Integer newShelfLevel = 1;
        Long newRackId = Long.parseLong("1");
        MvcResult resBefore = this.mockMvc.perform(get("/books/" + id))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(resBefore.getResponse().getContentAsString(),
                repository.findById(Long.parseLong(id)).orElse(new BookEntity("No name")).toString());

        JSONObject obj = new JSONObject(resBefore.getResponse().getContentAsString());
        JSONObject shelf = obj.getJSONObject("shelf");
        JSONObject rack = shelf.getJSONObject("rack");
        rack.put("id", newRackId);
        shelf.put("rack", rack);
        shelf.put("level", newShelfLevel);
        shelf.put("id", newShelfId);
        obj.put("shelf", shelf);
        obj.put("name", newName);

        MvcResult res = this.mockMvc.perform(put("/books/" + id)
                .contentType(MediaType.APPLICATION_JSON).content(obj.toString()))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        MvcResult resAfter = this.mockMvc.perform(get("/books/" + id))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        BookEntity changedBook = repository.findById(Long.parseLong(id)).orElse(new BookEntity("noName"));

        assertEquals(changedBook.getName(), newName);
        assertEquals(changedBook.getShelf().getId(), newShelfId);
        assertEquals(changedBook.getShelf().getLevel(), newShelfLevel);
        assertEquals(changedBook.getShelf().getRack().getId(), newRackId);
    }

}
