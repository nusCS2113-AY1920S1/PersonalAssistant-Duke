package util;

import java.util.regex.Pattern;

public class ValidityHelper {
    public boolean emailChecker(String email) {
        Pattern validEmailAddressRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        return validEmailAddressRegex.matcher(email).find();
    }

    public boolean phoneChecker(String phoneNumber) {
        if (phoneNumber.length()>8) {
            return false;
        }
        try {
            Integer.parseInt(phoneNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
