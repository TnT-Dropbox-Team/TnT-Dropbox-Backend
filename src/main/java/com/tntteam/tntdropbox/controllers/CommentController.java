package com.tntteam.tntdropbox.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @GetMapping("group/{GrId}")
    public String getAllComments(@PathVariable long GrId) {
        return "Seznam vseh komentarjev v skupini: " + GrId;
    }
    @PostMapping("group/{GrId}")
    public String uploadComment(@PathVariable long GrId) {
        return "dodaj komentar v skupino: " + GrId;
    }
    @PutMapping("/{id}")
    public String editComment(@PathVariable long id) {
        return "uredi komentar: " + id;
    }
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable long id) {
        return "Izbriši komentar: " + id;
    }
}
