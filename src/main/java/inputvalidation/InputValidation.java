package inputvalidation;

import java.util.regex.Pattern;

public class InputValidation {

    private final String USERID_PATTERN = "[0-9]*";
    private final String USERNAME_PATTERN = "[a-zA-Z]+";
    private final String ADDRESS_PATTERN = "[a-zA-Z]*";
    private final String DEPARTMENT = "[a-zA-Z]*";
    private final String EMAIL_PATTERN = "[0]{0}|^[a-z]*[0-9]*@gmail.com";
    private final String MOBILE_NUMBER = "[0]{0}|[789]{1}[0-9]{9}";
    private final String DATE_OF_BIRTH = "[0]{0}|(([0-2]{1}[0-9]{1}|[3]{1}[0-1])/([0-9]|[1]{1}[0-2]{1})/([9]{1}|[1-2]{1})[0-9])";

    private static boolean isValid(String expression, String input) {
        boolean isPatternMatch = Pattern.matches(expression, input);
        return isPatternMatch;
    }

    public void userIdValidator(String input) throws InvalidException {
        if (input.isEmpty()) {
            throw new InvalidException("User Id can not be empty.");
        }
        if (!InputValidation.isValid(USERID_PATTERN, input)) {
            throw new InvalidException("User Id must be integer and positive.");
        }
    }

    public void userNameValidator(String input) throws InvalidException {
        if (input.isEmpty()) {
            throw new InvalidException("Username can not be empty.");
        }
        if (!InputValidation.isValid(USERNAME_PATTERN, input)) {
            throw new InvalidException("Username can only use alphabet letter characters.");
        }
    }

    public void userAddressValidator(String input) throws InvalidException {
        if (!InputValidation.isValid(ADDRESS_PATTERN, input)) {
            throw new InvalidException("Address can only use alphabet letter characters.");
        }
    }

    public void userEmailValidator(String input) throws InvalidException {
        if (!InputValidation.isValid(EMAIL_PATTERN, input)) {
            throw new InvalidException("Invalid Email (Valid format =  example@gmail.com)");
        }
    }

    public void userMobileNumberValidator(String input) throws InvalidException {
        if (!InputValidation.isValid(MOBILE_NUMBER, input)) {
            throw new InvalidException("Invalid Mobile number (use only 10 digits in mobile number)");
        }
    }

    public void userDateOfBirthValidator(String input) throws InvalidException {
        if (!InputValidation.isValid(DATE_OF_BIRTH, input)) {
            throw new InvalidException("Invalid Date Of Birth (Valid format = DD/MM/YY)");
        }
    }

    public void userDepartmentValidator(String input) throws InvalidException {
        if (!InputValidation.isValid(DEPARTMENT, input)) {
            throw new InvalidException("Department can only use alphabet letter characters.");
        }
    }
}
