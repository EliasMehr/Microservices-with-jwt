package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.models.types.Role;
import org.springframework.stereotype.Service;

import static com.advertisementproject.userservice.db.models.types.Role.CUSTOMER;

@Service
public class ValidationService {

//    public void validateUser(@Valid User user){
//        if(!isValidIdentificationNumber(user.getIdentificationNumber(), user.getRole())){
//            throw new IllegalArgumentException("Invalid identificationNumber");
//        }
//    }
    public void validateIdentificationNumber(User user){
        if (!isValidIdentificationNumber(user.getIdentificationNumber(), user.getRole())) {
            throw new IllegalArgumentException("Invalid identificationNumber");
        }
    }

    private boolean isValidIdentificationNumber(String identificationNumber, Role role){
        //TODO Fix actual validation!!!
        //Would have to be changed if more roles are added
        return role == CUSTOMER ? isValidPersonalNumber(identificationNumber) : isValidOrganizationNumber(identificationNumber);
    }

    private boolean isValidPersonalNumber(String personalNumber){

        return true;
    }

    private boolean isValidOrganizationNumber(String organizationNumber){

        return true;
    }
}
