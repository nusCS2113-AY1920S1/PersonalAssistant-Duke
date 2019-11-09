package owlmoney.logic.parser.find;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.find.FindBondCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for finding of bonds.
 */
public class ParseFindBond extends ParseFind {
    static final String FIND_BOND = "/find /bonds";

    /**
     * Creates an instance of ParseFindBond.
     *
     * @param data Raw user input data.
     * @param type Represents the type of object to be searched.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseFindBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(DESCRIPTION_PARAMETER, FIND_BOND);
        checkRedundantParameter(CATEGORY_PARAMETER, FIND_BOND);
        checkRedundantParameter(TO_PARAMETER, FIND_BOND);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are missing or invalid parameters.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> findIterator = findParameters.keySet().iterator();

        while (findIterator.hasNext()) {
            String key = findIterator.next();
            String value = findParameters.get(key);
            if (!DESCRIPTION_PARAMETER.equals(key) && !CATEGORY_PARAMETER.equals(key)
                    && !TO_PARAMETER.equals(key) && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when doing a search");
            }
            if (FROM_PARAMETER.equals(key)) {
                checkName(value);
            }
            if (NAME_PARAMETER.equals(key)) {
                checkName(value);
            }
        }
    }

    /**
     * Returns the command to find bonds.
     *
     * @return Returns FindBondCommand to be executed.
     */
    public Command getCommand() {
        FindBondCommand newFindBondCommand = new FindBondCommand(findParameters.get(NAME_PARAMETER), findParameters.get(
                FROM_PARAMETER));
        return newFindBondCommand;
    }
}
