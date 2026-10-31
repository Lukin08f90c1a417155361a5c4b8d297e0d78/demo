package com.example.lostfound.controller;

import com.example.lostfound.model.ItemClaim;
import com.example.lostfound.service.ItemClaimService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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