package com.advertisementproject.userservice.service.interfaces;

import com.advertisementproject.userservice.api.request.UpdateUserRequest;
import com.advertisementproject.userservice.api.response.CompanyUserResponse;
import com.advertisementproject.userservice.api.response.CustomerUserResponse;
import com.advertisementproject.userservice.db.entity.Company;
import com.advertisementproject.userservice.db.entity.Customer;
import com.advertisementproject.userservice.db.entity.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


/**
 * Service for doing CRUD operations for users, customers and companies in the database
 */
public interface UserService {

    /**
     * Retrieves all customer/company users
     *
     * @return list of all users with full user information including user as well as related customer/company
     */
    List<Object> findAllUsers();

    /**
     * Retrieves all info for a customer/company user
     *
     * @param id the id for which to retrieve full user information
     * @return full user information including user as well as related customer/company for the supplied user id
     */
    Object getFullUserInfoById(UUID id);

    /**
     * Retrieves all info for a customer/company user
     *
     * @param email the email for which to retrieve full user information
     * @return full user information including user as well as related customer/company for the supplied email
     */
    Object getFullUserInfoByEmail(String email);

    /**
     * Saves a customer user to the database
     *
     * @param user     the user object to be saved
     * @param customer the customer object to be saved
     * @return the newly saved customer user
     */
    CustomerUserResponse saveCustomerUser(User user, Customer customer);

    /**
     * Saves a company user to the database
     *
     * @param user    the user object to be saved
     * @param company the company object to be saved
     * @return the newly saved company user
     */
    CompanyUserResponse saveCompanyUser(User user, Company company);

    /**
     * Validates that a customer/company user is not already registered for a supplied email
     *
     * @param email the email to validate is not already registered
     */
    void validateNotAlreadyRegistered(String email);

    /**
     * Retrieve a user object for the supplied id
     *
     * @param id the user id for which to retrieve a user object
     * @return the user object retrieved for the supplied id
     */
    User findUserById(UUID id);

    /**
     * Retrieve a customer object for the supplied id
     *
     * @param id the user id for which to retrieve a customer object
     * @return the customer object retrieved for the supplied id
     */
    Customer findCustomerById(UUID id);

    /**
     * Retrieve a company object for the supplied id
     *
     * @param id the user id for which to retrieve a company object
     * @return the company object retrieved for the supplied id
     */
    Company findCompanyById(UUID id);

    /**
     * Deletes user and related customer/company for the supplied id
     *
     * @param id the user id for which to delete a customer/company user
     */
    @Transactional
    void deleteUserById(UUID id);

    /**
     * Updates a customer/company user with the fields supplied in the UpdateUserRequest
     *
     * @param id                the id of the customer/company user to be updated
     * @param updateUserRequest request object including fields that should be updated
     * @return the newly updated customer/company user
     */
    @Transactional
    Object updateUser(UUID id, UpdateUserRequest updateUserRequest);

    /**
     * Enables the user with supplied id
     *
     * @param userId the id of the user to be enabled
     */
    @Transactional
    void enableUser(UUID userId);
}