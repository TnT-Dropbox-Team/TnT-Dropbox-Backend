package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.dtos.HistoryAddDTO;
import com.tntteam.tntdropbox.dtos.HistoryGetDTO;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.models.History;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.HistoryRepository;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    public HistoryService(HistoryRepository historyRepository, UserRepository userRepository) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
    }

    public Page<HistoryGetDTO> getAllLogsForUser(Long userId, int page, int size, String[] sort) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User with id " + userId + " not found");
        }

        Pageable pageable = buildPageable(page, size, sort);
        Page<History> historyPage = historyRepository.findByUserId(userId, pageable);
        return historyPage.map(history -> new HistoryGetDTO(
                history.getId(),
                history.getLog(),
                history.getCreatedAt()
        ));
    }

    private Pageable buildPageable(int page, int size, String[] sort) {
        if (sort == null || sort.length == 0) {
            return PageRequest.of(page, size, Sort.unsorted());
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (int i = 0; i < sort.length; i += 2) {
            String field = sort[i];
            String direction = sort[i + 1];
            orders.add(new Sort.Order(Sort.Direction.fromString(direction), field));
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }

    public HistoryGetDTO getLogForUser(Long userId, Long logId) {
        History history = historyRepository.findById(logId).orElseThrow(
                () -> new ResourceNotFoundException("History not found"));
        if (historyRepository.existsByIdAndUserId(history.getId(), userId))
            return new HistoryGetDTO(history.getId(), history.getLog(), history.getCreatedAt());
        else
            throw new ForbiddenException("User does not have access to this log");
    }

    public HistoryGetDTO addNewLogForUser(Long userId, HistoryAddDTO historyAddDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        History history = new History();
        history.setLog(historyAddDTO.getLog());
        history.setUser(user);
        history.setCreatedAt(LocalDateTime.now());

        History historyRet = historyRepository.save(history);
        return new HistoryGetDTO(historyRet.getId(), historyRet.getLog(), historyRet.getCreatedAt());
    }
}
