package owlmoney.logic.parser.saving;

import java.util.Iterator;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.bank.EditSavingsCommand;
import owlmoney.logic.parser.exception.ParserException;

/**
 * Parses the inputs for editing a saving.
 */
public class ParseEditSaving extends ParseSaving {

    /**
     * Creates an instance of ParseEditSaving.
     *
     * @param data Raw user input data.
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
            } else if (NAME.equals(key)) {
                checkName(NAME, value);
            }
            if (INCOME.equals(key) && !(value.isEmpty() || value.isBlank())) {
                checkIncome(value);
                changeCounter++;
            }
            if (AMOUNT.equals(key) && !(value.isEmpty() || value.isBlank())) {
                checkAmount(value);
                changeCounter++;
            }
            if (NEW_NAME.equals(key) && !(value.isEmpty() || value.isBlank())) {
                checkName(NEW_NAME, value);
                changeCounter++;
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
