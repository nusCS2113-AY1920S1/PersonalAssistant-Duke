package owlmoney.logic.parser.bond;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bond.ListBondCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses input by user for listing bonds.
 */
public class ParseListBond extends ParseBond {
    private static final String LIST_COMMAND = "/list";

    /**
     * Creates an instance of ParseDeleteBond.
     *
     * @param data raw user input.
     * @param type the type of command.
     * @throws ParserException If there are redundant parameters or if the first parameter is not valid.
     */
    public ParseListBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(NAME_PARAMETER, LIST_COMMAND);
        checkRedundantParameter(AMOUNT_PARAMETER, LIST_COMMAND);
        checkRedundantParameter(RATE_PARAMETER, LIST_COMMAND);
        checkRedundantParameter(DATE_PARAMETER, LIST_COMMAND);
        checkRedundantParameter(YEAR_PARAMETER, LIST_COMMAND);
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
            if (NUM_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                bondParameters.put(NUM_PARAMETER, "30");
            } else if (NUM_PARAMETER.equals(key)) {
                checkInt(NUM_PARAMETER, value);
            }
            if (FROM_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when deleting a bond");
            } else if (FROM_PARAMETER.equals(key)) {
                checkName(FROM_PARAMETER, value);
            }
        }
    }

    /**
     * Returns the command to execute the deletion of a bond.
     *
     * @return Returns ListBondCommand to be executed.
     */
    @Override
    public Command getCommand() {
        ListBondCommand newListBondCommand =
                new ListBondCommand(bondParameters.get(FROM_PARAMETER), Integer.parseInt(bondParameters.get(
                        NUM_PARAMETER)));
        return newListBondCommand;
    }
}
