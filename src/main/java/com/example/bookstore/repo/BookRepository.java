package com.example.bookstore.repo;

import java.util.*;

import com.example.bookstore.objects.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long> {

    Optional<BookEntity> findByName(String name);

    List<BookEntity> findByShelfLevel(Long shelfLevel);

    @Override
    List<BookEntity> findAll();

    @Query(value = "SELECT * FROM book " +
            "INNER JOIN shelf ON shelf.id = book.shelf_id " +
            "WHERE shelf.rack_id = ?1", nativeQuery = true)
    List<BookEntity> findByRackId(Long id);

    @Query(value = "SELECT * FROM book " +
            "INNER JOIN shelf ON shelf.id = book.shelf_id " +
            "WHERE shelf.rack_id = ?1 AND shelf.level = ?2", nativeQuery = true)
    List<BookEntity> findByRackIdAndShelfLevel(Long rackId, Long shelfLevel);
}
