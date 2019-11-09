package owlmoney.logic.parser.transaction.expenditure;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.AddExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for adding an expenditure.
 */
public class ParseAddExpenditure extends ParseExpenditure {

    private static final String ADD_COMMAND = "/add";
    private static final String DEPOSIT_CATEGORY = "DEPOSIT";
    private static final String BONDS_CATEGORY = "BONDS";
    private static final String TRANSFER_CATEGORY = "FUND TRANSFER";
    private static final String CARD_CATEGORY = "CREDIT CARD";
    private static final String[] RESERVED_CATEGORY = new String[] {
            DEPOSIT_CATEGORY, BONDS_CATEGORY, TRANSFER_CATEGORY, CARD_CATEGORY};
    private static final List<String> RESERVED_CATEGORY_LISTS = Arrays.asList(RESERVED_CATEGORY);
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
                    && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when adding a new expenditure");
            }
            if (CATEGORY_PARAMETER.equals(key) && value != null &&
                    RESERVED_CATEGORY_LISTS.contains(value.toUpperCase())) {
                throw new ParserException(key + " cannot be " + value + " when adding a new expenditure");
            } else if (CATEGORY_PARAMETER.equals(key) && (value == null || value.isBlank())) {
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
