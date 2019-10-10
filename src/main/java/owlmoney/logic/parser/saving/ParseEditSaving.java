package owlmoney.logic.parser.saving;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bank.EditSavingsCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Represents the parsing of inputs for editing a saving.
 */
public class ParseEditSaving extends ParseSaving {

    /**
     * Constructor which creates an instance of ParseEditSaving.
     *
     * @param data Raw user input date.
     * @throws ParserException If the first parameter is invalid.
     */
    public ParseEditSaving(String data) throws ParserException {
        super(data);
        checkFirstParameter();
    }

    /**
     * Checks each user input for each parameter.
     *
     * @throws ParserException If there are any invalid or missing inputs.
     */
    public void checkParameter() throws ParserException {
        Iterator<String> savingsIterator = savingsParameters.keySet().iterator();
        int changeCounter = 0;
        while (savingsIterator.hasNext()) {
            String key = savingsIterator.next();
            String value = savingsParameters.get(key);
            if (NAME.equals(key) && (value.isEmpty() || value.isBlank())) {
                throw new ParserException("/name cannot be empty.");
            } else if (!value.isEmpty() && !value.isBlank()) {
                changeCounter++;
                if (INCOME.equals(key) || AMOUNT.equals(key)) {
                    checkIncome(value);
                } else if (NAME.equals(key) || NEW_NAME.equals(key)) {
                    checkName(key, value);
                }
            }
        }
        if (changeCounter == 0) {
            throw new ParserException("Edit should have at least 1 differing parameter to change.");
        }
    }

    /**
     * Returns the command to execute the editing of a saving.
     *
     * @return Returns EditSavingsCommand to be executed.
     */
    public Command getCommand() {
        EditSavingsCommand newEditSavingsCommand = new EditSavingsCommand(savingsParameters.get(NAME),
                savingsParameters.get(INCOME), savingsParameters.get(AMOUNT), savingsParameters.get(NEW_NAME));
        return newEditSavingsCommand;
    }

}
