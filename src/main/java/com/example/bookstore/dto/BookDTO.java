package com.example.bookstore.dto;

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
}
