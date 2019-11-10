package owlmoney.logic.parser.cardbill;

import static owlmoney.commons.log.LogsCenter.getLogger;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

/**
 * Abstracts common CardBill methods and functions where the child parsers will inherit from.
 */
public abstract class ParseCardBill {
    HashMap<String, String> cardBillParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    static final String CARD_PARAMETER = "/card";
    static final String BANK_PARAMETER = "/bank";
    static final String DATE_PARAMETER = "/date";
    private static final String FIRST_DAY = "01/";
    private static final String[] CARD_BILL_KEYWORD = new String[] {
        CARD_PARAMETER, BANK_PARAMETER, DATE_PARAMETER
    };
    private static final List<String> CARD_BILL_KEYWORD_LISTS = Arrays.asList(CARD_BILL_KEYWORD);
    static final Logger logger = getLogger(ParseCardBill.class);

    /**
     * Creates an instance of CardBill object.
     *
     * @param data Raw user input date.
     */
    ParseCardBill(String data) {
        this.rawData = data;
    }

    /**
     * Fills the hash table to map each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        cardBillParameters.put(CARD_PARAMETER,
                parseRawData.extractParameter(rawData, CARD_PARAMETER, CARD_BILL_KEYWORD).trim());
        cardBillParameters.put(BANK_PARAMETER,
                parseRawData.extractParameter(rawData, BANK_PARAMETER, CARD_BILL_KEYWORD).trim());
        cardBillParameters.put(DATE_PARAMETER,
                parseRawData.extractParameter(rawData, DATE_PARAMETER, CARD_BILL_KEYWORD).trim());
    }

    /**
     * Checks if the first parameter is a valid parameter.
     *
     * @throws ParserException If the first parameter is invalid.
     */
    void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!CARD_BILL_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            logger.warning("Incorrect parameter " + rawDateSplit[0]);
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Checks if the card name entered by the user does not contain special character and not too long.
     *
     * @param nameString Name of the card
     * @throws ParserException If the name is too long or contain special characters.
     */
    void checkName(String nameString, String type) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            logger.warning(type + " name can only contain letters and at most 30 characters");
            throw new ParserException(type + " name can only contain letters and at most 30 characters");
        }
    }

    /**
     * Checks if the date is of valid YearMonth format and not after now.
     *
     * @param yearMonthString Date to be checked.
     * @return Date if checks pass.
     * @throws ParserException If date format is invalid.
     */
    YearMonth checkDate(String yearMonthString) throws ParserException, DateTimeParseException {
        if (RegexUtil.regexCheckMonthYearFormat(yearMonthString)) {
            try {
                String dateString = FIRST_DAY + yearMonthString;
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(dateString, dateFormat);
                if (localDate.compareTo(LocalDate.now()) > 0) {
                    logger.warning("/date cannot be after this month");
                    throw new ParserException("/date cannot be after this month");
                }
                YearMonth yearMonthDate = YearMonth.from(localDate);
                return yearMonthDate;

            } catch (DateTimeParseException e) {
                logger.warning("Parser Error: Incorrect date format");
                throw new ParserException("Parser Error: Incorrect date format. "
                        + "Date format is mm/yyyy in year range of 1900-2099");
            }
        }
        logger.warning("Parser Error: Incorrect date format");
        throw new ParserException("Incorrect date format."
                + " Date format is mm/yyyy in year range of 1900-2099");
    }

    /**
     * Abstract method where each card parser performs different checks on the user entered parameters.
     *
     * @throws ParserException If any parameters fail the check.
     */
    public abstract void checkParameter() throws ParserException;

    /**
     * Abstract method where each card parser creates different commands.
     *
     * @return Command to be executed.
     */
    public abstract Command getCommand();
}
