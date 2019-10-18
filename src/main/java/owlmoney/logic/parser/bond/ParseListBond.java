package owlmoney.logic.parser.bond;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bond.ListBondCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for listing of bonds.
 */
public class ParseListBond extends ParseBond {
    private static final String LIST = "/list";

    /**
     * Creates an instance of ParseDeleteBond.
     *
     * @param data raw user input.
     * @param type the type of command.
     * @throws ParserException If there are redundant parameters or if the first parameter is not valid.
     */
    public ParseListBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(NAME, LIST);
        checkRedundantParameter(AMOUNT, LIST);
        checkRedundantParameter(RATE, LIST);
        checkRedundantParameter(DATE, LIST);
        checkRedundantParameter(YEAR, LIST);
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
            if (NUM.equals(key) && (value.isBlank() || value.isEmpty())) {
                bondParameters.put(NUM, "30");
            } else if (NUM.equals(key)) {
                checkInt(NUM, value);
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
     * @return Returns ListBondCommand to be executed.
     */
    @Override
    public Command getCommand() {
        ListBondCommand newListBondCommand =
                new ListBondCommand(bondParameters.get(FROM), Integer.parseInt(bondParameters.get(NUM)));
        return newListBondCommand;
    }
}
