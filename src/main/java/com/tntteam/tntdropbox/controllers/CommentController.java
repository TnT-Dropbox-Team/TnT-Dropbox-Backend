package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.services.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("group/{grId}")
    public String getAllComments(@PathVariable long grId) {
        return commentService.getAllComments(grId);
    }
    @PostMapping("group/{grId}")
    public String uploadComment(@PathVariable long grId) {
        return commentService.uploadComment(grId);
    }
    @PutMapping("/{id}")
    public String editComment(@PathVariable long id) {
        return commentService.editComment(id);
    }
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable long id) {
        return commentService.deleteComment(id);
    }
}
