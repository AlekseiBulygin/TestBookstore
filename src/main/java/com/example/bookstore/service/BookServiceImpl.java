package com.example.bookstore.service;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.exceptions.*;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.objects.BookEntity;
import com.example.bookstore.repo.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository repo, ShelfRepository shelfRepository, BookMapper mapper) {
        this.bookRepository = repo;
        this.shelfRepository = shelfRepository;
        this.bookMapper = mapper;
    }

    public void validateNewBookDto(BookDTO book) {
        if (isNull(book) || !isNull(book.getId()) || isNull(book.getName()) ||
                isNull(book.getShelfLevel()) || isNull(book.getRackId())) {
            throw new RuntimeException("Wrong BookDTO parameters");
        }
    }

    @Override
    public BookDTO save(BookDTO bookDto) {
        validateNewBookDto(bookDto);
        BookEntity newBook = bookMapper.toEntity(bookDto);
        newBook.setShelf(shelfRepository.findByRackIdAndLevel(bookDto.getRackId(), bookDto.getShelfLevel())
                .orElseThrow(() -> new BookNotFoundException(bookDto.getName())));
        return bookMapper.toDto(bookRepository.save(newBook));
    }

    @Override
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public BookDTO findByName(String name) {
        return bookMapper.toDto(bookRepository.findByName(name).orElseThrow(() -> new BookNotFoundException(name)));
    }

    @Override
    public BookDTO findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString())));
    }

    @Override
    public List<BookDTO> findByRackId(Long id) {
        return bookRepository.findByRackId(id).stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByShelfId(Long id) {
        return bookRepository.findByShelfId(id).stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByRackIdAndShelfId(Long rackId, Long shelfId) {
        return bookRepository.findByRackIdAndShelfId(rackId, shelfId).stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDTO replaceBook(BookDTO bookDto, Long id) {
        BookEntity book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id.toString()));
        book.setName(bookDto.getName());
        book.setShelf(shelfRepository.findByRackIdAndLevel(bookDto.getRackId(), bookDto.getShelfLevel())
                .orElseThrow(() -> new BookNotFoundException(bookDto.getName())));
        return bookMapper.toDto(bookRepository.save(book));
    }
}
