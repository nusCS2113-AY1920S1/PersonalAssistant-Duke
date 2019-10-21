package owlmoney.logic.parser.find;

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

public abstract class ParseFind {
    HashMap<String, String> findParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    String type;
    private static final String[] FIND_KEYWORD = new String[] {
            "/name", "/desc", "/category", "/from", "/to"
    };
    private static final List<String> FIND_KEYWORD_LISTS = Arrays.asList(FIND_KEYWORD);
    static final String ISFIND = "/find";
    static final String NAME = "/name";
    static final String DESCRIPTION = "/desc";
    static final String CATEGORY = "/category";
    static final String FROM = "/from";
    static final String TO = "/to";

    /**
     * Constructor which creates an instance of any ParseExpenditure type object.
     *
     * @param data Raw user input date.
     * @param type Represents type of expenditure to be added.
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
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Fills a hash table mapping each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        findParameters.put(DESCRIPTION,
                parseRawData.extractParameter(rawData, DESCRIPTION, FIND_KEYWORD));
        findParameters.put(CATEGORY,
                parseRawData.extractParameter(rawData, CATEGORY, FIND_KEYWORD));
        findParameters.put(FROM,
                parseRawData.extractParameter(rawData, FROM, FIND_KEYWORD));
        findParameters.put(TO,
                parseRawData.extractParameter(rawData, TO, FIND_KEYWORD));
    }

    /**
     * Checks if the description entered by the user does not have special characters and is not too long.
     *
     * @param descString Deposit description.
     * @throws ParserException If the string has special characters or is too long.
     */
    void checkDescription(String descString) throws ParserException {
        if (!RegexUtil.regexCheckDescription(descString)) {
            throw new ParserException("/desc can only contain numbers and letters and at most 50 characters");
        }
    }

    /**
     * Checks if the bank name entered by the user does not contain special character and not too long.
     *
     * @param nameString Name of bank
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String nameString) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException("/from can only contain letters and at most 30 characters");
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
     * Abstract method where each saving parser performs different checks on the parameters.
     *
     * @throws ParserException If any parameters fail the check.
     */
    public abstract void checkParameter() throws ParserException;

    /**
     * Abstract method where each saving parser creates different commands.
     *
     * @return Command to be executed.
     */
    public abstract Command getCommand();
}
