package com.fededge.server.controller;

import com.fededge.server.persistence.entity.TrainingEvent;
import com.fededge.server.persistence.repository.TrainingEventRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class DashboardController {

    private final TrainingEventRepository eventRepository;

    public DashboardController(TrainingEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @GetMapping("/events")
    public List<TrainingEvent> getRecentEvents() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }
}
