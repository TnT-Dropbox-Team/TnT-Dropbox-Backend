package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.services.FileService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String getAllFiles() {
        return fileService.getAllFiles();
    }
    @GetMapping("/group/{grId}")
    public String getAllGroupFiles(@PathVariable long grId) {
        return fileService.getAllGroupFiles(grId);
    }
    @GetMapping("/{id}")
    public String getFile(@PathVariable long id) {
        return fileService.getFile(id);
    }
    @PostMapping()
    public String uploadNewFile() {
        return fileService.uploadNewFile();
    }
    @PostMapping("/group/{grId}")
    public String uploadGroupFile(@PathVariable long grId) {
        return fileService.uploadGroupFile(grId);
    }
    @PutMapping("/{id}")
    public String updateFile(@PathVariable long id) {
        return fileService.updateFile(id);
    }
    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable long id) {
        return fileService.deleteFile(id);
    }
}
