package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.services.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    }
    @GetMapping("/group/{grId}")
    public List<File> getAllGroupFiles(@PathVariable long grId) {
        return fileService.getAllGroupFiles(grId);
    }
    @GetMapping("/{id}")
    public File getFile(@PathVariable long id) {
        return fileService.getFile(id);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public File uploadNewFile(@RequestBody File file) {
        return fileService.uploadNewFile(file);
    }
    @PostMapping("/group/{grId}")
    @ResponseStatus(HttpStatus.CREATED)
    public File uploadGroupFile(@PathVariable long grId, @RequestBody File file) {
        return fileService.uploadGroupFile(grId, file);
    }
    @PutMapping("/{id}")
    public File updateFile(@PathVariable long id, @RequestBody File file) {
        return fileService.updateFile(id, file);
    }
    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable long id) {
        fileService.deleteFile(id);
    }
}
