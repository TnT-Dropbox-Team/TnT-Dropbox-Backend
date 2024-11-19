package com.tntteam.tntdropbox.services;

import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    public String getAllLogs() {
        return "Seznam vseh logov:";
    }
    public String getLog(long id) {
        return "podrobnosti loga: " + id;
    }
    public String addNewLog() {
        return "ustvari nov log";
    }
}
