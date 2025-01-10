package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.services.HistoryService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@Profile({"history", "test"})
@RestController
@RequestMapping("/logs")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping()
    public String getAllLogs() {
        return historyService.getAllLogs();
    }
    @GetMapping("/{id}")
    public String getLog(@PathVariable long id) {
        return historyService.getLog(id);
    }
    @PostMapping()
    public String addNewLog() {
        return historyService.addNewLog();
    }
}

