package owlmoney.logic.parser.find;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.find.FindRecurringExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseFindRecurring extends ParseFind {
    /**
     * Creates an instance of ParseFindTransaction.
     *
     * @param data Raw user input data.
     * @param type Represents the type of object to be searched.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseFindRecurring(String data, String type) throws ParserException {
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
        int findCounter = 0;
        while (findIterator.hasNext()) {
            String key = findIterator.next();
            String value = findParameters.get(key);
            if (DESCRIPTION.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkDescription(value);
                findCounter++;
            }
            if (CATEGORY.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkCategory(value);
                findCounter++;
            }
            if (NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when finding recurring expenditure");
            } else if (NAME.equals(key)) {
                checkName(value);
            }
        }
        if (findCounter == 0) {
            throw new ParserException("Finding of recurring expenditure should have at least 1 parameter "
                    + "which is not empty for find.");
        }
    }

    /**
     * Returns the command to find transaction.
     *
     * @return Returns FindTransactionCommand to be executed.
     */
    public Command getCommand() {
        FindRecurringExpenditureCommand newFindRecurringExpenditureCommand = new FindRecurringExpenditureCommand(
                findParameters.get(NAME), findParameters.get(DESCRIPTION), findParameters.get(CATEGORY), this.type);
        return newFindRecurringExpenditureCommand;

    }
}
