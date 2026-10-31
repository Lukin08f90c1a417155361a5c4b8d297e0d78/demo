package com.example.lostfound.model;

import jakarta.persistence.*;

@Entity
@Table(name = "found_items")
public class FoundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(name = "location_found", nullable = false)
    private String locationFound;

    @Column(nullable = false)
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getLocationFound() { return locationFound; }
    public void setLocationFound(String locationFound) { this.locationFound = locationFound; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}