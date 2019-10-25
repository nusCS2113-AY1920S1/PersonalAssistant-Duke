package owlmoney.logic.parser.card;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

/**
 * Abstracts common Card methods and functions where the child parsers will inherit from.
 */
public abstract class ParseCard {
    HashMap<String, String> cardParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    private static final String[] CARD_KEYWORD = new String[] {
        "/name", "/limit", "/rebate", "/newname"
    };
    private static final List<String> EXPENDITURE_KEYWORD_LISTS = Arrays.asList(CARD_KEYWORD);
    static final String NAME = "/name";
    static final String LIMIT = "/limit";
    static final String REBATE = "/rebate";
    static final String NEW_NAME = "/newname";

    /**
     * Creates an instance of Card object.
     *
     * @param data Raw user input data.
     */
    ParseCard(String data) {
        this.rawData = data;
    }

    /**
     * Fills the hash table to map each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        cardParameters.put(NAME,
                parseRawData.extractParameter(rawData, NAME, CARD_KEYWORD));
        cardParameters.put(LIMIT,
                parseRawData.extractParameter(rawData, LIMIT, CARD_KEYWORD));
        cardParameters.put(REBATE,
                parseRawData.extractParameter(rawData, REBATE, CARD_KEYWORD));
        cardParameters.put(NEW_NAME,
                parseRawData.extractParameter(rawData, NEW_NAME, CARD_KEYWORD));
    }

    /**
     * Checks if the first parameter is a valid parameter.
     *
     * @throws ParserException If the first parameter is invalid.
     */
    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!EXPENDITURE_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Checks if the parameter entered by the user is a double and only contains numbers.
     *
     * @param valueString String to be checked and converted to double.
     * @throws ParserException If the string is not a double value.
     */
    void checkLimit(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckMoney(valueString)) {
            throw new ParserException("Limit can only be positive numbers"
                    + " with at most 9 digits and 2 decimal places");
        }
    }

    /**
     * Checks if the parameter entered by the user is a double and only contains numbers.
     *
     * @param valueString String to be checked and converted to double.
     * @throws ParserException If the string is not a double value.
     */
    void checkCashBack(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckMoney(valueString)) {
            throw new ParserException("Cash back can only be positive numbers"
                    + " with at most 2 digits and 2 decimal places and at most 20");
        }
    }

    /**
     * Checks if the card name entered by the user does not contain special character and not too long.
     *
     * @param nameString Name of the card
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String nameString) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException("Card name can only contain letters and at most 30 characters");
        }
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
            throw new ParserException(command + "/card should not contain " + parameter);
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
