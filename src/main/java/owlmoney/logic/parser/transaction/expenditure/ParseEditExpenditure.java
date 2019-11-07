package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.EditExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for editing an expenditure.
 */
public class ParseEditExpenditure extends ParseExpenditure {
    private static final String EDIT_COMMAND = "/edit";
    private static final String RESERVED_CATEGORY = "deposit";

    /**
     * Creates an instance of ParseEditExpenditure.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseEditExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(NUM_PARAMETER, EDIT_COMMAND);
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
                throw new ParserException(key + " cannot be deposit when editing an expenditure");
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
            if (DATE_PARAMETER.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkDate(value);
                changeCounter++;
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    /**
     * Returns the command to execute the editing of an expenditure.
     *
     * @return EditExpenditureCommand to be executed.
     */
    public Command getCommand() {
        EditExpenditureCommand newEditExpenditureCommand = new EditExpenditureCommand(expendituresParameters.get(
                FROM_PARAMETER),
                expendituresParameters.get(AMOUNT_PARAMETER), expendituresParameters.get(DATE_PARAMETER),
                expendituresParameters.get(DESCRIPTION_PARAMETER), expendituresParameters.get(CATEGORY_PARAMETER),
                Integer.parseInt(expendituresParameters.get(TRANSACTION_NUMBER_PARAMETER)), this.type);
        return newEditExpenditureCommand;
    }
}
