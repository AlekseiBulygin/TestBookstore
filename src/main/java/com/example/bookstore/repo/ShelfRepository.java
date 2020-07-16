package com.example.bookstore.repo;

import com.example.bookstore.objects.ShelfEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ShelfRepository extends CrudRepository<ShelfEntity, Long> {

    Optional<ShelfEntity> findById(Long id);

}