package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.UserNotFoundException;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found for email : " + email)
        );
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found for id: " + id)
        );
    }

    public void deleteUserById(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User not found for id: " + id);
        }
    }

    public User updateUser(UUID id, User user){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            user.setId(id);
            userRepository.save(user);
            return user;
        } else {
            throw new UserNotFoundException("User not found for id: " + id);
        }
    }

    public boolean emailAlreadyExists(String email){
        return userRepository.existsByEmail(email);
    }
}
