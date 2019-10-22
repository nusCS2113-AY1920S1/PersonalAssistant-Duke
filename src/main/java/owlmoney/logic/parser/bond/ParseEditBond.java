package owlmoney.logic.parser.bond;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bond.EditBondCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses input by user for editing bonds.
 */
public class ParseEditBond extends ParseBond {
    private static final String EDIT = "/edit";

    /**
     * Creates an instance of ParseEditBond.
     *
     * @param data raw user input.
     * @param type the type of command.
     * @throws ParserException If there are redundant parameters or if the first parameter is not valid.
     */
    public ParseEditBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(AMOUNT, EDIT);
        checkRedundantParameter(DATE, EDIT);
        checkRedundantParameter(NUM, EDIT);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing inputs.
     */
    @Override
    public void checkParameter() throws ParserException {
        Iterator<String> bondIterator = bondParameters.keySet().iterator();
        int changeCounter = 0;
        while (bondIterator.hasNext()) {
            String key = bondIterator.next();
            String value = bondParameters.get(key);
            if (NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing a bond");
            } else if (NAME.equals(key)) {
                checkName(NAME, value);
            }
            if (FROM.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when editing a bond");
            } else if (FROM.equals(key)) {
                checkName(FROM, value);
            }
            if (RATE.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkInterestRate(value);
                changeCounter++;
            }
            if (YEAR.equals(key) && !(value.isBlank() || value.isEmpty())) {
                checkYear(value);
                changeCounter++;
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    /**
     * Returns the command to execute the editing of a bond.
     *
     * @return Returns EditBondCommand to be executed.
     */
    @Override
    public Command getCommand() {
        EditBondCommand newEditBondCommand =
                new EditBondCommand(bondParameters.get(FROM),
                        bondParameters.get(NAME),
                        bondParameters.get(RATE),
                        bondParameters.get(YEAR));
        return newEditBondCommand;
    }
}
