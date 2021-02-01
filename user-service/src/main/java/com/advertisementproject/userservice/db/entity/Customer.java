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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    private UUID userId;
    @NotNull
    @Size(min = 2, max = 20, message = "First name must be 2-20 characters long")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20, message = "Last name must be 2-20 characters long")
    private String lastName;
    @NotNull(message = "Personal ID number must not be null")
    private String personalIdNumber;

    public static Customer toCustomer(UUID userId, CustomerRegistrationRequest request){
        return Customer.builder()
                .userId(userId)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .personalIdNumber(request.getPersonalIdNumber())
                .build();
    }
}
