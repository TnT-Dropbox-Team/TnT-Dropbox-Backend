package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.dtos.LoginUserDTO;
import com.tntteam.tntdropbox.dtos.RegisterUserDTO;
import com.tntteam.tntdropbox.exceptions.conflict.ConflictException;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.exceptions.unauthorized.UnauthorizedException;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public String register(RegisterUserDTO user) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername())))
            throw new ConflictException("Username is already taken");
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        userRepository.save(newUser);
        return jwtService.generateToken(newUser);
    }
    public String login(LoginUserDTO loginUserDTO) {
        User user = userRepository.findByUsername(loginUserDTO.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
        if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }
        return jwtService.generateToken(user);
    }
    public void deleteUserProfile(Long id) {
        if (!userRepository.existsById(id))
            throw new ResourceNotFoundException("User with id " + id + " not found");
        userRepository.deleteById(id);
    }
}
