package com.example.lostfound.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "handover_records")
public class HandoverRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "claim_id", nullable = false, unique = true)
    private ItemClaim claim;

    @Column(name = "handover_date", nullable = false)
    private LocalDateTime handoverDate;

    @Column(nullable = false)
    private boolean confirmed;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ItemClaim getClaim() { return claim; }
    public void setClaim(ItemClaim claim) { this.claim = claim; }
    public LocalDateTime getHandoverDate() { return handoverDate; }
    public void setHandoverDate(LocalDateTime handoverDate) { this.handoverDate = handoverDate; }
    public boolean isConfirmed() { return confirmed; }
    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }
}