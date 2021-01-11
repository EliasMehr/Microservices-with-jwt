package com.advertisementproject.userservice.api.request;

import com.advertisementproject.userservice.db.models.types.CompanyType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.advertisementproject.userservice.db.models.types.CompanyType.NOT_SPECIFIED;

@Data
public class RegistrationRequest {
    @NotNull
    private final String identificationNumber;
    @NotNull
    @Size(min = 2, max = 20, message = "First name must be 2-20 characters long")
    private final String firstName;
    @NotNull
    @Size(min = 2, max = 20, message = "Last name must be 2-20 characters long")
    private final String lastName;
    @NotNull
    @Size(min = 2, max = 20, message = "Address must be 2-20 characters long")
    private final String address;
    @NotNull
    @Size(min = 2, max = 20, message = "City must be 2-20 characters long")
    private final String city;
    @NotNull
    @Pattern( regexp = "^[0-9]{5}$", message = "Zip code must be 5 digits long")
    private final String zipCode;
    @NotNull
    @Pattern( regexp = "^[0-9]{10}$", message = "PhoneNumber must be exactly 10 digits")
    private final String phoneNumber;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email is invalid")
    private final String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = """
                    Password must contain at least one digit [0-9].
                    Password must contain at least one lowercase Latin character [a-z].
                    Password must contain at least one uppercase Latin character [A-Z].
                    Password must contain at least one special character like ! @ # & ( ).
                    Password must contain a length of at least 8 characters and a maximum of 20 characters""")
    private final String password;
    private final CompanyType type = NOT_SPECIFIED;
}
