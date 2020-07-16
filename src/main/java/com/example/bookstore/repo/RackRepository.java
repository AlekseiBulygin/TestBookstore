package com.example.bookstore.repo;

import com.example.bookstore.objects.RackEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RackRepository extends CrudRepository<RackEntity, Long> {

    Optional<RackEntity> findById(Long id);

}
