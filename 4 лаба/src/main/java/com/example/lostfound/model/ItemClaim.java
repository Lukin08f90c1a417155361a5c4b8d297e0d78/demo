package com.example.lostfound.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_claims")
public class ItemClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loss_report_id", nullable = false)
    private LossReport lossReport;

    @ManyToOne
    @JoinColumn(name = "found_item_id", nullable = false)
    private FoundItem foundItem;

    private String evidence;

    @Column(nullable = false)
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LossReport getLossReport() { return lossReport; }
    public void setLossReport(LossReport lossReport) { this.lossReport = lossReport; }
    public FoundItem getFoundItem() { return foundItem; }
    public void setFoundItem(FoundItem foundItem) { this.foundItem = foundItem; }
    public String getEvidence() { return evidence; }
    public void setEvidence(String evidence) { this.evidence = evidence; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}