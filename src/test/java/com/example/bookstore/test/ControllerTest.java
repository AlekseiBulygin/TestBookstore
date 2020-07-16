package com.example.bookstore.test;

import com.example.bookstore.controller.BookstoreController;
import static org.hamcrest.Matchers.containsString;

import com.example.bookstore.objects.*;
import com.example.bookstore.repo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
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
import java.util.Iterator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    ShelfRepository shelfRepository;

    @Autowired
    RackRepository rackRepository;

    @Autowired
    private BookstoreController controller;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public void initRepo() {
        RackEntity rack1 = new RackEntity();
        RackEntity rack2 = new RackEntity();

        ShelfEntity shelf1 = new ShelfEntity(1);
        ShelfEntity shelf2 = new ShelfEntity(2);
        ShelfEntity shelf3 = new ShelfEntity(3);

        ShelfEntity shelf4 = new ShelfEntity(1);
        ShelfEntity shelf5 = new ShelfEntity(2);
        ShelfEntity shelf6 = new ShelfEntity(3);

        BookEntity book11 = new BookEntity("War and peace");
        BookEntity book12 = new BookEntity("Idiot");
        BookEntity book13 = new BookEntity("Anna Karenina");
        BookEntity book14 = new BookEntity("Crime and punishment");
        BookEntity book15 = new BookEntity("Eugene Onegin");

        book11.setShelf(shelf1);
        book12.setShelf(shelf1);
        book13.setShelf(shelf2);
        book14.setShelf(shelf2);
        book15.setShelf(shelf3);

        ArrayList<BookEntity> bookList1 = new ArrayList<>();
        ArrayList <BookEntity> bookList2 = new ArrayList<>();
        ArrayList <BookEntity> bookList3 = new ArrayList<>();
        bookList1.add(book11);
        bookList1.add(book12);
        bookList2.add(book13);
        bookList2.add(book14);
        bookList3.add(book15);

        shelf1.setBooks(bookList1);
        shelf2.setBooks(bookList2);
        shelf3.setBooks(bookList3);

        shelf1.setRack(rack1);
        shelf2.setRack(rack1);
        shelf3.setRack(rack1);

        ArrayList <ShelfEntity> shelvesList = new ArrayList<>();
        shelvesList.add(shelf1);
        shelvesList.add(shelf2);
        shelvesList.add(shelf3);
        rack1.setShelves(shelvesList);

        BookEntity book21 = new BookEntity("The Great Gatsby");
        BookEntity book22 = new BookEntity("The Lord of the Rings");
        BookEntity book23 = new BookEntity("1984");
        BookEntity book24 = new BookEntity("Dorian Grey");
        BookEntity book25 = new BookEntity("Fahrenheit 451");

        book21.setShelf(shelf4);
        book22.setShelf(shelf4);
        book23.setShelf(shelf5);
        book24.setShelf(shelf6);
        book25.setShelf(shelf6);

        ArrayList <BookEntity> bookList4 = new ArrayList<>();
        ArrayList <BookEntity> bookList5 = new ArrayList<>();
        ArrayList <BookEntity> bookList6 = new ArrayList<>();
        bookList4.add(book21);
        bookList4.add(book22);
        bookList5.add(book23);
        bookList6.add(book24);
        bookList6.add(book25);

        shelf4.setBooks(bookList4);
        shelf4.setRack(rack2);
        shelf5.setBooks(bookList5);
        shelf5.setRack(rack2);
        shelf6.setBooks(bookList6);
        shelf6.setRack(rack2);

        ArrayList <ShelfEntity> shelvesList2 = new ArrayList<>();
        shelvesList2.add(shelf4);
        shelvesList2.add(shelf5);
        shelvesList2.add(shelf6);

        rack2.setShelves(shelvesList2);

        rackRepository.save(rack1);
        shelfRepository.saveAll(shelvesList);
        repository.save(book11);
        repository.save(book12);
        repository.save(book13);
        repository.save(book14);
        repository.save(book15);

        rackRepository.save(rack2);
        shelfRepository.saveAll(shelvesList2);
        repository.save(book21);
        repository.save(book22);
        repository.save(book23);
        repository.save(book24);
        repository.save(book25);
    }

    @Test
    public void test() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void showBooks() throws Exception {
        Iterator<BookEntity> iterator = repository.findAll().iterator();
        ArrayList<BookEntity> books = new ArrayList<>();
        iterator.forEachRemaining(books::add);

        String newString = "[";

        for( BookEntity b: books) {
            newString = newString.concat(b.toString() + ",");
        }
        MvcResult res = this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        assertEquals(res.getResponse().getContentAsString(), newString.substring(0, newString.length() - 1)+"]");
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
        this.mockMvc.perform(get("/books/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().json("{\"id\":3,\"name\":\"War and peace\",\"shelf\":{\"id\":2,\"level\":1,\"rack\":{\"id\":1}}}"));
	}

	@Test
	public void findByRackId() throws Exception {
        MvcResult res = this.mockMvc.perform(get("/books/rack/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(content().("{\"id\":3,\"name\":\"War and peace\",\"shelf\":{\"id\":2,\"level\":1,\"rack\":{\"id\":1}}}"));
                .andReturn();


	}

}
