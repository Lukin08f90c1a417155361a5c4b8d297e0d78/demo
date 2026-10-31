package com.example.lostfound.controller;

import com.example.lostfound.model.HandoverRecord;
import com.example.lostfound.service.HandoverRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/handover-records")
public class HandoverRecordController {
    private final HandoverRecordService service;

    public HandoverRecordController(HandoverRecordService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HandoverRecord>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HandoverRecord> getById(@PathVariable Long id) {
        HandoverRecord record = service.getById(id);
        return record != null ? ResponseEntity.ok(record) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<HandoverRecord> create(@RequestBody HandoverRecord record) {
        return new ResponseEntity<>(service.create(record), HttpStatus.CREATED);
    }

    @PostMapping("/confirm/{claimId}")
    public ResponseEntity<HandoverRecord> confirmHandover(@PathVariable Long claimId) {
        HandoverRecord record = service.confirmHandover(claimId);
        return new ResponseEntity<>(record, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HandoverRecord> update(@PathVariable Long id, @RequestBody HandoverRecord record) {
        HandoverRecord updated = service.update(id, record);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}