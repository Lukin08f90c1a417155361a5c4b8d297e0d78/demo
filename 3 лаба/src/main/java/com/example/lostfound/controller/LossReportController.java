package com.example.lostfound.controller;

import com.example.lostfound.model.LossReport;
import com.example.lostfound.service.LossReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loss-reports")
public class LossReportController {
    private final LossReportService service;

    public LossReportController(LossReportService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LossReport>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LossReport> getById(@PathVariable Long id) {
        LossReport report = service.getById(id);
        return report != null ? ResponseEntity.ok(report) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<LossReport> create(@RequestBody LossReport report) {
        return new ResponseEntity<>(service.create(report), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LossReport> update(@PathVariable Long id, @RequestBody LossReport report) {
        LossReport updated = service.update(id, report);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}