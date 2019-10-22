package owlmoney.logic.parser.investment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

/**
 * Abstracts common Investment methods and functions where the child parsers will inherit from.
 */
public abstract class ParseInvestment {
    HashMap<String, String> investmentParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    private static final String[] INVESTMENT_KEYWORD = new String[] {"/amount", "/name", "/newname"};
    static final String AMOUNT = "/amount";
    static final String NAME = "/name";
    static final String NEW_NAME = "/newname";
    private static final List<String> INVESTMENT_KEYWORD_LISTS = Arrays.asList(INVESTMENT_KEYWORD);

    /**
     * Creates an instance of any ParseInvestment type object.
     *
     * @param data Raw user input date.
     */
    ParseInvestment(String data) {
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
            throw new ParserException(command + "/investment should not contain " + parameter);
        }
    }

    /**
     * Checks if the first parameter is a valid parameter.
     *
     * @throws ParserException If the first parameter is invalid.
     */
    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!INVESTMENT_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Fills a hash table mapping each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        investmentParameters.put(AMOUNT,
                parseRawData.extractParameter(rawData, AMOUNT, INVESTMENT_KEYWORD));
        investmentParameters.put(NAME,
                parseRawData.extractParameter(rawData, NAME, INVESTMENT_KEYWORD));
        investmentParameters.put(NEW_NAME,
                parseRawData.extractParameter(rawData, NEW_NAME, INVESTMENT_KEYWORD));
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
     * Checks if the bank name entered by the user does not contain special character and not too long.
     *
     * @param key        /name or /newname
     * @param nameString Name of bank
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String key, String nameString) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException(key + " can only contain letters and at most 50 characters");
        }
    }

    /**
     * Abstract method where each investment parser performs different checks on the parameters.
     *
     * @throws ParserException If any parameters fail the check.
     */
    public abstract void checkParameter() throws ParserException;

    /**
     * Abstract method where each investment parser creates different commands.
     *
     * @return Command to be executed.
     */
    public abstract Command getCommand();
}
