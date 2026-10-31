package com.example.lostfound.service;

import com.example.lostfound.model.FoundItem;
import com.example.lostfound.model.ItemClaim;
import com.example.lostfound.model.LossReport;
import com.example.lostfound.repository.FoundItemRepository;
import com.example.lostfound.repository.ItemClaimRepository;
import com.example.lostfound.repository.LossReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemClaimService {
    private final ItemClaimRepository repository;
    private final LossReportRepository lossReportRepository;
    private final FoundItemRepository foundItemRepository;

    public ItemClaimService(ItemClaimRepository repository,
                            LossReportRepository lossReportRepository,
                            FoundItemRepository foundItemRepository) {
        this.repository = repository;
        this.lossReportRepository = lossReportRepository;
        this.foundItemRepository = foundItemRepository;
    }

    public List<ItemClaim> getAll() { return repository.findAll(); }
    public ItemClaim getById(Long id) { return repository.findById(id).orElse(null); }
    public ItemClaim create(ItemClaim claim) { return repository.save(claim); }
    public ItemClaim update(Long id, ItemClaim claim) {
        claim.setId(id);
        return repository.save(claim);
    }
    public void delete(Long id) { repository.deleteById(id); }

    @Transactional
    public ItemClaim createClaim(Long lossReportId, Long foundItemId, String evidence) {
        LossReport report = lossReportRepository.findById(lossReportId)
                .orElseThrow(() -> new RuntimeException("Заявление не найдено"));
        FoundItem item = foundItemRepository.findById(foundItemId)
                .orElseThrow(() -> new RuntimeException("Предмет не найден"));

        if (!"OPEN".equals(report.getStatus())) {
            throw new RuntimeException("Заявление уже обработано");
        }
        if (!"AVAILABLE".equals(item.getStatus())) {
            throw new RuntimeException("Предмет уже недоступен");
        }

        ItemClaim claim = new ItemClaim();
        claim.setLossReport(report);
        claim.setFoundItem(item);
        claim.setEvidence(evidence);
        claim.setStatus("PENDING");

        report.setStatus("IN_PROGRESS");
        item.setStatus("CLAIMED");

        lossReportRepository.save(report);
        foundItemRepository.save(item);
        return repository.save(claim);
    }
}