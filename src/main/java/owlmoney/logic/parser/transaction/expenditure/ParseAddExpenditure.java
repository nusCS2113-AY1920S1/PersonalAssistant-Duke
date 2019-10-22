package owlmoney.logic.parser.transaction.expenditure;

import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.transaction.AddExpenditureCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for adding a expenditure.
 */
public class ParseAddExpenditure extends ParseExpenditure {

    static final String ADD = "/add";
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
        checkRedundantParameter(TRANSNO, ADD);
        checkRedundantParameter(NUM, ADD);
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
            if (!TRANSNO.equals(key) && !NUM.equals(key) && !CATEGORY.equals(key)
                    && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new expenditure");
            }
            if (CATEGORY.equals(key) && "deposit".equals(value)) {
                throw new ParserException(key + " cannot be deposit when adding a new expenditure");
            } else if (CATEGORY.equals(key) && (value.isBlank() || value.isEmpty())) {
                expendituresParameters.put(CATEGORY, "Miscellaneous");
            } else if (CATEGORY.equals(key)) {
                checkDescription(value, key);
            }
            if (AMOUNT.equals(key)) {
                checkAmount(value);
            }
            if (DESCRIPTION.equals(key)) {
                checkDescription(value, key);
            }
            if (DATE.equals(key)) {
                date = checkDate(value);
            }
            if (FROM.equals(key)) {
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
        AddExpenditureCommand newAddExpenditureCommand = new AddExpenditureCommand(expendituresParameters.get(FROM),
                Double.parseDouble(expendituresParameters.get(AMOUNT)), date,
                (expendituresParameters.get(DESCRIPTION)), (expendituresParameters.get(CATEGORY)), this.type);
        return newAddExpenditureCommand;
    }
}
