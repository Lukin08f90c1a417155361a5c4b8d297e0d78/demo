package com.example.lostfound.controller;

import com.example.lostfound.model.FoundItem;
import com.example.lostfound.service.FoundItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/found-items")
public class FoundItemController {
    private final FoundItemService service;

    public FoundItemController(FoundItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FoundItem>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoundItem> getById(@PathVariable Long id) {
        FoundItem item = service.getById(id);
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FoundItem> create(@RequestBody FoundItem item) {
        return new ResponseEntity<>(service.create(item), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FoundItem> update(@PathVariable Long id, @RequestBody FoundItem item) {
        FoundItem updated = service.update(id, item);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}