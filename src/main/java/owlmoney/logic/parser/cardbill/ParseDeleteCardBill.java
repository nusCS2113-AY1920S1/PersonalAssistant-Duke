package owlmoney.logic.parser.cardbill;

import java.time.YearMonth;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.cardbill.DeleteCardBillCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for deleting a card bill.
 */
public class ParseDeleteCardBill extends ParseCardBill {
    private YearMonth yearMonth;

    /**
     * Creates an instance of ParseAddCardBill.
     *
     * @param data Raw data of user input to be parsed.
     * @throws ParserException If there is a redundant parameter or first parameter is not a valid type.
     */
    public ParseDeleteCardBill(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing input.
     */
    @Override
    public void checkParameter() throws ParserException {
        Iterator<String> cardBillIterator = cardBillParameters.keySet().iterator();

        while (cardBillIterator.hasNext()) {
            String key = cardBillIterator.next();
            String value = cardBillParameters.get(key);
            if (value.isBlank() || value.isEmpty()) {
                throw new ParserException(key + " cannot be empty when deleting a card bill payment!");
            }
            if (CARD.equals(key)) {
                checkName(value, "Card");
            }
            if (BANK.equals(key)) {
                checkName(value, "Bank");
            }
            if (DATE.equals(key)) {
                yearMonth = checkDate(value);
            }
        }
    }

    /**
     * Returns the command to execute the deleting of a card bill.
     *
     * @return DeleteCardBillCommand to be executed.
     */
    @Override
    public Command getCommand() {
        DeleteCardBillCommand newDeleteCardBillCommand =
                new DeleteCardBillCommand(cardBillParameters.get(CARD), yearMonth,
                        cardBillParameters.get(BANK));
        return newDeleteCardBillCommand;
    }
}
