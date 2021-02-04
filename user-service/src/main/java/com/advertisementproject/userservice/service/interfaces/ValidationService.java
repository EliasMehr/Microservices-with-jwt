package com.advertisementproject.userservice.service.interfaces;

import com.advertisementproject.userservice.db.entity.Company;
import com.advertisementproject.userservice.db.entity.Customer;
import com.advertisementproject.userservice.db.entity.User;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ValidationService {
    void validateUser(@Valid @RequestBody User user);

    void validateCustomer(@Valid @RequestBody Customer customer);

    void validateCompany(@Valid @RequestBody Company company);
}
