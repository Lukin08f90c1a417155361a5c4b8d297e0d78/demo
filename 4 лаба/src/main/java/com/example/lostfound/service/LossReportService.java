package com.example.lostfound.service;

import com.example.lostfound.model.LossReport;
import com.example.lostfound.repository.LossReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LossReportService {
    private final LossReportRepository repository;

    public LossReportService(LossReportRepository repository) {
        this.repository = repository;
    }

    public List<LossReport> getAll() { return repository.findAll(); }
    public LossReport getById(Long id) { return repository.findById(id).orElse(null); }
    public LossReport create(LossReport report) { return repository.save(report); }
    public LossReport update(Long id, LossReport report) {
        report.setId(id);
        return repository.save(report);
    }
    public void delete(Long id) { repository.deleteById(id); }
}