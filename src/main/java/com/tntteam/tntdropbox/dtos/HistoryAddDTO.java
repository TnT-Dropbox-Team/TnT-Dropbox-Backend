package com.tntteam.tntdropbox.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class HistoryAddDTO {
    @NotNull(message = "Log can not be null")
    @NotEmpty(message = "Log can not be empty")
    private String log;

    public HistoryAddDTO() {}

    public HistoryAddDTO(String log) {
        this.log = log;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "HistoryAddDTO{" +
                "log='" + log + '\'' +
                '}';
    }
}
