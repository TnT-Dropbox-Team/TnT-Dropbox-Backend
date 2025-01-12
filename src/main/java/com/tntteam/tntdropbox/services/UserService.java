package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.dtos.*;
import com.tntteam.tntdropbox.exceptions.conflict.ConflictException;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.exceptions.unauthorized.UnauthorizedException;
import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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
        userRepository.save(newUser);
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
    public void deleteUserProfile(Long id) {
        if (!userRepository.existsById(id))
            throw new ResourceNotFoundException("User with id " + id + " not found");
        userRepository.deleteById(id);
    }
}
