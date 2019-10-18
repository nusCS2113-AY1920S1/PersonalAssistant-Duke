package owlmoney.logic.parser.bond;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bond.DeleteBondCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses input by user for editing bonds.
 */

public class ParseDeleteBond extends ParseBond {
    private static final String DELETE = "/delete";

    /**
     * Creates an instance of ParseDeleteBond.
     *
     * @param data raw user input.
     * @param type the type of command.
     * @throws ParserException If there are redundant parameters or if the first parameter is not valid.
     */
    public ParseDeleteBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(AMOUNT, DELETE);
        checkRedundantParameter(RATE, DELETE);
        checkRedundantParameter(DATE, DELETE);
        checkRedundantParameter(YEAR, DELETE);
        checkRedundantParameter(NUM, DELETE);
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
            if (NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting a bond");
            } else if (NAME.equals(key)) {
                checkName(NAME, value);
            }
            if (FROM.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when deleting a bond");
            } else if (FROM.equals(key)) {
                checkName(FROM, value);
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
                new DeleteBondCommand(bondParameters.get(FROM), bondParameters.get(NAME));
        return newDeleteBondCommand;
    }
}
