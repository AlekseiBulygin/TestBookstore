package com.example.bookstore.test;

import com.example.bookstore.repo.BookRepository;
import com.example.bookstore.objects.*;
import com.example.bookstore.repo.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private RackRepository rackRepository;

    @Test
    public void testFindByName() {
        BookEntity book = new BookEntity("Oblomov");
        repository.save(book);
        BookEntity newBook = repository.findByName("Idiot").orElse(new BookEntity("noName"));
        assertThat(newBook.getName().equals("Idiot"));
    }

    @Test
	public void findAll() throws Exception {
        Iterator<BookEntity> iterator = repository.findAll().iterator();
        ArrayList<BookEntity> books = new ArrayList<>();
        iterator.forEachRemaining(books::add);
        assertEquals(books.size(), 10);
	}

    @Test
	public void findByRackId() throws Exception {
        assertEquals(repository.findByRackId("10").size(), 5);
	}

	@Test
	public void findByShelfId() throws Exception {
        Long id = Long.parseLong("2");
        assertEquals(repository.findByShelfId(id).size(), 2);
	}

	@Test
	public void findByRackAndShelfId() throws Exception {
        assertEquals(repository.findByRackIdAndShelfId("1", "5").size(), 2);
	}
}