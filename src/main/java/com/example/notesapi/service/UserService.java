package com.example.notesapi.service;

import com.example.notesapi.domain.PatchUserDTO;
import com.example.notesapi.domain.User;
import com.example.notesapi.domain.UserResponseDTO;
import com.example.notesapi.repository.UserRepository;
import com.example.notesapi.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO createUser(User user) {
        return new UserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO getUser(Long userId) throws CustomException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new CustomException("User with Id" + userId + "doesn't exist");
        }
        return new UserResponseDTO(user.get());
    }

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Long userId, PatchUserDTO patchUserDTO) throws CustomException {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new CustomException("User with Id" + userId + "doesn't exist");
        }

        User userObj = user.get();

        if(patchUserDTO.firstName() != null) {
            userObj.setFirstName(patchUserDTO.firstName());
        }
        if(patchUserDTO.secondName() != null) {
            userObj.setSecondName(patchUserDTO.secondName());
        }
        if(patchUserDTO.email() != null) {
            userObj.setEmail(patchUserDTO.email());
        }
        if(patchUserDTO.phoneNumber() != null) {
            userObj.setPhoneNumber(patchUserDTO.phoneNumber());
        }
        if(patchUserDTO.password() != null) {
            userObj.setPassword(patchUserDTO.password());
        }

        return new UserResponseDTO(userRepository.save(userObj));

    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
