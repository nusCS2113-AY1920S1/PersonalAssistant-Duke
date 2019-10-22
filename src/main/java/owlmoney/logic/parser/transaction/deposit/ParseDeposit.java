package owlmoney.logic.parser.transaction.deposit;

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
 * ParseDeposit class which is abstract where various deposit parser objects inherit from given that it is abstract.
 */
public abstract class ParseDeposit {
    HashMap<String, String> depositParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    private static final String[] EXPENDITURE_KEYWORD = new String[] {
        "/amount", "/date", "/desc", "/category", "/to", "/transno", "/from", "/num"
    };
    private static final List<String> EXPENDITURE_KEYWORD_LISTS = Arrays.asList(EXPENDITURE_KEYWORD);
    static final String AMOUNT = "/amount";
    static final String DATE = "/date";
    static final String DESCRIPTION = "/desc";
    static final String TO = "/to";
    static final String TRANSNO = "/transno";
    static final String FROM = "/from";
    static final String NUM = "/num";

    /**
     * Constructor which creates an instance of any ParseSaving type object.
     *
     * @param data Raw user input data.
     */
    ParseDeposit(String data) {
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
            throw new ParserException(command + " /deposit should not contain " + parameter);
        }
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
     * Fills a hash table mapping each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        depositParameters.put(AMOUNT,
                parseRawData.extractParameter(rawData, AMOUNT, EXPENDITURE_KEYWORD));
        depositParameters.put(DATE,
                parseRawData.extractParameter(rawData, DATE, EXPENDITURE_KEYWORD));
        depositParameters.put(DESCRIPTION,
                parseRawData.extractParameter(rawData, DESCRIPTION, EXPENDITURE_KEYWORD));
        depositParameters.put(TO,
                parseRawData.extractParameter(rawData, TO, EXPENDITURE_KEYWORD));
        depositParameters.put(TRANSNO,
                parseRawData.extractParameter(rawData, TRANSNO, EXPENDITURE_KEYWORD));
        depositParameters.put(FROM,
                parseRawData.extractParameter(rawData, FROM, EXPENDITURE_KEYWORD));
        depositParameters.put(NUM,
                parseRawData.extractParameter(rawData, NUM, EXPENDITURE_KEYWORD));
    }

    /**
     * Checks if the amount entered by the user is a double and only contains numbers.
     *
     * @param valueString String to be converted to double as the user's amount.
     * @throws ParserException If the string is not a double value.
     */
    void checkAmount(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckMoney(valueString)) {
            throw new ParserException("/amount can only be positive numbers"
                    + " with at most 9 digits and 2 decimal places");
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
     * @param variable   /to or /from
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String nameString, String variable) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException(variable + " can only contain letters and at most 30 characters");
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
