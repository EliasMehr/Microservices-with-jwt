package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.EmailAlreadyRegisteredException;
import com.advertisementproject.userservice.api.exception.UserNotFoundException;
import com.advertisementproject.userservice.api.request.UpdateUserRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.db.models.Company;
import com.advertisementproject.userservice.db.models.Customer;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.models.types.Role;
import com.advertisementproject.userservice.db.repository.CompanyRepository;
import com.advertisementproject.userservice.db.repository.CustomerRepository;
import com.advertisementproject.userservice.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ValidationService validationService;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found for email : " + email)
        );
    }

    public List<Object> findAllUsers() {

        List<User> userList = userRepository.findAll();
        List<Object> extendedUserList = new ArrayList<>();
        for (User user : userList) {
            if(user.getRole().equals(Role.CUSTOMER)) {
                extendedUserList.add(new CustomerUserResponse(user, findCustomerById(user.getId())));
            }
            else {
                extendedUserList.add(new CompanyUserResponse(user, findCompanyById(user.getId())));
            }
        }
        return extendedUserList;
    }

    public Object getFullUserInfoById(UUID id) {
        User user = findUserById(id);
        if(user.getRole().equals(Role.CUSTOMER)) {
            return new CustomerUserResponse(user, findCustomerById(user.getId()));
        }
        else {
            return new CompanyUserResponse(user, findCompanyById(user.getId()));
        }
    }

    public void validateNotAlreadyRegistered(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email is already registered for email: " + email);
        }
    }

    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found for id: " + id)
        );
    }

    public Customer findCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Customer not found for id: " + id)
        );
    }
    public Company findCompanyById(UUID id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Company not found for id: " + id)
        );
    }

    @Transactional
    public void deleteUserById(UUID id) {

        User user = findUserById(id);
        if(user.getRole().equals(Role.CUSTOMER)) {
            customerRepository.deleteById(user.getId());
        }
        else {
            companyRepository.deleteById(user.getId());
        }
        userRepository.deleteById(id);

    }

    @Transactional
    public Object updateUser(UUID id, UpdateUserRequest updateUserRequest) {

        User user = findUserById(id);
        updateUserFields(updateUserRequest, user);
        validationService.validateUser(user);

        if (user.getRole().equals(Role.CUSTOMER)) {
            Customer customer = findCustomerById(user.getId());
            updateCustomerFields(updateUserRequest, customer);
            validationService.validateCustomer(customer);
            userRepository.save(user);
            customerRepository.save(customer);
            return new CustomerUserResponse(user, customer);
        }

        else {
            Company company = findCompanyById(user.getId());
            updateCompanyFields(updateUserRequest, company);
            validationService.validateCompany(company);
            userRepository.save(user);
            companyRepository.save(company);
            return new CompanyUserResponse(user, company);
        }

    }


    private void updateUserFields(UpdateUserRequest updateUserRequest, User user) {

        if(updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
        if(updateUserRequest.getPassword() != null) {
            user.setRawPassword(updateUserRequest.getPassword());
            user.setHashedPassword(new BCryptPasswordEncoder(12).encode(updateUserRequest.getPassword()));
        }
        if(updateUserRequest.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }
        if(updateUserRequest.getAddress() != null) {
            user.setAddress(updateUserRequest.getAddress());
        }
        if(updateUserRequest.getCity() != null) {
            user.setCity(updateUserRequest.getCity());
        }
        if(updateUserRequest.getZipCode() != null) {
            user.setZipCode(updateUserRequest.getZipCode());
        }
    }

    private void updateCustomerFields(UpdateUserRequest updateUserRequest, Customer customer) {
        if(updateUserRequest.getFirstName() != null) {
            customer.setFirstName(updateUserRequest.getFirstName());
        }
        if(updateUserRequest.getLastName() != null) {
            customer.setLastName(updateUserRequest.getLastName());
        }
        if(updateUserRequest.getPersonalIdNumber() != null) {
            customer.setPersonalIdNumber(updateUserRequest.getPersonalIdNumber());
        }
    }

    private void updateCompanyFields(UpdateUserRequest updateUserRequest, Company company) {
        if(updateUserRequest.getName() != null) {
            company.setName(updateUserRequest.getName());
        }
        if(updateUserRequest.getOrganizationNumber() != null) {
            company.setOrganizationNumber(updateUserRequest.getOrganizationNumber());
        }
        if(updateUserRequest.getDescription() != null) {
            company.setDescription(updateUserRequest.getDescription());
        }
        if(updateUserRequest.getCompanyType() != null) {
            company.setCompanyType(updateUserRequest.getCompanyType());
        }

    }




    public boolean emailAlreadyExists(String email){
        return userRepository.existsByEmail(email);
    }
}
