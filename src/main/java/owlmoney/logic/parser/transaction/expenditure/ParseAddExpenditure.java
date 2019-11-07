package owlmoney.logic.parser.transaction.expenditure;

import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.AddExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for adding an expenditure.
 */
public class ParseAddExpenditure extends ParseExpenditure {

    private static final String ADD_COMMAND = "/add";
    private static final String RESERVED_CATEGORY = "deposit";
    private Date date;

    /**
     * Creates an instance of ParseAddExpenditure.
     *
     * @param data Raw user input data.
     * @param type Represents type of expenditure to be added.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseAddExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(TRANSACTION_NUMBER_PARAMETER, ADD_COMMAND);
        checkRedundantParameter(NUM_PARAMETER, ADD_COMMAND);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are missing or invalid parameters.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (!TRANSACTION_NUMBER_PARAMETER.equals(key) && !NUM_PARAMETER.equals(key)
                    && !CATEGORY_PARAMETER.equals(key)
                    && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new expenditure");
            }
            if (CATEGORY_PARAMETER.equals(key) && RESERVED_CATEGORY.equals(value)) {
                throw new ParserException(key + " cannot be deposit when adding a new expenditure");
            } else if (CATEGORY_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                expendituresParameters.put(CATEGORY_PARAMETER, "Miscellaneous");
            } else if (CATEGORY_PARAMETER.equals(key)) {
                checkCategory(value);
            }
            if (AMOUNT_PARAMETER.equals(key)) {
                checkAmount(value);
            }
            if (DESCRIPTION_PARAMETER.equals(key)) {
                checkDescription(value, key);
            }
            if (DATE_PARAMETER.equals(key)) {
                date = checkDate(value);
            }
            if (FROM_PARAMETER.equals(key)) {
                checkName(value);
            }
        }
    }

    /**
     * Returns the command to add a new expenditure.
     *
     * @return Returns AddExpenditureCommand to be executed.
     */
    public Command getCommand() {
        AddExpenditureCommand newAddExpenditureCommand = new AddExpenditureCommand(expendituresParameters.get(
                FROM_PARAMETER),
                Double.parseDouble(expendituresParameters.get(AMOUNT_PARAMETER)), date,
                (expendituresParameters.get(DESCRIPTION_PARAMETER)),
                (expendituresParameters.get(CATEGORY_PARAMETER)), this.type);
        return newAddExpenditureCommand;
    }
}
