package owlmoney.logic.parser.card;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.card.DeleteCardCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for deleting a card.
 */
public class ParseDeleteCard extends ParseCard {
    private static final String DELETE = "/delete";

    /**
     * Constructor which creates an instance of ParseDeleteCard.
     *
     * @param data Raw user input data.
     * @throws ParserException If there are redundant parameters or if the first parameter is not valid.
     */
    public ParseDeleteCard(String data) throws ParserException {
        super(data);
        checkRedundantParameter(LIMIT, DELETE);
        checkRedundantParameter(REBATE, DELETE);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If parameter is missing or invalid.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> cardIterator = cardParameters.keySet().iterator();

        while (cardIterator.hasNext()) {
            String key = cardIterator.next();
            String value = cardParameters.get(key);
            if ((NAME.equals(key) && (value.isBlank() || value.isEmpty()))) {
                throw new ParserException(key + " cannot be empty when deleting a card");
            }
            if (NAME.equals(key)) {
                checkName(value);
            }
        }
    }

    /**
     * Returns the command to execute the deleting of card.
     *
     * @return DeleteCardCommand to be executed.
     */
    public Command getCommand() {
        DeleteCardCommand newDeleteCardCommand = new DeleteCardCommand(cardParameters.get(NAME));
        return newDeleteCardCommand;
    }

}

