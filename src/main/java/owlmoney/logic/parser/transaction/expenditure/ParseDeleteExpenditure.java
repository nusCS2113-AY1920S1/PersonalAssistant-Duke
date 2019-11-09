package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.DeleteExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for deleting an expenditure.
 */
public class ParseDeleteExpenditure extends ParseExpenditure {

    private static final String DELETE_COMMAND = "/delete";

    /**
     * Creates an instance of ParseDeleteExpenditure.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or if first parameter is invalid.
     */
    public ParseDeleteExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(AMOUNT_PARAMETER, DELETE_COMMAND);
        checkRedundantParameter(CATEGORY_PARAMETER, DELETE_COMMAND);
        checkRedundantParameter(DESCRIPTION_PARAMETER, DELETE_COMMAND);
        checkRedundantParameter(DATE_PARAMETER, DELETE_COMMAND);
        checkRedundantParameter(NUM_PARAMETER, DELETE_COMMAND);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If parameter is missing or invalid.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if ((TRANSACTION_NUMBER_PARAMETER.equals(key) || FROM_PARAMETER.equals(key))
                    && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting an expenditure");
            }
            if (TRANSACTION_NUMBER_PARAMETER.equals(key)) {
                checkInt(TRANSACTION_NUMBER_PARAMETER, value);
            }
            if (FROM_PARAMETER.equals(key)) {
                checkName(value);
            }
        }
    }

    /**
     * Returns the command to execute the deletion of an expenditure.
     *
     * @return Returns DeletionExpenditureCommand to be executed.
     */
    public Command getCommand() {
        DeleteExpenditureCommand newDeleteExpenditureCommand =
                new DeleteExpenditureCommand(Integer.parseInt(expendituresParameters.get(TRANSACTION_NUMBER_PARAMETER)),
                        expendituresParameters.get(FROM_PARAMETER), this.type);
        return newDeleteExpenditureCommand;
    }
}
