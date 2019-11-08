package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.EditRecurringExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for editing a recurring expenditure.
 */
public class ParseEditRecurringExpenditure extends ParseRecurringExpenditure {
    private static final String RESERVED_CATEGORY = "deposit";

    /**
     * Creates an instance of ParseEditRecurringExpenditure.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseEditRecurringExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If user input is invalid.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();
        int changeCounter = 0;
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (TRANSACTION_NUMBER_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing an expenditure");
            } else if (TRANSACTION_NUMBER_PARAMETER.equals(key)) {
                checkInt(TRANSACTION_NUMBER_PARAMETER, value);
            }
            if (FROM_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing an expenditure");
            } else if (FROM_PARAMETER.equals(key)) {
                checkName(value);
            }
            if (CATEGORY_PARAMETER.equals(key) && RESERVED_CATEGORY.equals(value)) {
                throw new ParserException(key + " cannot be deposit when editing a recurring expenditure");
            } else if (CATEGORY_PARAMETER.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkCategory(value);
                changeCounter++;
            }
            if (AMOUNT_PARAMETER.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkAmount(value);
                changeCounter++;
            }
            if (DESCRIPTION_PARAMETER.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkDescription(value, key);
                changeCounter++;
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    /**
     * Returns the command to execute the editing of a recurring expenditure.
     *
     * @return EditRecurringExpenditureCommand to be executed.
     */
    public Command getCommand() {
        EditRecurringExpenditureCommand newEditRecurringExpenditureCommand = new EditRecurringExpenditureCommand(
                expendituresParameters.get(FROM_PARAMETER), expendituresParameters.get(AMOUNT_PARAMETER),
                expendituresParameters.get(DESCRIPTION_PARAMETER), expendituresParameters.get(CATEGORY_PARAMETER),
                Integer.parseInt(expendituresParameters.get(TRANSACTION_NUMBER_PARAMETER)), this.type);
        return newEditRecurringExpenditureCommand;
    }
}
