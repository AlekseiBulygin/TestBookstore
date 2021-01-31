package com.example.bookstore.repo;

import java.util.*;

import com.example.bookstore.objects.BookEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Long> {

    @Query("SELECT b " +
            "FROM BookEntity b " +
            "INNER JOIN FETCH b.shelf s " +
            "INNER JOIN FETCH s.rack r " +
            "WHERE b.name = ?1")
    Optional<BookEntity> findByName(String name);

    @Query("SELECT b " +
            "FROM BookEntity b " +
            "INNER JOIN FETCH b.shelf s " +
            "INNER JOIN FETCH s.rack r " +
            "WHERE s.level = ?1")
    List<BookEntity> findByShelfLevel(Long shelfLevel);

    @Override
    @Query("SELECT b " +
            "FROM BookEntity b " +
            "INNER JOIN FETCH b.shelf s " +
            "INNER JOIN FETCH s.rack r")
    List<BookEntity> findAll();

    @Query("SELECT b " +
            "FROM BookEntity b " +
            "INNER JOIN FETCH b.shelf s " +
            "INNER JOIN FETCH s.rack r " +
            "WHERE r.id = ?1")
    List<BookEntity> findByRackId(Long id);

    @Query("SELECT b " +
            "FROM BookEntity as b " +
            "INNER JOIN FETCH b.shelf as s " +
            "INNER JOIN FETCH s.rack as r " +
            "WHERE r.id = ?1 AND s.level = ?2")
    List<BookEntity> findByRackIdAndShelfLevel(Long rackId, Long shelfLevel);
}
