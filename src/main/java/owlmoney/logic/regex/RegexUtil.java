package owlmoney.logic.regex;

/**
 * RegexUtil is used to validate user input to make sure it satisfies constraints set for user input.
 */
public final class RegexUtil {
    /**
     * Checks whether amount input by user is within the 9 digit constraints with a max of 2 decimal places.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckMoney(String input) {
        final String moneyRegex = "^\\s*(?=.*[1-9])\\d{1,9}(\\.\\d{1,2})?$";
        return input.matches(moneyRegex);
    }

    /**
     * Checks whether bank values input by user is within the 9 digit constraints with a max of 2 decimal places.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckBankAmount(String input) {
        final String moneyRegex = "^\\s*(?=.*[0-9])\\d{1,9}(\\.\\d{1,2})?$";
        return input.matches(moneyRegex);
    }

    /**
     * Checks whether the interest rate entered is within the 100% limit.
     * The first line of checks determines whether it is a 2 digit number up to 2 decimal places.
     * The second line of checks determines whether it is less than 100%.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckInterestRate(String input) {
        final String interestRegex = "^\\s*(?=.*[1-9])\\d{1,2}(\\.\\d{1,2})?$";
        if (input.matches(interestRegex)) {
            double parsedInput = Double.parseDouble(input);
            return (parsedInput < 100.00);
        } else {
            return false;
        }
    }

    /**
     * Checks whether the credit card cashback rate entered is within the 20% limit.
     * The first line of checks determines whether it is a 2 digit number up to 2 decimal places.
     * The second line of checks determines whether it is less than 20%.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckCashbackRate(String input) {
        final String interestRegex = "^\\s*(?=.*[1-9])\\d{1,2}(\\.\\d{1,2})?$";
        if (input.matches(interestRegex)) {
            double parsedInput = Double.parseDouble(input);
            return (parsedInput <= 20.00);
        } else {
            return false;
        }
    }

    /**
     * Checks whether input entered by user is numeric and is within the 9 digit constraints with no decimals.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckListNumber(String input) {
        final String listRegex = "^[1-9]\\d{0,8}$";
        return input.matches(listRegex);
    }

    /**
     * Checks whether input entered by user is numeric and is within the 9 digit constraints with no decimals.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckBondYear(String input) {
        final String yearRegex = "^[1-9]\\d{0,1}$";
        if (input.matches(yearRegex)) {
            int parsedInput = Integer.parseInt(input);
            return (parsedInput <= 10);
        } else {
            return false;
        }
    }

    /**
     * Checks whether input entered by user is alphanumeric with a maximum of 30 characters only.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckName(String input) {
        if (input.isBlank() || input.isEmpty()) {
            return false;
        }
        final String nameRegex = "^[a-zA-Z0-9 ]{1,30}$";
        return input.matches(nameRegex);
    }

    /**
     * Checks whether input entered by user is alphanumeric with a maximum of 50 characters only.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckDescription(String input) {
        if (input.isBlank() || input.isEmpty()) {
            return false;
        }
        final String descriptionRegex = "^[a-zA-Z0-9 ]{1,50}$";
        return input.matches(descriptionRegex);
    }

    /**
     * Checks whether input entered by user is alphanumeric with a maximum of 15 characters only.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckCategory(String input) {
        if (input.isBlank() || input.isEmpty()) {
            return false;
        }
        final String categoryRegex = "^[a-zA-Z ]{1,15}$";
        return input.matches(categoryRegex);
    }

    /**
     * Checks whether user input days is less than or equal to 365 days limit set for short term goals.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckDay(String input) {
        final String dayRegex = "^[1-9]\\d{0,2}$";
        if (input.matches(dayRegex)) {
            int parsedDay = Integer.parseInt(input);
            return (parsedDay <= 365);
        } else {
            return false;
        }
    }

    /**
     * Checks if date is in DD/MM/YYYY format from year 1900 to 2099.
     * It does not check for whether the month has 30 or 31 days or whether february is a leap year.
     *
     * @param input The user input that is subject to Regex checking.
     * @return the result of the check on whether it fulfills the criteria.
     */
    public static boolean regexCheckDateFormat(String input) {
        String dateRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        return input.matches(dateRegex);
    }

}
