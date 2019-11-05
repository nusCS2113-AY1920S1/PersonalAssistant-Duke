package util;

import java.util.regex.Pattern;

public class ValidityHelper {

    public boolean emailChecker(String email) {
        Pattern validEmailAddressRegex =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        System.out.println((validEmailAddressRegex.matcher(email).find()));
        return validEmailAddressRegex.matcher(email).find();
    }
}
