package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.EmailAlreadyRegisteredException;
import com.advertisementproject.userservice.api.exception.IdentificationNumberException;
import com.advertisementproject.userservice.db.models.Company;
import com.advertisementproject.userservice.db.models.Customer;
import com.advertisementproject.userservice.db.models.User;
import com.advertisementproject.userservice.db.models.types.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.advertisementproject.userservice.db.models.types.Role.CUSTOMER;

@Service
@Validated
public class ValidationService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void validateNotAlreadyRegistered(String email) {
        if(userService.emailAlreadyExists(email)){
            throw new EmailAlreadyRegisteredException("Email is already registered for email: " + email);
        }
    }

    public void validateUser(@Valid @RequestBody User user) {}

    public void validateCustomer(@Valid @RequestBody Customer customer){
        if(!isValidPersonalIdNumber(customer.getPersonalIdNumber())){
            throw new IdentificationNumberException("Invalid personal ID number");
        }
    }

    public void validateCompany(@Valid @RequestBody Company company){
        if(!isValidOrganizationNumber(company.getOrganizationNumber())){
            throw new IdentificationNumberException("Invalid organization number");
        }
    }

    private boolean isValidPersonalIdNumber(String input){
        boolean isValid = false;

        if (input.length() == 13 && input.charAt(8) == '-') {
            final String yyyyMMdd = input.substring(0, 8);
            input = yyyyMMdd + input.substring(9);

            if (isOnlyDigits(input)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                sdf.setLenient(false);

                try {
                    sdf.parse(yyyyMMdd); //Check if valid date
                    if (hasValidControlNumber(input.substring(2))) {
                        isValid = true;
                    }

                } catch (ParseException e) {
                    throw new IdentificationNumberException("Invalid birthdate for personal number");
                }
            }
        }
        return isValid;
    }

    private boolean isValidOrganizationNumber(String organizationNumber){
        if (organizationNumber.length() == 11 && organizationNumber.charAt(6) == '-') {
            organizationNumber = organizationNumber.replace("-", "");
        } else {
            return false;
        }
        return organizationNumber.length() == 10
                && isOnlyDigits(organizationNumber)
                && hasValidThirdDigit(organizationNumber)
                && hasValidControlNumber(organizationNumber);
    }

    private static boolean hasValidThirdDigit(String organizationNumber) {
        char c = organizationNumber.charAt(2);
        return Character.getNumericValue(c) >= 2;
    }

    private static boolean hasValidControlNumber(String identificationNumber) {
        char finalCharacter = identificationNumber.charAt(identificationNumber.length()-1);
        int controlNumber = Character.getNumericValue(finalCharacter);

        return controlNumber == calculateControlNumber(identificationNumber);
    }

    private static int calculateControlNumber(String identificationNumber){
        int sum = 0;

        for (int i = 0; i < identificationNumber.length() - 1; i++) {
            int currentDigit = identificationNumber.charAt(i) - '0';
            int multiplicationFactor = 1;

            if(i%2==0){
                multiplicationFactor = 2;
            }

            int result = currentDigit*multiplicationFactor;

            sum += result/10;
            sum += result%10;
        }

        int roundedToNextTen = (int) (Math.ceil(sum/10.0) * 10);

        return roundedToNextTen - sum;
    }

    private static boolean isOnlyDigits(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
