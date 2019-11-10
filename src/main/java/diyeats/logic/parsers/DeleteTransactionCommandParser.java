package diyeats.logic.parsers;

//@@author GaryStu

import diyeats.commons.exceptions.ProgramException;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * Parser class to handle deletion of a single transaction from the transactions list.
 */
public class DeleteTransactionCommandParser {

    /**
     * Parse user input and return DeleteTransactionCommand.
     * @param userInputStr String input by user.
     * @return <code>DeleteTransactionCommand</code> Command object demarcating the entry of transaction to be deleted.
     * @throws ProgramException
     */
    public DeleteTransactionCommandParser parse(String userInputStr) throws ProgramException {
        HashMap<String, String> argumentInfoMap;
        LocalDate localDate = LocalDate.now();
        InputValidator.validate(userInputStr);
    }
}
