package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.DeleteTransactionCommand;

//@@author GaryStu
/**
 * Parser class to handle deletion of a single transaction from the transactions list.
 */
public class DeleteTransactionCommandParser implements ParserInterface<DeleteTransactionCommand>  {

    /**
     * Parse user input and return DeleteTransactionCommand.
     * @param userInputStr String input by user.
     * @return <code>DeleteTransactionCommand</code> Command object demarcating the entry of transaction to be deleted.
     */
    public DeleteTransactionCommand parse(String userInputStr) {
        try {
            InputValidator.validate(userInputStr);
            String[] indexAndDate = ArgumentSplitter.splitArguments(userInputStr, "/date");
            if (indexAndDate[1].isBlank()) {
                return new DeleteTransactionCommand(indexAndDate[0]);
            } else {
                return new DeleteTransactionCommand(indexAndDate[0], indexAndDate[1]);
            }
        } catch (ProgramException e) {
            return new DeleteTransactionCommand(false, "Please enter index of transaction to delete on today's list or "
                    + "date and index of transaction to delete");
        }

    }
}
