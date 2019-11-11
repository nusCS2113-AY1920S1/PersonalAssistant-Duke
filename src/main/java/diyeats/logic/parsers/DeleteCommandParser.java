package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.DeleteCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

//@@author HashirZahir
/**
 * Parser class to handle deletion of a single item from model.
 */
public class DeleteCommandParser implements ParserInterface<DeleteCommand> {

    /**
     * Parse user input and return DeleteCommand.
     * @param userInputStr String input by user.
     * @return <code>DeleteCommand</code> Command object demarcating the entry of data to be deleted
     */
    @Override
    public DeleteCommand parse(String userInputStr)  {
        try {
            InputValidator.validate(userInputStr);
        } catch (ProgramException e) {
            return new DeleteCommand(false,"Please enter index of meal to delete on today's list or "
                    + "date and index of meal to delete. Type 'help delete' for more information.");
        }

        LocalDate deleteDate;
        String[] indexAndDate = ArgumentSplitter.splitArguments(userInputStr, "/date");

        try {
            InputValidator.validatePositiveInteger(indexAndDate[0]);
        } catch (ProgramException e) {
            return new DeleteCommand(false, e.getMessage());
        }

        int index = Integer.parseInt(indexAndDate[0]) - 1;

        try {
            if (indexAndDate[1].isEmpty()) {
                return new DeleteCommand(index);
            }
            deleteDate = LocalDate.parse(indexAndDate[1], dateFormat);
            return new DeleteCommand(index, deleteDate);
        } catch (DateTimeParseException e) {
            return new DeleteCommand(false, "Unable to parse date \"" + indexAndDate[1]
                    + "\" as a date.  Not executing command");
        }
    }
}
