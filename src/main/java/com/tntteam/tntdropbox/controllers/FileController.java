package com.tntteam.tntdropbox.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileController {
    @GetMapping
    public String getAllFiles() {
        return "Seznam vseh datotek";
    }
    @GetMapping("/group/{GrId}")
    public String getAllGroupFiles(@PathVariable long GrId) {
        return "Seznam vseh datotek od skupine: " + GrId;
    }
    @GetMapping("/{id}")
    public String getFile(@PathVariable long id) {
        return "Datoteka: " + id;
    }
    @PostMapping()
    public String uploadNewFile() {
        return "Shrani datoteko";
    }
    @PostMapping("/group/{GrId}")
    public String uploadGroupFile(@PathVariable long GrId) {
        return "shrani datoteko v skupino: " + GrId;
    }
    @PutMapping("/{id}")
    public String updateFile(@PathVariable long id) {
        return "Posodobi datoteko: " + id;
    }
    @DeleteMapping("/{id}")
    public String deleteFile(@PathVariable long id) {
        return "Izbriši datoteko: " + id;
    }
}
