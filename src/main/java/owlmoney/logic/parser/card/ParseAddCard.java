package owlmoney.logic.parser.card;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.card.AddCardCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for adding a new card.
 */
public class ParseAddCard extends ParseCard {

    /**
     * Constructor which creates an instance of ParseAddCard.
     *
     * @param data Raw data of user input to be parsed.
     * @throws ParserException If there is a redundant parameter or first parameter is not a valid type.
     */
    public ParseAddCard(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing input.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = cardParameters.keySet().iterator();

        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = cardParameters.get(key);
            if (!NEW_NAME.equals(key) && (value.isBlank() || value.isEmpty())) {
                throw new ParserException(key + " cannot be empty when adding a new card");
            }
            if (LIMIT.equals(key)) {
                checkDouble(value, LIMIT);
            }
            if (REBATE.equals(key)) {
                checkDouble(value, REBATE);
            }
            if (NAME.equals(key)) {
                checkName(value);
            }
        }
    }

    /**
     * Returns the command to execute the adding of a new card.
     *
     * @return AddCardCommand to be executed.
     */
    public Command getCommand() {
        AddCardCommand newAddCardCommand = new AddCardCommand(cardParameters.get(NAME),
                Double.parseDouble(cardParameters.get(LIMIT)), Double.parseDouble(cardParameters.get(REBATE)));
        return newAddCardCommand;
    }
}
