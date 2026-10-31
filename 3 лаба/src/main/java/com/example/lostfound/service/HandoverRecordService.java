package com.example.lostfound.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lostfound.model.HandoverRecord;
import com.example.lostfound.model.ItemClaim;
import com.example.lostfound.repository.FoundItemRepository;
import com.example.lostfound.repository.HandoverRecordRepository;
import com.example.lostfound.repository.ItemClaimRepository;

@Service
public class HandoverRecordService {
    private final HandoverRecordRepository repository;
    private final ItemClaimRepository itemClaimRepository;
    private final FoundItemRepository foundItemRepository;

    public HandoverRecordService(HandoverRecordRepository repository,
                                 ItemClaimRepository itemClaimRepository,
                                 FoundItemRepository foundItemRepository) {
        this.repository = repository;
        this.itemClaimRepository = itemClaimRepository;
        this.foundItemRepository = foundItemRepository;
    }

    public List<HandoverRecord> getAll() { return repository.findAll(); }
    public HandoverRecord getById(Long id) { return repository.findById(id).orElse(null); }

    public HandoverRecord create(HandoverRecord record) {
        record.setHandoverDate(LocalDateTime.now());
        return repository.save(record);
    }

    public HandoverRecord update(Long id, HandoverRecord record) {
        record.setId(id);
        record.setHandoverDate(LocalDateTime.now());
        return repository.save(record);
    }

    public void delete(Long id) { repository.deleteById(id); }

    @Transactional
    public HandoverRecord confirmHandover(Long claimId) {
        ItemClaim claim = itemClaimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Претензия не найдена"));

        if (!"PENDING".equals(claim.getStatus())) {
            throw new RuntimeException("Претензия уже обработана");
        }

        HandoverRecord record = new HandoverRecord();
        record.setClaim(claim);
        record.setHandoverDate(LocalDateTime.now());
        record.setConfirmed(true);

        claim.setStatus("HANDED_OVER");
        claim.getFoundItem().setStatus("RETURNED");

        itemClaimRepository.save(claim);
        foundItemRepository.save(claim.getFoundItem());

        return repository.save(record);
    }
}