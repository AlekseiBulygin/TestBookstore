package com.example.bookstore.dto;

import java.util.Objects;

public class BookDTO {
    private Long id;
    private String name;
    private Long rackId;
    private Long shelfLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getShelfLevel() {
        return shelfLevel;
    }

    public void setShelfLevel(Long shelfLevel) {
        this.shelfLevel = shelfLevel;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(name, bookDTO.name) &&
                Objects.equals(rackId, bookDTO.rackId) &&
                Objects.equals(shelfLevel, bookDTO.shelfLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rackId, shelfLevel);
    }

    @Override
    public String toString() {
        return String.format("{\"id\": %d, \"name\": \"%s\", \"rackId\": %d, \"shelfLevel\": %d}",
                id, name, rackId, shelfLevel);
    }
}
