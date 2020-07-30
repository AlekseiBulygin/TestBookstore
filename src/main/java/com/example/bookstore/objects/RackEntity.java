package com.example.bookstore.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="rack")
public class RackEntity {

    @Id
    @GeneratedValue
    private  Long id;


    @OneToMany(targetEntity = ShelfEntity.class, mappedBy = "rack", cascade=CascadeType.ALL)
    private List<ShelfEntity> shelves = new ArrayList<>();

    public RackEntity() {};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @JsonIgnore
    public List<ShelfEntity> getShelves() {
        return shelves;
    }

    public void setShelves(List<ShelfEntity> shelves) {
        this.shelves = shelves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RackEntity that = (RackEntity) o;
        return Objects.equals(shelves, that.shelves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelves);
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d}", this.id);
    }
}
