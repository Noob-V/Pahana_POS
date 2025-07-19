package com.pahanaedu.entities;

import java.time.LocalDateTime;

public class Category {
    private Integer id;
    private String name;
    private String description;
    private boolean isActive;
    private LocalDateTime createdDate;

    public Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.isActive = true;
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}