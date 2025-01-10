package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.services.FileService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile({"file", "test"})
@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}")
    public File getFile(@PathVariable Long id) {
        return fileService.getFile(id);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public File uploadNewFile(@RequestBody File file) {
        return fileService.uploadNewFile(file);
    }
    @PostMapping("/group/{grId}")
    @ResponseStatus(HttpStatus.CREATED)
    public File uploadGroupFile(@PathVariable Long grId, @RequestBody File file) {
        return fileService.uploadGroupFile(grId, file);
    }
    @PutMapping("/{id}")
    public File updateFile(@PathVariable Long id, @RequestBody File file) {
        return fileService.updateFile(id, file);
    }
    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }
}
