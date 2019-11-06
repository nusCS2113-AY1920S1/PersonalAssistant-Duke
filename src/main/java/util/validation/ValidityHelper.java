package util.validation;

import java.util.regex.Pattern;

public class ValidityHelper {
    /**
     * Checks validity of the email address.
     * @param email The entered email address.
     * @return Boolean value indicating validity of email address.
     */
    private boolean emailChecker(String email) {
        Pattern validEmailAddressRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return validEmailAddressRegex.matcher(email).find();
    }

    /**
     * Checks validity of the phone number.
     * @param phoneNumber The entered phone number.
     * @return Boolean value indicating validity of phoneNumber.
     */
    private boolean phoneChecker(String phoneNumber) {
        if (phoneNumber.length() > 8) {
            return false;
        }
        try {
            Integer.parseInt(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns an error message if either phone number or email address are not valid.
     * @param email The entered email address..
     * @param phone The entered phone number.
     * @return An error message indicating if either phone number or email address are not valid.
     */
    public String emailPhoneErrorMessage(String email, String phone) {
        boolean invalidEmailFlag = !emailChecker(email) && !"--".equals(email);
        boolean invalidPhoneFlag = !phoneChecker(phone) && !"--".equals(phone);
        String errorMessage = "";
        if (invalidPhoneFlag) {
            errorMessage += "Phone number is not a valid phone number! Please make sure the phone number "
                    + "only has digits and a length of no more than 8 digits.";
        }
        if (invalidEmailFlag) {
            String newLine = (!"".equals(errorMessage)) ? "\n\t" : "";
            errorMessage += (newLine + "Email address is not a valid email address! Please adhere to standard "
                    + "email address formats, such as archduke@emailprovider.com");
        }
        return errorMessage;
    }
}
