package com.tntteam.tntdropbox.dtos;

import java.time.LocalDateTime;

public class HistoryGetDTO {
    private Long id;
    private String log;
    private LocalDateTime createdAt;

    public HistoryGetDTO(Long id, String log, LocalDateTime createdAt) {
        this.id = id;
        this.log = log;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "HistoryGetDTO{" +
                "id=" + id +
                ", log='" + log + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
