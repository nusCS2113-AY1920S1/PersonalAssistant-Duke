package owlmoney.logic.parser.find;

import java.util.Date;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.find.FindBondCommand;
import owlmoney.logic.parser.exception.ParserException;

public class ParseFindBond extends ParseFind {
    /**
     * Constructor which creates an instance of ParseAddExpenditure.
     *
     * @param data Raw user input date.
     * @param type Represents type of expenditure to be added.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseFindBond(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(DESCRIPTION, ISFIND);
        checkRedundantParameter(CATEGORY, ISFIND);
        checkRedundantParameter(TO, ISFIND);
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
            if (!DESCRIPTION.equals(key) && !CATEGORY.equals(key) && !TO.equals(key)
                    && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when doing a search");
            }
            if (FROM.equals(key)) {
                checkName(value);
            }
            if (NAME.equals(key)) {
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
        FindBondCommand newFindBondCommand = new FindBondCommand(findParameters.get(NAME), findParameters.get(FROM));
        return newFindBondCommand;
    }
}
