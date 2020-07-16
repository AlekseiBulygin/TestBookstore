package com.example.bookstore.test;

import com.example.bookstore.repo.BookRepository;
import com.example.bookstore.objects.*;
import com.example.bookstore.repo.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void testChangeBookData() {


    }

}