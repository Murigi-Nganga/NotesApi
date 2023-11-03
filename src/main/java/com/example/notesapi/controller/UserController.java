package com.example.notesapi.controller;


import com.example.notesapi.domain.PatchUserDTO;
import com.example.notesapi.domain.User;
import com.example.notesapi.domain.UserResponseDTO;
import com.example.notesapi.service.UserService;
import com.example.notesapi.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param user The user object to be added to the database
     * @return The created user
     */
    @PostMapping(path = {"","/"})
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody User user) {
        UserResponseDTO createdUserDTO;
        try {
            createdUserDTO = userService.createUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(201).body(createdUserDTO);
    }

    @GetMapping(path = {"/{userId}", "/{userId}/"})
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long userId) {
        UserResponseDTO userResponseDTO;

        try {
            userResponseDTO = userService.getUser(userId);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(200).body(userResponseDTO);
    }

    @GetMapping(path = {"/all", "/all/"})
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PatchMapping(path = {"/{userId}", "/{userId}/"})
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long userId, @RequestBody PatchUserDTO patchUserDTO) {
        UserResponseDTO userResponseDTO;
        try {
            userResponseDTO = userService.updateUser(userId, patchUserDTO);
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(200).body(userResponseDTO);
    }

    @DeleteMapping(path = {"/{userId}", "/{userId}/"})
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
