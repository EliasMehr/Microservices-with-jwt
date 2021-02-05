package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.EmailAlreadyRegisteredException;
import com.advertisementproject.userservice.api.exception.EntityNotFoundException;
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

/**
 * Service implementation for doing CRUD operations for users, customers and companies in the database
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ValidationService validationService;
    private final MessagePublisher messagePublisher;

    /**
     * Retrieves all customer/company users
     * @return list of all users with full user information including user as well as related customer/company
     */
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

    /**
     * Retrieves all info for a customer/company user
     * @param id the id for which to retrieve full user information
     * @return full user information including user as well as related customer/company for the supplied user id
     * @throws EntityNotFoundException if the user is not found for the supplied user id
     */
    @Override
    public Object getFullUserInfoById(UUID id) {
        User user = findUserById(id);
        return getCustomerOrCompanyUser(user);
    }

    /**
     * Retrieves all info for a customer/company user
     * @param email the email for which to retrieve full user information
     * @return full user information including user as well as related customer/company for the supplied email
     * @throws EntityNotFoundException if the user is not found for the supplied email
     */
    @Override
    public Object getFullUserInfoByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found for email: " + email));
        return getCustomerOrCompanyUser(user);
    }

    /**
     * Saves a customer user to the database and sends messages to inform other microservices that a user has been
     * created so they can update their databases accordingly.
     * @param user the user object to be saved
     * @param customer the customer object to be saved
     * @return the newly saved customer user
     */
    @Override
    public CustomerUserResponse saveCustomerUser(User user, Customer customer){
        userRepository.save(user);
        messagePublisher.sendUserMessage(user);

        customerRepository.save(customer);
        return new CustomerUserResponse(user, customer);
    }

    /**
     * Saves a company user to the database and sends messages to inform other microservices that a user has been
     * created as well as that a company has been created so they can update their databases accordingly.
     * @param user the user object to be saved
     * @param company the company object to be saved
     * @return the newly saved company user
     */
    @Override
    public CompanyUserResponse saveCompanyUser(User user, Company company){
        userRepository.save(user);
        messagePublisher.sendUserMessage(user);

        companyRepository.save(company);
        messagePublisher.sendCompanyMessage(company);

        return new CompanyUserResponse(user, company);
    }


    /**
     * Validates that a customer/company user is not already registered for a supplied email
     * @param email the email to validate is not already registered.
     * @throws EmailAlreadyRegisteredException if the supplied email is already registered.
     */
    @Override
    public void validateNotAlreadyRegistered(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email is already registered for email: " + email);
        }
    }

    /**
     * Retrieve a user object for the supplied id
     * @param id the user id for which to retrieve a user object
     * @return the user object retrieved for the supplied id
     * @throws EntityNotFoundException if the user is not found in the database for the supplied id
     */
    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found for id: " + id)
        );
    }

    /**
     * Retrieve a customer object for the supplied id
     * @param id the user id for which to retrieve a customer object
     * @return the customer object retrieved for the supplied id
     * @throws EntityNotFoundException if the customer is not found in the database for the supplied id
     */
    @Override
    public Customer findCustomerById(UUID id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Customer not found for id: " + id)
        );
    }

    /**
     * Retrieve a company object for the supplied id
     * @param id the user id for which to retrieve a company object
     * @return the company object retrieved for the supplied id
     * @throws EntityNotFoundException if the company is not found in the database for the supplied id
     */
    @Override
    public Company findCompanyById(UUID id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Company not found for id: " + id)
        );
    }

    /**
     * Deletes user and related customer/company for the supplied id. Informs other microservices that a user has been
     * deleted and that they should remove information related to that user id.
     * @param id the user id for which to delete a customer/company user
     */
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

    /**
     * Updates a customer/company user with the fields supplied in the UpdateUserRequest. Informs other microservices
     * that a user has been updated and that they should update their own user table. If the user is a company user,
     * other microservices are informed that a company has been updated and they should update their own company table.
     * @param id the id of the customer/company user to be updated
     * @param updateUserRequest request object including fields that should be updated
     * @return the newly updated customer/company user
     */
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

    /**
     * Helper method to get a CustomerUserResponse or CompanyUserResponse from a supplied user depending on their role.
     * @param user the user for which to get a CustomerUserResponse or CompanyUserResponse
     * @return CustomerUserResponse or CompanyUserResponse depending on the role of the supplied user.
     */
    private Object getCustomerOrCompanyUser(User user) {
        if (user.getRole().equals(Role.CUSTOMER)) {
            return new CustomerUserResponse(user, findCustomerById(user.getId()));
        } else {
            return new CompanyUserResponse(user, findCompanyById(user.getId()));
        }
    }

    /**
     * Helper method to update user using the fields that are not null in the supplied UpdateUserRequest
     * @param updateUserRequest request object with fields to update the user with.
     * @param user the user to update
     */
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

    /**
     * Helper method to update customer using the fields that are not null in the supplied UpdateUserRequest
     * @param updateUserRequest request object with fields to update the customer with.
     * @param customer the customer to update
     */
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

    /**
     * Helper method to update company using the fields that are not null in the supplied UpdateUserRequest
     * @param updateUserRequest request object with fields to update the company with.
     * @param company the company to update
     */
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

    /**
     * Enables the user with supplied id
     * @param userId the id of the user to be enabled
     * @throws EntityNotFoundException if the user is not found for the supplied id
     */
    @Override
    @Transactional
    public void enableUser(UUID userId) {
        userRepository.enableUser(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found for id: " + userId));
        messagePublisher.sendUserMessage(user);
        log.info("User enabled with id: " + userId);
    }
}