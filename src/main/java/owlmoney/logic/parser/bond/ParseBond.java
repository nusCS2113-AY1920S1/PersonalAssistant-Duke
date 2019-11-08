package owlmoney.logic.parser.bond;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

/**
 * Abstracts common bond methods and functions where the child parsers will inherit from.
 */
public abstract class ParseBond {
    HashMap<String, String> bondParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    String type;
    static final String AMOUNT_PARAMETER = "/amount";
    static final String NAME_PARAMETER = "/name";
    static final String RATE_PARAMETER = "/rate";
    static final String DATE_PARAMETER = "/date";
    static final String YEAR_PARAMETER = "/year";
    static final String FROM_PARAMETER = "/from";
    static final String NUM_PARAMETER = "/num";
    private static final String[] BOND_KEYWORD = new String[] {
        AMOUNT_PARAMETER, NAME_PARAMETER, RATE_PARAMETER, DATE_PARAMETER,
        YEAR_PARAMETER, FROM_PARAMETER, NUM_PARAMETER
    };
    private static final List<String> BOND_KEYWORD_LISTS = Arrays.asList(BOND_KEYWORD);

    /**
     * Creates an instance of any ParseBond type object.
     *
     * @param data Raw user input date.
     * @param type Bank type since bonds can only be added to savings account.
     */
    ParseBond(String data, String type) {
        this.rawData = data;
        this.type = type;
    }

    /**
     * Checks the user input for any redundant parameters.
     *
     * @param parameter Redundant parameter to check for,
     * @param command   Command the user performed.
     * @throws ParserException If a redundant parameter is detected.
     */
    void checkRedundantParameter(String parameter, String command) throws ParserException {
        if (rawData.contains(parameter)) {
            throw new ParserException(command + "/bond should not contain " + parameter);
        }
    }

    /**
     * Checks if the first parameter is a valid parameter.
     *
     * @throws ParserException If the first parameter is invalid.
     */
    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!BOND_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Fills a hash table mapping each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        bondParameters.put(AMOUNT_PARAMETER,
                parseRawData.extractParameter(rawData, AMOUNT_PARAMETER, BOND_KEYWORD).trim());
        bondParameters.put(NAME_PARAMETER,
                parseRawData.extractParameter(rawData, NAME_PARAMETER, BOND_KEYWORD).trim());
        bondParameters.put(DATE_PARAMETER,
                parseRawData.extractParameter(rawData, DATE_PARAMETER, BOND_KEYWORD).trim());
        bondParameters.put(RATE_PARAMETER,
                parseRawData.extractParameter(rawData, RATE_PARAMETER, BOND_KEYWORD).trim());
        bondParameters.put(YEAR_PARAMETER,
                parseRawData.extractParameter(rawData, YEAR_PARAMETER, BOND_KEYWORD).trim());
        bondParameters.put(FROM_PARAMETER,
                parseRawData.extractParameter(rawData, FROM_PARAMETER, BOND_KEYWORD).trim());
        bondParameters.put(NUM_PARAMETER,
                parseRawData.extractParameter(rawData, NUM_PARAMETER, BOND_KEYWORD).trim());
    }

    /**
     * Checks if the amount entered by the user is a double and only contains numbers.
     *
     * @param valueString String to be converted to double as the user's amount.
     * @throws ParserException If the string is not a double value.
     */
    void checkAmount(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckMoney(valueString)) {
            throw new ParserException("/amount can only be numbers with at most 9 digits, 2 decimal places"
                    + " and a value of more than 0");
        }
    }

    /**
     * Checks if the interest rate of the bond is more than 0 and less than 100%.
     *
     * @param rateString String to be converted to double as the interest rate.
     * @throws ParserException If the string is not a double value or amount is more than 100%.
     */
    void checkInterestRate(String rateString) throws ParserException {
        if (!RegexUtil.regexCheckInterestRate(rateString)) {
            throw new ParserException("Interest rate should be a positive number less than 100%");
        }
    }

    /**
     * Checks if the bond name entered by the user does not contain special character and not too long.
     *
     * @param key        /name or /newname
     * @param nameString Name of bond
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String key, String nameString) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException(key + " can only be alphanumeric and at most 30 characters");
        }
    }

    /**
     * Checks if the deposit date is of valid format and not after now.
     *
     * @param dateString Date to be checked.
     * @return Date if checks pass.
     * @throws ParserException If date format is invalid.
     */
    Date checkDate(String dateString) throws ParserException {
        if (RegexUtil.regexCheckDateFormat(dateString)) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            temp.setLenient(false);
            Date date;
            try {
                date = temp.parse(dateString);
                if (date.compareTo(new Date()) > 0) {
                    throw new ParserException("/date cannot be after today");
                }
                return date;
            } catch (ParseException e) {
                throw new ParserException("Incorrect date format."
                        + " Date format is dd/mm/yyyy in year range of 1900-2099");
            }
        }
        throw new ParserException("Incorrect date format." + " Date format is dd/mm/yyyy in year range of 1900-2099");
    }

    /**
     * Checks if the bond is within 1 and 10 years.
     *
     * @param yearString year in string format.
     * @throws ParserException If the year does not follow the specified format of max of 10 years.
     */
    void checkYear(String yearString) throws ParserException {
        if (!RegexUtil.regexCheckBondYear(yearString)) {
            throw new ParserException("Bond years must be between 1 and 10 years");
        }
    }

    /**
     * Checks if the transaction number or display number entered by the user is an integer.
     *
     * @param valueString String to be converted to integer.
     * @throws ParserException If the string is not an integer.
     */
    void checkInt(String variable, String valueString) throws ParserException {
        if (!RegexUtil.regexCheckListNumber(valueString)) {
            throw new ParserException(variable + " can only be a positive number with at most 9 digits");
        }
    }

    /**
     * Checks the parameters entered by the user.
     *
     * @throws ParserException If any parameters fail the check.
     */
    public abstract void checkParameter() throws ParserException;

    /**
     * Gets the relevant command to be executed.
     *
     * @return Command to be executed.
     */
    public abstract Command getCommand();
}
