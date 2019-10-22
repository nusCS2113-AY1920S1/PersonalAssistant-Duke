package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.EditExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for editing an expenditure.
 */
public class ParseEditExpenditure extends ParseExpenditure {
    private static final String EDIT = "/edit";

    /**
     * Creates an instance of ParseEditExpenditure.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseEditExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(NUM, EDIT);
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
            if (DATE.equals(key) && !(value.isBlank() || value.isEmpty())) {
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
        EditExpenditureCommand newEditExpenditureCommand = new EditExpenditureCommand(expendituresParameters.get(FROM),
                expendituresParameters.get(AMOUNT), expendituresParameters.get(DATE),
                expendituresParameters.get(DESCRIPTION), expendituresParameters.get(CATEGORY),
                Integer.parseInt(expendituresParameters.get(TRANSNO)), this.type);
        return newEditExpenditureCommand;
    }
}
