package owlmoney.logic.parser.saving;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

/**
 * Abstracts common Savings methods and functions where the child parsers will inherit from.
 */
public abstract class ParseSaving {
    HashMap<String, String> savingsParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    private static final String[] SAVINGS_KEYWORD = new String[] {"/amount", "/income", "/name", "/newname"};
    static final String AMOUNT = "/amount";
    static final String INCOME = "/income";
    static final String NAME = "/name";
    static final String NEW_NAME = "/newname";
    private static final List<String> SAVINGS_KEYWORD_LISTS = Arrays.asList(SAVINGS_KEYWORD);

    /**
     * Creates an instance of any ParseSaving type object.
     *
     * @param data Raw user input data.
     */
    ParseSaving(String data) {
        this.rawData = data;
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
            throw new ParserException(command + "/savings should not contain " + parameter);
        }
    }

    /**
     * Checks if the first parameter is a valid parameter.
     *
     * @throws ParserException If the first parameter is invalid.
     */
    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!SAVINGS_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Fills a hash table mapping each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        savingsParameters.put(AMOUNT,
                parseRawData.extractParameter(rawData, AMOUNT, SAVINGS_KEYWORD));
        savingsParameters.put(INCOME,
                parseRawData.extractParameter(rawData, INCOME, SAVINGS_KEYWORD));
        savingsParameters.put(NAME,
                parseRawData.extractParameter(rawData, NAME, SAVINGS_KEYWORD));
        savingsParameters.put(NEW_NAME,
                parseRawData.extractParameter(rawData, NEW_NAME, SAVINGS_KEYWORD));
    }

    /**
     * Checks if the amount entered by the user is a double and only contains numbers.
     *
     * @param valueString String to be converted to double as the user's amount.
     * @throws ParserException If the string is not a double value.
     */
    void checkAmount(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckBankAmount(valueString)) {
            throw new ParserException("/amount can only be numbers with at most 9 digits, 2 decimal places"
                    + " and a value of at least 0");
        }
    }

    /**
     * Checks if the income entered by the user is a double and only contains numbers.
     *
     * @param valueString String to be converted to double as the user's income.
     * @throws ParserException If the string is not a double value.
     */
    void checkIncome(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckBankAmount(valueString)) {
            throw new ParserException("/income can only be numbers with at most 9 digits and 2 decimal places"
                    + " and a value of at least 0");
        }
    }

    /**
     * Checks if the bank name entered by the user does not contain special character and not too long.
     *
     * @param key        /name or /newname
     * @param nameString Name of bank
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String key, String nameString) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException(key + " can only contain letters and at most 30 characters");
        }
    }

    /**
     * Checks the parameters entered by the user.
     *
     * @throws ParserException If any parameters fail the check.
     */
    public abstract void checkParameter() throws ParserException;

    /**
     * Gets the command needed to be executed.
     *
     * @return Command to be executed.
     */
    public abstract Command getCommand();
}
