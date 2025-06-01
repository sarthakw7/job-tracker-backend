package com.jobsTrackerApp.Controller;

import com.jobsTrackerApp.DTO.AuthDto;
import com.jobsTrackerApp.DTO.UserDto;
import com.jobsTrackerApp.Exception.ResourceConflictException;
import com.jobsTrackerApp.Model.User;
import com.jobsTrackerApp.Security.JwtTokenProvider;
import com.jobsTrackerApp.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    public ResponseEntity<AuthDto.JwtResponse> authenticateUser(@Valid @RequestBody AuthDto.LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        User user = userService.findUserByEmail(loginRequest.getEmail());
        UserDto.Response userResponse = modelMapper.map(user, UserDto.Response.class);

        return ResponseEntity.ok(new AuthDto.JwtResponse(jwt, "Bearer", userResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto.Response> registerUser(@Valid @RequestBody AuthDto.SignupRequest signupRequest) {
        if (userService.existsByEmail(signupRequest.getEmail())) {
            throw new ResourceConflictException("Email is already in use");
        }

        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .fullName(signupRequest.getFullName())
                .roles(Set.of("USER"))
                .build();

        User registerUser = userService.registerUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modelMapper.map(registerUser, UserDto.Response.class));
    }
}






























