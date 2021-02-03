package com.advertisementproject.userservice.service;

import com.advertisementproject.userservice.api.exception.IdentificationNumberException;
import com.advertisementproject.userservice.db.entity.Company;
import com.advertisementproject.userservice.db.entity.Customer;
import com.advertisementproject.userservice.db.entity.User;
import com.advertisementproject.userservice.service.interfaces.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Validation Service implementation for validating entities.
 */
@Service
@Validated
@AllArgsConstructor
public class ValidationServiceImpl implements ValidationService {

    /**
     * Validates a user according to annotations
     * @param user the user to be validated
     */
    @Override
    public void validateUser(@Valid @RequestBody User user) {}

    /**
     * Validates a customer according to annotations and personal id number
     * @param customer the customer to be validated
     * @throws IdentificationNumberException if the personal id number has invalid format
     */
    @Override
    public void validateCustomer(@Valid @RequestBody Customer customer){
        if(!isValidPersonalIdNumber(customer.getPersonalIdNumber())){
            throw new IdentificationNumberException("Invalid personal ID number");
        }
    }

    /**
     * Validates a company according to annotations and organization number
     * @param company the company to be validated
     * @throws IdentificationNumberException if the organization number has invalid format
     */
    @Override
    public void validateCompany(@Valid @RequestBody Company company){
        if(!isValidOrganizationNumber(company.getOrganizationNumber())){
            throw new IdentificationNumberException("Invalid organization number");
        }
    }

    /**
     * Helper method to validate that a string is in valid personal id number format. Validates that the personal id
     * number has a correct length, is only digits apart from a hyphen, has a valid birthdate and that the control number
     * based on the luhn algorithm is correct.
     * @param input the string to be validated as a personal id number
     * @return true if valid personal id number format, otherwise false
     * @throws IdentificationNumberException if the birthdate portion of the personal id number is not a valid date
     */
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

    /**
     * Helper method to validate that a string is in valid organization number format. Validates that the organization
     * number has a correct length, is only digits apart from an optional hyphen, has a valid third digit to
     * differentiate from a personal id number and that the control number based on the luhn algorithm is correct.
     * @param input the string to be validated as a organization number
     * @return true if valid organization number format, otherwise false
     */
    private boolean isValidOrganizationNumber(String input){
        if (input.length() == 11 && input.charAt(6) == '-') {
            input = input.replace("-", "");
        } else {
            return false;
        }
        return input.length() == 10
                && isOnlyDigits(input)
                && hasValidThirdDigit(input)
                && hasValidControlNumber(input);
    }

    /**
     * Helper method to check if the third digit of a organization number is 2 or higher to differentiate from a
     * personal id number.
     * @param organizationNumber the organization number string to check the third digit for.
     * @return true if valid third digit, otherwise false
     */
    private static boolean hasValidThirdDigit(String organizationNumber) {
        char c = organizationNumber.charAt(2);
        return Character.getNumericValue(c) >= 2;
    }

    /**
     * Helper method to check if a personal id number or organization number has a correct control number according to
     * the luhn algorithm. The final digit is the control number and the first 9 digits are used to calculate the
     * control number.
     * @param identificationNumber the personal id number or organization number to check the control number for.
     * @return true if valid control number, otherwise false.
     */
    private static boolean hasValidControlNumber(String identificationNumber) {
        char finalCharacter = identificationNumber.charAt(identificationNumber.length()-1);
        int controlNumber = Character.getNumericValue(finalCharacter);

        return controlNumber == calculateControlNumber(identificationNumber);
    }

    /**
     * Helper method to calculate the control number based on the luhn algorithm for a 10 digit personal id number or
     * organization number. The luhn algorithm is based on the first 9 out of 10 digits and the result should match the
     * final digit which is the control number.
     * The luhn algorithm is calculated as follows:
     * First all 9 digits are multiplied with 2 or 1, alternating every time. So the first digit is multiplied by 2, the
     * second by 1, the third by 2, fourth by 1 and so on.
     * Then all the results are summed up, but the all digits are counted the same positional value, so if the first
     * digit was 8, then 8*2=16, which means that when the numbers are summed up then it's added as 1+6=7.
     * After all the results are summed up in this manner, the result is rounded up to the next ten from which the
     * result is subtracted. As an example, if the result is 37, then it's rounded up to 40. 40-37 = 3, which is the
     * correct control number for that 9 digit sequence and that is the value that would be returned from this method.
     * @param identificationNumber a 10 digit personal id number or organization number to calculate the control number
     *                             based on the luhn algorithm.
     * @return true if valid control number, false otherwise.
     */
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

    /**
     * Helper method to check if a string consists of only digits.
     * @param input the input string to check.
     * @return true if input is only digits, otherwise false.
     */
    private static boolean isOnlyDigits(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
