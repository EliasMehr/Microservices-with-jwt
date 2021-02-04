package com.advertisementproject.userservice.api.response;

import com.advertisementproject.userservice.db.entity.Customer;
import com.advertisementproject.userservice.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerUserResponse {

    private final User user;
    private final Customer customer;

}
