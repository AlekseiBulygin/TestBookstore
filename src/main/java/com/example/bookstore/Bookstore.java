package com.example.bookstore;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.objects.*;
import com.example.bookstore.repo.*;
import com.example.bookstore.service.BookServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication(scanBasePackages = {"com.example.bookstore"})
public class Bookstore implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Bookstore.class);

    @Autowired
    BookServiceImpl bookService;

    @Autowired
    BookRepository repository;

    @Autowired
    RackRepository rackRepository;

    @Autowired
    ShelfRepository shelfRepository;

    public static void main(String... args) {
        SpringApplication.run(Bookstore.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        // here you can set any configurations for mapper
        return mapper;
    }
    @Override
    public void run(String ... args) {
        RackEntity rack1 = new RackEntity();
        RackEntity rack2 = new RackEntity();

        ShelfEntity shelf1 = new ShelfEntity(1L);
        ShelfEntity shelf2 = new ShelfEntity(2L);
        ShelfEntity shelf3 = new ShelfEntity(3L);

        ShelfEntity shelf4 = new ShelfEntity(1L);
        ShelfEntity shelf5 = new ShelfEntity(2L);
        ShelfEntity shelf6 = new ShelfEntity(3L);

        BookDTO book11 = new BookDTO();
        BookDTO book12 = new BookDTO();
        BookDTO book13 = new BookDTO();
        BookDTO book14 = new BookDTO();
        BookDTO book15 = new BookDTO();

        book11.setName("War and peace");
        book12.setName("Idiot");
        book13.setName("Anna Karenina");
        book14.setName("Crime and punishment");
        book15.setName("Eugene Onegin");

        book11.setShelfLevel(1L);
        book12.setShelfLevel(2L);
        book13.setShelfLevel(3L);
        book14.setShelfLevel(1L);
        book15.setShelfLevel(2L);

        book11.setRackId(1L);
        book12.setRackId(1L);
        book13.setRackId(1L);
        book14.setRackId(1L);
        book15.setRackId(1L);

        shelf1.setRack(rack1);
        shelf2.setRack(rack1);
        shelf3.setRack(rack1);

        ArrayList <ShelfEntity> shelvesList = new ArrayList<>();
        shelvesList.add(shelf1);
        shelvesList.add(shelf2);
        shelvesList.add(shelf3);
        rack1.setShelves(shelvesList);

        rackRepository.save(rack1);
        shelfRepository.saveAll(shelvesList);
        bookService.save(book11);
        bookService.save(book12);
        bookService.save(book13);
        bookService.save(book14);
        bookService.save(book15);

    }

}
