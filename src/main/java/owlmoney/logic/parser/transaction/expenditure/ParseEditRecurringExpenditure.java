package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.EditRecurringExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for editing a recurring expenditure.
 */
public class ParseEditRecurringExpenditure extends ParseRecurringExpenditure {

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
            if (TRANSNO.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing an expenditure");
            } else if (TRANSNO.equals(key)) {
                checkInt(TRANSNO, value);
            }
            if (FROM.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing an expenditure");
            } else if (FROM.equals(key)) {
                checkName(value);
            }
            if (CATEGORY.equals(key) && !(value.isBlank() || value.isEmpty())) {
                changeCounter++;
            }
            if (AMOUNT.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkAmount(value);
                changeCounter++;
            }
            if (DESCRIPTION.equals(key) && !(value.isBlank() || value.isEmpty())) {
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
                expendituresParameters.get(FROM), expendituresParameters.get(AMOUNT),
                expendituresParameters.get(DESCRIPTION), expendituresParameters.get(CATEGORY),
                Integer.parseInt(expendituresParameters.get(TRANSNO)), this.type);
        return newEditRecurringExpenditureCommand;
    }
}
