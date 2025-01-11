package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.dtos.JwtDTO;
import com.tntteam.tntdropbox.dtos.LoginUserDTO;
import com.tntteam.tntdropbox.dtos.RegisterUserDTO;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.services.JwtService;
import com.tntteam.tntdropbox.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Profile({"user", "test"})
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public JwtDTO register(@Valid @RequestBody RegisterUserDTO user) {
        return userService.register(user);
    }
    @PostMapping("/login")
    public JwtDTO login(@RequestBody LoginUserDTO user) {
        return userService.login(user);
    }
    @SecurityRequirement(name = "TnTSecurityScheme")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUserProfile(@PathVariable Long id) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!authenticatedUser.getId().equals(id)) {
            throw new ForbiddenException("You cannot delete another user's profile");
        }
        userService.deleteUserProfile(id);
    }
}
