package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.dtos.HistoryAddDTO;
import com.tntteam.tntdropbox.dtos.HistoryGetDTO;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.models.History;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.services.HistoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logs")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("/user/{id}")
    public Page<HistoryGetDTO> getAllLogs(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUser.getId().equals(id)) {
            throw new ForbiddenException("You cannot view another user's history");
        }
        return historyService.getAllLogsForUser(id, page, size, sort);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("/{id}")
    public HistoryGetDTO getLog(@PathVariable Long id) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return historyService.getLogForUser(authenticatedUser.getId(), id);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public HistoryGetDTO addNewLog(@Valid @RequestBody HistoryAddDTO historyAddDTO) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(" " + historyAddDTO);
        return historyService.addNewLogForUser(authenticatedUser.getId(), historyAddDTO);
    }
}

