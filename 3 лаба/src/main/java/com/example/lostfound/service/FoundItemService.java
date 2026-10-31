package com.example.lostfound.service;

import com.example.lostfound.model.FoundItem;
import com.example.lostfound.repository.FoundItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoundItemService {
    private final FoundItemRepository repository;

    public FoundItemService(FoundItemRepository repository) {
        this.repository = repository;
    }

    public List<FoundItem> getAll() { return repository.findAll(); }
    public FoundItem getById(Long id) { return repository.findById(id).orElse(null); }
    public FoundItem create(FoundItem item) { return repository.save(item); }
    public FoundItem update(Long id, FoundItem item) {
        item.setId(id);
        return repository.save(item);
    }
    public void delete(Long id) { repository.deleteById(id); }
}