package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.UserNotFoundException;
import com.advertisementproject.userservice.api.request.RegistrationRequest;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.models.types.CompanyType;
import com.advertisementproject.userservice.db.models.types.Role;
import com.advertisementproject.userservice.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.advertisementproject.userservice.db.models.User.toUser;
import static com.advertisementproject.userservice.db.models.types.Role.CUSTOMER;
import static com.advertisementproject.userservice.db.models.types.Role.ORGANIZATION;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ValidationService validationService;

    public User registerUser(RegistrationRequest registrationRequest) {
        validationService.validateNotAlreadyRegistered(registrationRequest.getEmail());
        User user = toUser(registrationRequest);
        validationService.validateUser(user);
        userRepository.save(user);
        //TODO validate identification number and make sure any other validation is being done correctly (custom validator for User?)
        //TODO send request to email service to send email for validation
        return user;
    }

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
