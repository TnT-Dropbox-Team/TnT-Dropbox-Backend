package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.dtos.CommentDTO;
import com.tntteam.tntdropbox.dtos.CommentInputDTO;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.services.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile({"comment", "test"})
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("group/{groupId}")
    public Page<CommentDTO> getAllComments(
            @PathVariable long groupId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return commentService.getAllComments(groupId, user.getId(), page, size);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @PostMapping("group/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO uploadComment(@PathVariable long groupId, @Valid @RequestBody CommentInputDTO commentInput) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return commentService.uploadComment(groupId, user.getId(), commentInput);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @PutMapping("/{id}")
    public CommentDTO editComment(@PathVariable long id, @Valid @RequestBody CommentInputDTO commentInput) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return commentService.editComment(id, user.getId(), commentInput);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        commentService.deleteComment(id, user.getId());
    }
}
