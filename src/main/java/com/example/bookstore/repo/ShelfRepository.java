package com.example.bookstore.repo;

import com.example.bookstore.objects.ShelfEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface ShelfRepository extends CrudRepository<ShelfEntity, Long> {

    Optional<ShelfEntity> findById(Long id);

    @Query(value = "SELECT * FROM shelf " +
        "WHERE shelf.rack_id = ?1 AND shelf.level = ?2", nativeQuery = true)
    Optional<ShelfEntity> findByRackIdAndLevel(Long id, Long level);


}