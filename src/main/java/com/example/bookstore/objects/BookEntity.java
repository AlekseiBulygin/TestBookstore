package com.example.bookstore.objects;

import javax.persistence.*;

@Entity
@Table(name="book")
public class BookEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "SHELF_ID")
    private ShelfEntity shelf;

    protected BookEntity () {};

    public BookEntity (String name) {
        this.name = name;
    }

    public ShelfEntity getShelf() {
        return shelf;
    }

    public void setShelf(ShelfEntity shelf) {
        this.shelf = shelf;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%d,\"name\":\"%s\",\"shelf\":%s}",
                this.id, this.name, this.shelf.toString());
    }
}
