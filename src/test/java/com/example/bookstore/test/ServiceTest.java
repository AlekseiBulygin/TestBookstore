package com.example.bookstore.test;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.exceptions.BookNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.objects.*;
import com.example.bookstore.repo.*;
import com.example.bookstore.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    private BookEntity currentBook;
    private ShelfEntity shelf;
    private RackEntity rack;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private ShelfRepository shelfRepository;

    @MockBean
    private RackRepository rackRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    @Before
    public void setUp() {
        rack = new RackEntity();
        rack.setId(1L);
        shelf = new ShelfEntity(1L);
        shelf.setRack(rack);
        shelf.setId(2L);
        String name = "New Book";
        Long id = 3L;
        currentBook = new BookEntity(name);
        currentBook.setId(id);
    }

    @Test
    public void givenNewBook_whenSaveNewBook_thenSaveNewBook() {
        Mockito.when(shelfRepository.findByRackIdAndLevel(rack.getId(), shelf.getLevel())).thenReturn(Optional.of(shelf));

        BookDTO bookDto = new BookDTO();
        bookDto.setName("New Book");
        bookDto.setShelfLevel(1L);
        bookDto.setRackId(1L);

        BookEntity book = bookMapper.toEntity(bookDto);
        book.setShelf(shelf);

        Mockito.when(bookRepository.save(book)).thenReturn(book);

        BookDTO savedBook = bookService.save(bookDto);

        assertEquals(bookDto.getName(), savedBook.getName());
        assertEquals(bookDto.getShelfLevel(), savedBook.getShelfLevel());

    }

    @Test
    public void givenExistingBook_whenFindAll_thenFoundExistingBooks() {
        List<BookEntity> books = Collections.singletonList(currentBook);
        Mockito.when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> foundBooks = bookService.findAll();
        assertEquals(1, foundBooks.size());
    }

    @Test
    public void givenExistingBook_whenFindBookByName_thenFoundBook() {
        BookDTO book = bookMapper.toDto(currentBook);
        Mockito.when(bookRepository.findByName(currentBook.getName())).thenReturn(Optional.of(currentBook));
        BookDTO foundBook = bookService.findByName("New Book");

        assertEquals(book.getName(), foundBook.getName());

    }

    @Test(expected = BookNotFoundException.class)
    public void givenNotExistingBook_whenFindBookByName_thenBookNotFoundException() {
        String noName = "Not exist book";
        Mockito.when(bookRepository.findByName(noName)).thenReturn(Optional.empty());
        BookDTO foundBook = bookService.findByName(noName);
    }

    @Test
    public void givenExistingBook_whenFindBookById_thenFoundBook() {
        BookDTO book = bookMapper.toDto(currentBook);
        Mockito.when(bookRepository.findById(currentBook.getId())).thenReturn(Optional.of(currentBook));
        BookDTO foundBook = bookService.findById(currentBook.getId());

        assertEquals(book.getName(), foundBook.getName());
        assertEquals(book.getId(), foundBook.getId());
    }

    @Test(expected = BookNotFoundException.class)
    public void givenNotExistingBook_whenFindBookById_thenBookNotFoundException() {
        Long noId = 999L;
        Mockito.when(bookRepository.findById(noId)).thenReturn(Optional.empty());
        BookDTO foundBook = bookService.findById(noId);
    }

    @Test
    public void givenRackId_whenFindBooksByRackId_thenFoundBookList() {
        Long rackId = 1L;
        List<BookEntity> books = Collections.singletonList(currentBook);

        Mockito.when(bookRepository.findByRackId(rackId)).thenReturn(books);
        List<BookDTO> foundBook = bookService.findByRackId(rackId);

        assertEquals(1, foundBook.size());
    }

    @Test
    public void givenNotExistedRackId_whenFindBooksByRackId_thenFoundEmptyBookList() {
        Long rackId = 999L;
        List<BookEntity> books = Collections.emptyList();

        Mockito.when(bookRepository.findByRackId(rackId)).thenReturn(books);
        List<BookDTO> foundBook = bookService.findByRackId(rackId);

        assertEquals(0, foundBook.size());
    }

    @Test
    public void givenShelfLevel_whenFindBooksByShelfLevel_thenFoundBookList() {
        Long shelfLevel = 1L;
        List<BookEntity> books = Collections.singletonList(currentBook);

        Mockito.when(bookRepository.findByShelfLevel(shelfLevel)).thenReturn(books);
        List<BookDTO> foundBook = bookService.findByShelfLevel(shelfLevel);

        assertEquals(1, foundBook.size());
    }

    @Test
    public void givenNotExistedShelfLevel_whenFindBooksByShelfLevel_thenFoundEmptyBookList() {
        Long shelfLevel = 999L;
        List<BookEntity> books = Collections.emptyList();

        Mockito.when(bookRepository.findByShelfLevel(shelfLevel)).thenReturn(books);
        List<BookDTO> foundBook = bookService.findByShelfLevel(shelfLevel);

        assertEquals(0, foundBook.size());
    }
}
