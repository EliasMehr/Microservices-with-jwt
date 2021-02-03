package com.advertisementproject.userservice.service.interfaces;

import com.advertisementproject.userservice.db.entity.Company;
import com.advertisementproject.userservice.db.entity.Customer;
import com.advertisementproject.userservice.db.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * Validation Service implementation for validating entities.
 */
public interface ValidationService {

    /**
     * Validates a user according to annotations
     * @param user the user to be validated
     */
    void validateUser(@Valid @RequestBody User user);

    /**
     * Validates a customer according to annotations and personal id number
     * @param customer the customer to be validated
     */
    void validateCustomer(@Valid @RequestBody Customer customer);

    /**
     * Validates a company according to annotations and organization number
     * @param company the company to be validated
     */
    void validateCompany(@Valid @RequestBody Company company);
}
