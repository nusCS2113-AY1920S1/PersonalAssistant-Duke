package owlmoney.logic.parser.transaction.expenditure;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.ListExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for listing expenditures.
 */
public class ParseListExpenditure extends ParseExpenditure {
    private static final String LIST = "/list";

    /**
     * Creates an instance of ParseListExpenditure.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters.
     */
    public ParseListExpenditure(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(TRANSACTION_NUMBER_PARAMETER, LIST);
        checkRedundantParameter(AMOUNT_PARAMETER, LIST);
        checkRedundantParameter(DATE_PARAMETER, LIST);
        checkRedundantParameter(DESCRIPTION_PARAMETER, LIST);
        checkRedundantParameter(CATEGORY_PARAMETER, LIST);
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If the user input is invalid.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = expendituresParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = expendituresParameters.get(key);
            if (FROM_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when listing expenditures");
            } else if (FROM_PARAMETER.equals(key)) {
                checkName(value);
            }
            if (NUM_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                expendituresParameters.put(NUM_PARAMETER, "30");
            } else if (NUM_PARAMETER.equals(key)) {
                checkInt(NUM_PARAMETER, value);
            }
        }
    }

    /**
     * Returns the command to execute the listing of expenditures.
     *
     * @return ListExpendituresCommand to be executed.
     */
    public Command getCommand() {
        ListExpenditureCommand newListExpenditureCommand = new ListExpenditureCommand(expendituresParameters.get(
                FROM_PARAMETER),
                Integer.parseInt(expendituresParameters.get(NUM_PARAMETER)), this.type);
        return newListExpenditureCommand;
    }
}
