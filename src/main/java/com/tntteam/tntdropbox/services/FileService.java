package com.tntteam.tntdropbox.services;

import org.springframework.stereotype.Service;

@Service
public class FileService {
    public String getAllFiles() {
        return "Seznam vseh datotek";
    }
    public String getAllGroupFiles(long grId) {
        return "Seznam vseh datotek od skupine: " + grId;
    }
    public String getFile(long id) {
        return "Datoteka: " + id;
    }
    public String uploadNewFile() {
        return "Shrani datoteko";
    }
    public String uploadGroupFile(long grId) {
        return "shrani datoteko v skupino: " + grId;
    }
    public String updateFile(long id) {
        return "Posodobi datoteko: " + id;
    }
    public String deleteFile(long id) {
        return "Izbriši datoteko: " + id;
    }
}