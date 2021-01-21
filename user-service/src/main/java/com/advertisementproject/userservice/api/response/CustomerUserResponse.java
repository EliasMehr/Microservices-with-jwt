package com.advertisementproject.userservice.api.response;

import com.advertisementproject.userservice.db.models.Customer;
import com.advertisementproject.userservice.db.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerUserResponse {

    private final User user;
    private final Customer customer;

}
