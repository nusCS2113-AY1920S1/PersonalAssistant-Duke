package owlmoney.logic.parser.bond;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bond.EditBondCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses input by user for editing bonds.
 */
public class ParseEditBond extends ParseBond {
    private static final String EDIT_COMMAND = "/edit";

    /**
     * Creates an instance of ParseEditBond.
     *
     * @param data raw user input.
     * @param type the type of command.
     * @throws ParserException If there are redundant parameters or if the first parameter is not valid.
     */
    public ParseEditBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(AMOUNT_PARAMETER, EDIT_COMMAND);
        checkRedundantParameter(DATE_PARAMETER, EDIT_COMMAND);
        checkRedundantParameter(NUM_PARAMETER, EDIT_COMMAND);
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
            if (NAME_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when editing a bond");
            } else if (NAME_PARAMETER.equals(key)) {
                checkName(NAME_PARAMETER, value);
            }
            if (FROM_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when editing a bond");
            } else if (FROM_PARAMETER.equals(key)) {
                checkName(FROM_PARAMETER, value);
            }
            if (RATE_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
                checkInterestRate(value);
                changeCounter++;
            }
            if (YEAR_PARAMETER.equals(key) && !(value == null || value.isBlank())) {
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
                new EditBondCommand(bondParameters.get(FROM_PARAMETER),
                        bondParameters.get(NAME_PARAMETER),
                        bondParameters.get(RATE_PARAMETER),
                        bondParameters.get(YEAR_PARAMETER));
        return newEditBondCommand;
    }
}
