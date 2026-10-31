package com.example.lostfound.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lostfound.model.ItemClaim;
import com.example.lostfound.service.ItemClaimService;

@RestController
@RequestMapping("/api/item-claims")
public class ItemClaimController {
    private final ItemClaimService service;

    public ItemClaimController(ItemClaimService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ItemClaim>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemClaim> getById(@PathVariable Long id) {
        ItemClaim claim = service.getById(id);
        return claim != null ? ResponseEntity.ok(claim) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ItemClaim> create(@RequestBody ItemClaim claim) {
        return new ResponseEntity<>(service.create(claim), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/claim")
    public ResponseEntity<ItemClaim> createClaim(
            @RequestParam Long lossReportId,
            @RequestParam Long foundItemId,
            @RequestParam String evidence) {
        ItemClaim claim = service.createClaim(lossReportId, foundItemId, evidence);
        return new ResponseEntity<>(claim, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ItemClaim> update(@PathVariable Long id, @RequestBody ItemClaim claim) {
        ItemClaim updated = service.update(id, claim);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}