package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.dtos.FileGetDTO;
import com.tntteam.tntdropbox.dtos.LoginUserDTO;
import com.tntteam.tntdropbox.dtos.RegisterUserDTO;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.services.FileService;
import com.tntteam.tntdropbox.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;
    private final FileService fileService;

    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterUserDTO user) {
        return userService.register(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginUserDTO user) {
        return userService.login(user);
    }
    @SecurityRequirement(name = "TnTSecurityScheme")
    @DeleteMapping("/{id}")
    public void deleteUserProfile(@PathVariable Long id) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUser.getId().equals(id)) {
            throw new ForbiddenException("You cannot delete another user's profile");
        }
        userService.deleteUserProfile(id);
    }
    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("/{userId}/files")
    public Page<FileGetDTO> getUserFiles(
            @PathVariable Long userId,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String fileType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUser.getId().equals(userId)) {
            throw new ForbiddenException("You cannot view another user's files");
        }
        return fileService.getAllUserFiles(
                userId, searchQuery, fileType, page, size, sort);
    }
}
