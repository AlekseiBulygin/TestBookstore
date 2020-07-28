package com.example.bookstore.test;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.objects.*;
import com.example.bookstore.service.BookService;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {

    @Before
    public void before() {
        System.out.println("ho-ho-ho");
    }

    @Autowired
    private BookService bookService;

    @Test
    public void findByName() {
        BookDTO newBook = bookService.findByName("Idiot");
        assertThat(newBook.getName().equals("Idiot"));
    }

    @Test
	public void findAll() throws Exception {
        List<BookDTO> books = bookService.findAll();
        assertEquals(books.size(), 10);
	}

    @Test
	public void findByRackId() throws Exception {
        assertEquals(bookService.findByRackId(10L).size(), 5);
	}

//	@Test
//	public void findByShelfId() throws Exception {
//        assertEquals(bookService.findByShelfId(2L).size(), 2);
//	}
//
//	@Test
//	public void findByRackAndShelfId() throws Exception {
//        assertEquals(bookService.findByRackIdAndShelfId(1L, 5L).size(), 2);
//	}
}
