package owlmoney.logic.parser.find;

import static owlmoney.commons.log.LogsCenter.getLogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

/**
 * Abstracts common Find methods and functions where the child parsers will inherit from.
 */
public abstract class ParseFind {
    HashMap<String, String> findParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    String type;
    static final String NAME_PARAMETER = "/name";
    static final String DESCRIPTION_PARAMETER = "/desc";
    static final String CATEGORY_PARAMETER = "/category";
    static final String FROM_PARAMETER = "/from";
    static final String TO_PARAMETER = "/to";
    private static final String[] FIND_KEYWORD = new String[] {
        NAME_PARAMETER, DESCRIPTION_PARAMETER, CATEGORY_PARAMETER, FROM_PARAMETER, TO_PARAMETER
    };
    private static final List<String> FIND_KEYWORD_LISTS = Arrays.asList(FIND_KEYWORD);
    static final Logger logger = getLogger(ParseFind.class);


    /**
     * Creates an instance of any ParseFind object.
     *
     * @param data Raw user input data.
     * @param type Represents the type of object to be searched.
     */
    ParseFind(String data, String type) {
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
            logger.warning(command + " should not contain " + parameter);
            throw new ParserException(command + " should not contain " + parameter);
        }
    }

    /**
     * Checks if the first parameter is a valid parameter.
     *
     * @throws ParserException If the first parameter is invalid.
     */
    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!FIND_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            logger.warning("Incorrect parameter " + rawDateSplit[0]);
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Fills a hash table mapping each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        findParameters.put(DESCRIPTION_PARAMETER,
                parseRawData.extractParameter(rawData, DESCRIPTION_PARAMETER, FIND_KEYWORD).trim());
        findParameters.put(CATEGORY_PARAMETER,
                parseRawData.extractParameter(rawData, CATEGORY_PARAMETER, FIND_KEYWORD).trim());
        findParameters.put(FROM_PARAMETER,
                parseRawData.extractParameter(rawData, FROM_PARAMETER, FIND_KEYWORD).trim());
        findParameters.put(TO_PARAMETER,
                parseRawData.extractParameter(rawData, TO_PARAMETER, FIND_KEYWORD).trim());
        findParameters.put(NAME_PARAMETER,
                parseRawData.extractParameter(rawData, NAME_PARAMETER, FIND_KEYWORD).trim());
    }

    /**
     * Checks if the description entered by the user does not have special characters and is not too long.
     *
     * @param descString The description of the transaction.
     * @throws ParserException If the string has special characters or is too long.
     */
    void checkDescription(String descString) throws ParserException {
        if (!RegexUtil.regexCheckDescription(descString)) {
            logger.warning("/desc can only contain numbers and letters and at most 50 characters");
            throw new ParserException("/desc can only contain numbers and letters and at most 50 characters");
        }
    }

    /**
     * Checks if the category entered by the user does not have special characters and is not too long.
     *
     * @param categoryString The category of the transaction.
     * @throws ParserException If the string has special characters or is too long.
     */
    void checkCategory(String categoryString) throws ParserException {
        if (!RegexUtil.regexCheckCategory(categoryString)) {
            logger.warning("/category can only contains letters and at most 15 characters");
            throw new ParserException(
            "/category can only contains letters and at most 15 characters");
        }
    }

    /**
     * Checks if the bank or card name entered by the user does not contain
     * special character and not too long.
     *
     * @param nameString Name of bank or card
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String nameString) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            logger.warning("/name and /from can only be alphanumeric and at most 30 characters");
            throw new ParserException("/name and /from can only be alphanumeric and at most 30 characters");
        }
    }

    /**
     * Checks if the date is of valid format and not after now.
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
                    logger.warning("/from and /to date cannot be after today");
                    throw new ParserException("/from and /to date cannot be after today");
                }
                return date;
            } catch (ParseException e) {
                logger.warning("Incorrect date format."
                        + " Date format is dd/mm/yyyy in year range of 1900-2099");
                throw new ParserException("Incorrect date format."
                        + " Date format is dd/mm/yyyy in year range of 1900-2099");
            }
        }
        logger.warning("Incorrect date format."
                + " Date format is dd/mm/yyyy in year range of 1900-2099");
        throw new ParserException("Incorrect date format."
                + " Date format is dd/mm/yyyy in year range of 1900-2099");
    }

    /**
     * Abstract method where each child parser for ParseFind performs different checks on the parameters.
     *
     * @throws ParserException If any parameters fail the check.
     */
    public abstract void checkParameter() throws ParserException;

    /**
     * Abstract method where each child parser for ParseFind  creates different commands.
     *
     * @return Command to be executed.
     */
    public abstract Command getCommand();
}
