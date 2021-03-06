package com.advertisementproject.userservice.api.response;

import com.advertisementproject.userservice.db.entity.Customer;
import com.advertisementproject.userservice.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Response object including a user and customer, in other words a customer user
 */
@AllArgsConstructor
@Getter
public class CustomerUserResponse {

    /**
     * The core user details for the customer user.
     */
    private final User user;

    /**
     * Customer information for the customer user.
     */
    private final Customer customer;

}
