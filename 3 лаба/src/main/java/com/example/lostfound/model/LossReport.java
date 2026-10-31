package com.example.lostfound.model;

import jakarta.persistence.*;

@Entity
@Table(name = "loss_reports")
public class LossReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)
    private User applicant;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getApplicant() { return applicant; }
    public void setApplicant(User applicant) { this.applicant = applicant; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}