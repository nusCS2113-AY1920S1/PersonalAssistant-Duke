package owlmoney.logic.parser.transfer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transfer.TransferCommand;
import owlmoney.logic.parser.ParseRawData;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;

/**
 * Represents the parsing of inputs for transferring of fund.
 */
public class ParseTransfer {

    private HashMap<String, String> transferParameters = new HashMap<String, String>();
    private ParseRawData parseRawData = new ParseRawData();
    private String rawData;
    private static final String AMOUNT_PARAMETER = "/amount";
    private static final String FROM_PARAMETER = "/from";
    private static final String TO_PARAMETER = "/to";
    private static final String DATE_PARAMETER = "/date";
    private static final String[] TRANSFER_KEYWORD = new String[] {AMOUNT_PARAMETER,
        FROM_PARAMETER, TO_PARAMETER, DATE_PARAMETER};
    private static final List<String> TRANSFER_KEYWORD_LISTS = Arrays.asList(TRANSFER_KEYWORD);

    private Date date;

    /**
     * Creates an instance of any ParseTransfer type object.
     *
     * @param data Raw user input data.
     */
    public ParseTransfer(String data) throws ParserException {
        this.rawData = data;
        checkFirstParameter();
    }

    /**
     * Checks if the first parameter is a valid parameter.
     *
     * @throws ParserException If the first parameter is invalid.
     */
    private void checkFirstParameter() throws ParserException {
        String[] rawDateSplit = rawData.split(" ", 2);
        if (!TRANSFER_KEYWORD_LISTS.contains(rawDateSplit[0])) {
            throw new ParserException("Incorrect parameter " + rawDateSplit[0]);
        }
    }

    /**
     * Fills a hash table mapping each user input to each parameter.
     *
     * @throws ParserException If duplicate parameters are detected.
     */
    public void fillHashTable() throws ParserException {
        transferParameters.put(AMOUNT_PARAMETER,
                parseRawData.extractParameter(rawData, AMOUNT_PARAMETER, TRANSFER_KEYWORD).trim());
        transferParameters.put(FROM_PARAMETER,
                parseRawData.extractParameter(rawData, FROM_PARAMETER, TRANSFER_KEYWORD).trim());
        transferParameters.put(TO_PARAMETER,
                parseRawData.extractParameter(rawData, TO_PARAMETER, TRANSFER_KEYWORD).trim());
        transferParameters.put(DATE_PARAMETER,
                parseRawData.extractParameter(rawData, DATE_PARAMETER, TRANSFER_KEYWORD).trim());
    }

    /**
     * Checks if the amount entered by the user is a double and only contains numbers.
     *
     * @param valueString String to be converted to double as the user's amount.
     * @throws ParserException If the string is not a double value.
     */
    private void checkAmount(String valueString) throws ParserException {
        if (!RegexUtil.regexCheckBankAmount(valueString)) {
            throw new ParserException("/amount can only be numbers with at most 9 digits, 2 decimal places"
                    + " and a value of at least 0");
        }
    }

    /**
     * Checks if the bank name entered by the user does not contain special character and not too long.
     *
     * @param key        /from or /to
     * @param nameString Name of bank
     * @throws ParserException If the name is too long or contain special characters.
     */
    private void checkName(String key, String nameString) throws ParserException {
        if (!RegexUtil.regexCheckName(nameString)) {
            throw new ParserException(key + " can only be alphanumeric and at most 30 characters");
        }
    }

    /**
     * Checks if the  date is of valid format and not after now.
     *
     * @param dateString Date to be checked.
     * @return Date if checks pass.
     * @throws ParserException If date format is invalid.
     */
    private Date checkDate(String dateString) throws ParserException {
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
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing inputs.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> transferIterator = transferParameters.keySet().iterator();
        while (transferIterator.hasNext()) {
            String key = transferIterator.next();
            String value = transferParameters.get(key);
            if ((value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when transferring fund.");
            }
            if (FROM_PARAMETER.equals(key)) {
                checkName(FROM_PARAMETER, value);
            }
            if (TO_PARAMETER.equals(key)) {
                checkName(TO_PARAMETER, value);
            }
            if (AMOUNT_PARAMETER.equals(key)) {
                checkAmount(value);
            }
            if (DATE_PARAMETER.equals(key)) {
                date = checkDate(value);
            }
        }
    }

    /**
     * Returns the command to execute the transferring of fund.
     *
     * @return Returns newTransferCommand to be executed.
     */
    public Command getCommand() {
        TransferCommand newTransferCommand = new TransferCommand(transferParameters.get(FROM_PARAMETER),
                transferParameters.get(TO_PARAMETER),
                Double.parseDouble(transferParameters.get(AMOUNT_PARAMETER)),
                date);
        return newTransferCommand;
    }
}
