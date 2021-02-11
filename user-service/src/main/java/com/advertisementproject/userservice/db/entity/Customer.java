package com.advertisementproject.userservice.db.entity;

import com.advertisementproject.userservice.api.request.CustomerRegistrationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Customer entity for customer users with personal information about the user
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    /**
     * Primary id for Customer entity that matches user id.
     */
    @Id
    private UUID userId;

    /**
     * The first name of the customer.
     */
    @NotNull
    @Size(min = 2, max = 20, message = "First name must be 2-20 characters long")
    private String firstName;

    /**
     * The last name of the customer.
     */
    @NotNull
    @Size(min = 2, max = 20, message = "Last name must be 2-20 characters long")
    private String lastName;

    /**
     * Personal identification number for the customer according to the Swedish system.
     */
    @NotNull(message = "Personal ID number must not be null")
    private String personalIdNumber;

    /**
     * Builder method for constructing a customer from relevant fields in a supplied CustomerRegistrationRequest for a
     * supplied user id
     *
     * @param userId  the user id for which to create a customer entity
     * @param request request including all the relevant fields needed to make a customer entity
     * @return a new customer object based on the supplied user id and request object fields
     */
    public static Customer toCustomer(UUID userId, CustomerRegistrationRequest request) {
        return Customer.builder()
                .userId(userId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .personalIdNumber(request.getPersonalIdNumber())
                .build();
    }
}
