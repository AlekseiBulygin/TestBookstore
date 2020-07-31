package com.example.bookstore.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "shelf")
public class ShelfEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long level;

    @OneToMany(targetEntity = BookEntity.class, mappedBy = "shelf", cascade=CascadeType.ALL)
    private List<BookEntity> books;

    @ManyToOne
    @JoinColumn(name = "RACK_ID")
    private RackEntity rack;

    protected ShelfEntity() {};

    public ShelfEntity(Long level) {
        this.level = level;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLevel() {
        return this.level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public RackEntity getRack() {
        return rack;
    }

    public void setRack(RackEntity rack) {
        this.rack = rack;
    }

    @JsonIgnore
    public List<BookEntity> getBooks() {
        return books;
    }

    public void addBook(BookEntity newBook) {
        this.books.add(newBook);
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelfEntity that = (ShelfEntity) o;
        return Objects.equals(level, that.level) &&
                Objects.equals(books, that.books) &&
                Objects.equals(rack, that.rack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, books, rack);
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d,\"level\":%s,\"rack\":%s}",
                this.id, this.level, this.rack.toString());
    }
}