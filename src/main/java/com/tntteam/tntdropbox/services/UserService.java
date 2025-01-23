package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.dtos.*;
import com.tntteam.tntdropbox.exceptions.conflict.ConflictException;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.exceptions.unauthorized.UnauthorizedException;
import com.tntteam.tntdropbox.models.Group;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.GroupRepository;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, GroupRepository groupRepository, PasswordEncoder passwordEncoder, JwtService jwtService, EmailService emailService) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailService = emailService;
    }

    public Page<SimpleUserDTO> getAllUsers(String searchQuery, int page, int size) {
        searchQuery = (searchQuery == null || searchQuery.trim().isEmpty()) ? "%" : "%" + searchQuery.trim() + "%";
        Pageable pageable = buildPageable(page, size);
        Page<User> userPage = userRepository.findByUsernameLike(searchQuery, pageable);

        return userPage.map(user -> new SimpleUserDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName()
        ));
    }

    private Pageable buildPageable(int page, int size) {
        return PageRequest.of(page, size, Sort.unsorted());
    }

    public JwtDTO register(RegisterUserDTO user) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername())))
            throw new ConflictException("Username is already taken");
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        userRepository.save(newUser);
        if (user.getEmail() != null) {
            emailService.sendEmail(
                    user.getEmail(),
                    "Registration successful",
                    user.getFirstName() + ", Welcome to TnT Dropbox. Your registration was successful. Thank you for joining us!"
            );
        }
        return new JwtDTO(jwtService.generateToken(newUser));
    }

    public JwtDTO login(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByUsername(loginUserDTO.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
        if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }
        return new JwtDTO(jwtService.generateToken(user));
    }

    @Transactional
    public void deleteUserProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found"));

        for (Group group : user.getGroups()) {
            group.getUsers().remove(user);
        }
        user.getGroups().clear();

        for (Group group : user.getAdminGroups()) {
            group.getUsers().remove(user);

            if (!group.getUsers().isEmpty()) {
                User newAdmin = group.getUsers().get(0);
                group.setAdmin(newAdmin);
                groupRepository.save(group);
            } else {
                groupRepository.delete(group);
            }
        }

        user.getAdminGroups().clear();

        userRepository.deleteById(id);
    }
}
