package com.jobsTrackerApp.Controller;

import com.jobsTrackerApp.DTO.UserDto;
import com.jobsTrackerApp.Model.User;
import com.jobsTrackerApp.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserDto.Response>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(modelMapper.map(user, UserDto.Response.class));
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserDto.Response> getUserByEmail(@RequestParam String email){
        User user = userService.findUserByEmail(email);
        return ResponseEntity.ok(modelMapper.map(user, UserDto.Response.class));
    }

    @PostMapping
    public ResponseEntity<UserDto.Response> registerUser(@Valid @RequestBody UserDto.Request request){
        User user = modelMapper.map(request, User.class);
        User saved = userService.registerUser(user);
        return ResponseEntity.ok(modelMapper.map(saved, UserDto.Response.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto.Response> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserDto.UpdateRequest updateRequest) {
        User updated = userService.updateUserProfile(id, updateRequest);
        return ResponseEntity.ok(modelMapper.map(updated, UserDto.Response.class));

    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody UserDto.PasswordChangeRequest request
    ) {
        userService.changePassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}

