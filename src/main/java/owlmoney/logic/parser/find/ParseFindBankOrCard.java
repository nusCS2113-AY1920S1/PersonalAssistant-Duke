package owlmoney.logic.parser.find;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.find.FindBankOrCardCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for finding of bank or card.
 */
public class ParseFindBankOrCard extends ParseFind {
    private static final String FIND_BANK_OR_CARD = "/find /savings or /find /investment or /find /card";

    /**
     * Creates an instance of ParseFindBankOrCard.
     *
     * @param data Raw user input data.
     * @param type Represents the type of object to be searched.
     * @throws ParserException If there are redundant parameters or first parameter is invalid.
     */
    public ParseFindBankOrCard(String data, String type) throws ParserException {
        super(data, type);
        checkRedundantParameter(DESCRIPTION_PARAMETER, FIND_BANK_OR_CARD);
        checkRedundantParameter(CATEGORY_PARAMETER, FIND_BANK_OR_CARD);
        checkRedundantParameter(FROM_PARAMETER, FIND_BANK_OR_CARD);
        checkRedundantParameter(TO_PARAMETER, FIND_BANK_OR_CARD);
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
                    && !FROM_PARAMETER.equals(key) && !TO_PARAMETER.equals(key)
                    && (value == null || value.isBlank())) {
                throw new ParserException(key + " cannot be empty when doing a search");
            }
            if (NAME_PARAMETER.equals(key)) {
                checkName(value);
            }
        }
    }

    /**
     * Returns the command to find bank or card.
     *
     * @return Returns FindBankOrCardCommand to be executed.
     */
    public Command getCommand() {
        FindBankOrCardCommand newFindBankOrCardCommand =
                new FindBankOrCardCommand(findParameters.get(NAME_PARAMETER), this.type);
        return newFindBankOrCardCommand;
    }
}
