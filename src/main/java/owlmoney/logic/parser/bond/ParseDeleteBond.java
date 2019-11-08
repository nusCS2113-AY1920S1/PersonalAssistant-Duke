package owlmoney.logic.parser.bond;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bond.DeleteBondCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses input by user for editing bonds.
 */
public class ParseDeleteBond extends ParseBond {
    private static final String DELETE_COMMAND = "/delete";

    /**
     * Creates an instance of ParseDeleteBond.
     *
     * @param data raw user input.
     * @param type the type of command.
     * @throws ParserException If there are redundant parameters or if the first parameter is not valid.
     */
    public ParseDeleteBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(AMOUNT_PARAMETER, DELETE_COMMAND);
        checkRedundantParameter(RATE_PARAMETER, DELETE_COMMAND);
        checkRedundantParameter(DATE_PARAMETER, DELETE_COMMAND);
        checkRedundantParameter(YEAR_PARAMETER, DELETE_COMMAND);
        checkRedundantParameter(NUM_PARAMETER, DELETE_COMMAND);
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
        while (bondIterator.hasNext()) {
            String key = bondIterator.next();
            String value = bondParameters.get(key);
            if (NAME_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting a bond");
            } else if (NAME_PARAMETER.equals(key)) {
                checkName(NAME_PARAMETER, value);
            }
            if (FROM_PARAMETER.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting a bond");
            } else if (FROM_PARAMETER.equals(key)) {
                checkName(FROM_PARAMETER, value);
            }
        }
    }

    /**
     * Returns the command to execute the deletion of a bond.
     *
     * @return Returns DeleteBondCommand to be executed.
     */
    @Override
    public Command getCommand() {
        DeleteBondCommand newDeleteBondCommand =
                new DeleteBondCommand(bondParameters.get(FROM_PARAMETER), bondParameters.get(NAME_PARAMETER));
        return newDeleteBondCommand;
    }
}
