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

public interface UserService {
    List<Object> findAllUsers();

    Object getFullUserInfoById(UUID id);

    Object getFullUserInfoByEmail(String email);

    CustomerUserResponse saveCustomerUser(User user, Customer customer);

    CompanyUserResponse saveCompanyUser(User user, Company company);

    void validateNotAlreadyRegistered(String email);

    User findUserById(UUID id);

    Customer findCustomerById(UUID id);

    Company findCompanyById(UUID id);

    @Transactional
    void deleteUserById(UUID id);

    @Transactional
    Object updateUser(UUID id, UpdateUserRequest updateUserRequest);

    @Transactional
    void enableUser(UUID userId);
}
