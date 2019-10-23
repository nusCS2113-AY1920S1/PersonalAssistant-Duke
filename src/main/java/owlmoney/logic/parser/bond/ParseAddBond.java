package owlmoney.logic.parser.bond;

import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bond.AddBondCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses input by user for adding bonds.
 */
public class ParseAddBond extends ParseBond {
    private static final String ADD = "/add";
    private Date date;

    /**
     * Creates an instance of ParseAddBond.
     *
     * @param data Raw data of user input to be parsed.
     * @throws ParserException If there is a redundant parameter or first parameter is not a valid type.
     */
    public ParseAddBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(NUM, ADD);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing input.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> bondIterator = bondParameters.keySet().iterator();

        while (bondIterator.hasNext()) {
            String key = bondIterator.next();
            String value = bondParameters.get(key);
            if (!NUM.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding bond");
            }
            if (NAME.equals(key)) {
                checkName(NAME, value);
            }
            if (FROM.equals(key)) {
                checkName(FROM, value);
            }
            if (AMOUNT.equals(key)) {
                checkAmount(value);
            }
            if (RATE.equals(key)) {
                checkInterestRate(value);
            }
            if (DATE.equals(key)) {
                date = checkDate(value);
            }
            if (YEAR.equals(key)) {
                checkYear(value);
            }
        }
    }

    /**
     * Returns the command to execute the adding of a new bond.
     *
     * @return AddBondCommand to be executed.
     */
    public Command getCommand() {
        AddBondCommand newAddBondCommand = new AddBondCommand(bondParameters.get(NAME),
                bondParameters.get(FROM),
                Double.parseDouble(bondParameters.get(AMOUNT)),
                Double.parseDouble(bondParameters.get(RATE)),
                this.date,
                Integer.parseInt(bondParameters.get(YEAR)), this.type);
        return newAddBondCommand;
    }
}
