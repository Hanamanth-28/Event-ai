
package com.eventscribe.api.service;

import com.eventscribe.api.dto.AuthResponse;
import com.eventscribe.api.dto.LoginRequest;
import com.eventscribe.api.dto.RegisterRequest;
import com.eventscribe.api.dto.UserDto;
import com.eventscribe.api.model.User;
import com.eventscribe.api.repository.UserRepository;
import com.eventscribe.api.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                        AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        // Check if user already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        // Create new user
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getEmail(),
                        registerRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        // Create response
        UserDto userDto = new UserDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
        return new AuthResponse(userDto, jwt);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        // Get user details
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return new AuthResponse(userDto, jwt);
    }
}
