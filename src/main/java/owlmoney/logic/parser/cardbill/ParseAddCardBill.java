package owlmoney.logic.parser.cardbill;

import java.time.YearMonth;
import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.cardbill.AddCardBillCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for adding a card bill.
 */
public class ParseAddCardBill extends ParseCardBill {
    private YearMonth yearMonth;

    /**
     * Creates an instance of ParseAddCardBill.
     *
     * @param data Raw data of user input to be parsed.
     * @throws ParserException If there is a redundant parameter or first parameter is not a valid type.
     */
    public ParseAddCardBill(String data) throws ParserException {
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
                throw new ParserException(key + " cannot be empty when adding making a card bill payment!");
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
     * Returns the command to execute the adding of a card bill.
     *
     * @return AddCardBillCommand to be executed.
     */
    @Override
    public Command getCommand() {
        AddCardBillCommand newAddCardBillCommand = new AddCardBillCommand(cardBillParameters.get(CARD),
                yearMonth, cardBillParameters.get(BANK));
        return newAddCardBillCommand;
    }
}
