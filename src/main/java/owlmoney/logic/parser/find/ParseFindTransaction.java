package owlmoney.logic.parser.find;

import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.find.FindTransactionCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for finding of transactions.
 */
public class ParseFindTransaction extends ParseFind {

    /**
     * Creates an instance of ParseFindTransaction.
     *
     * @param data Raw user input data.
     * @param type Represents the type of object to be searched.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseFindTransaction(String data, String type) throws ParserException {
        super(data, type);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are missing or invalid parameters.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> findIterator = findParameters.keySet().iterator();
        boolean isFromExist = false;
        boolean isToExist = false;
        int findCounter = 0;
        Date fromDate = new Date();
        Date toDate = new Date();
        while (findIterator.hasNext()) {
            String key = findIterator.next();
            String value = findParameters.get(key);
            if (FROM_PARAMETER.equals(key) && !(value.isBlank() || value.isEmpty())) {
                fromDate = checkDate(value);
                isFromExist = true;
                findCounter++;
            }
            if (TO_PARAMETER.equals(key) && !(value.isBlank() || value.isEmpty())) {
                toDate = checkDate(value);
                isToExist = true;
                findCounter++;
            }
            if (DESCRIPTION_PARAMETER.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkDescription(value);
                findCounter++;
            }
            if (CATEGORY_PARAMETER.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkCategory(value);
                findCounter++;
            }
            if (NAME_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when finding transaction");
            } else if (NAME_PARAMETER.equals(key)) {
                checkName(value);
            }
        }
        if ((isFromExist && !isToExist) || (isToExist && !isFromExist)) {
            throw new ParserException("/from and /to both must exist to be a valid command.");
        } else if (isFromExist && isToExist) {
            checkDateRange(fromDate, toDate);
        }
        if (findCounter == 0) {
            throw new ParserException("Finding of transaction should have at least 1 parameter "
                    + "which is not empty for find.");
        }
    }

    /**
     * Checks the to date is not before the from date.
     *
     * @throws ParserException If the to date is before the from date.
     */
    private void checkDateRange(Date fromDate, Date toDate) throws ParserException {
        if (toDate.before(fromDate)) {
            throw new ParserException("/to date cannot be before /from date.");
        }
    }

    /**
     * Returns the command to find transaction.
     *
     * @return Returns FindTransactionCommand to be executed.
     */
    public Command getCommand() {
        FindTransactionCommand newFindTransactionCommand = new FindTransactionCommand(
            findParameters.get(NAME_PARAMETER), findParameters.get(FROM_PARAMETER),
            findParameters.get(TO_PARAMETER),
            findParameters.get(DESCRIPTION_PARAMETER),
            findParameters.get(CATEGORY_PARAMETER),
            this.type);
        return newFindTransactionCommand;

    }
}

