package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.EmailAlreadyRegisteredException;
import com.advertisementproject.userservice.api.exception.UserNotFoundException;
import com.advertisementproject.userservice.api.request.UpdateUserRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.db.entity.Company;
import com.advertisementproject.userservice.db.entity.Customer;
import com.advertisementproject.userservice.db.entity.User;
import com.advertisementproject.userservice.db.entity.types.Role;
import com.advertisementproject.userservice.db.repository.CompanyRepository;
import com.advertisementproject.userservice.db.repository.CustomerRepository;
import com.advertisementproject.userservice.db.repository.UserRepository;
import com.advertisementproject.userservice.messagebroker.publisher.MessagePublisher;
import com.advertisementproject.userservice.service.interfaces.UserService;
import com.advertisementproject.userservice.service.interfaces.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ValidationService validationService;
    private final MessagePublisher messagePublisher;

    @Override
    public List<Object> findAllUsers() {

        List<User> userList = userRepository.findAll();
        List<Object> extendedUserList = new ArrayList<>();
        for (User user : userList) {
            if(user.getRole().equals(Role.CUSTOMER)) {
                extendedUserList.add(new CustomerUserResponse(user, findCustomerById(user.getId())));
            } else {
                extendedUserList.add(new CompanyUserResponse(user, findCompanyById(user.getId())));
            }
        }
        return extendedUserList;
    }

    @Override
    public Object getFullUserInfoById(UUID id) {
        User user = findUserById(id);
        return getCustomerOrCompanyUser(user);
    }

    @Override
    public Object getFullUserInfoByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for email: " + email));
        return getCustomerOrCompanyUser(user);
    }

    @Override
    public CustomerUserResponse saveCustomerUser(User user, Customer customer){
        userRepository.save(user);
        messagePublisher.sendUserMessage(user);

        customerRepository.save(customer);
        return new CustomerUserResponse(user, customer);
    }

    @Override
    public CompanyUserResponse saveCompanyUser(User user, Company company){
        userRepository.save(user);
        messagePublisher.sendUserMessage(user);

        companyRepository.save(company);
        messagePublisher.sendCompanyMessage(company);

        return new CompanyUserResponse(user, company);
    }


    @Override
    public void validateNotAlreadyRegistered(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email is already registered for email: " + email);
        }
    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found for id: " + id)
        );
    }

    @Override
    public Customer findCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Customer not found for id: " + id)
        );
    }

    @Override
    public Company findCompanyById(UUID id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("Company not found for id: " + id)
        );
    }

    @Override
    @Transactional
    public void deleteUserById(UUID id) {

        User user = findUserById(id);
        if(user.getRole().equals(Role.CUSTOMER)) {
            customerRepository.deleteById(user.getId());
        } else {
            companyRepository.deleteById(user.getId());
        }
        userRepository.deleteById(id);
        messagePublisher.sendUserDeleteMessage(id);
    }

    @Override
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
            messagePublisher.sendUserMessage(user);

            customerRepository.save(customer);

            return new CustomerUserResponse(user, customer);
        } else {
            Company company = findCompanyById(user.getId());
            updateCompanyFields(updateUserRequest, company);
            validationService.validateCompany(company);

            userRepository.save(user);
            messagePublisher.sendUserMessage(user);

            companyRepository.save(company);
            messagePublisher.sendCompanyMessage(company);

            return new CompanyUserResponse(user, company);
        }

    }

    private Object getCustomerOrCompanyUser(User user) {
        if (user.getRole().equals(Role.CUSTOMER)) {
            return new CustomerUserResponse(user, findCustomerById(user.getId()));
        } else {
            return new CompanyUserResponse(user, findCompanyById(user.getId()));
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

    @Override
    @Transactional
    public void enableUser(UUID userId) {
        userRepository.enableUser(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found for id: " + userId));
        messagePublisher.sendUserMessage(user);
        log.info("User enabled with id: " + userId);
    }

}
