package com.tntteam.tntdropbox.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class HistoryController {
    @GetMapping()
    public String getAllLogs() {
        return "Seznam vseh logov:";
    }
    @GetMapping("/{id}")
    public String getLog(@PathVariable long id) {
        return "podrobnosti loga: " + id;
    }
    @PostMapping()
    public String addNewLog() {
        return "ustvari nov log";
    }
}

