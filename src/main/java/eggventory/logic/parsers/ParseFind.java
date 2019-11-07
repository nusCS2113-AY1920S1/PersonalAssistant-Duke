package eggventory.logic.parsers;

import eggventory.commons.exceptions.InsufficientInfoException;
import eggventory.logic.commands.Command;
import eggventory.logic.commands.CommandDictionary;
import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.logic.commands.find.FindDescriptionCommand;


//@@author yanprosobo
public class ParseFind {

    private Command processFindDescription(String searchQuery) throws BadInputException {
        return new FindDescriptionCommand(CommandType.FIND, searchQuery);
    }

    /**
     * Processes a user command that began with the word "find".
     * Used to filter between the different properties the user wants to search through (stock, description, etc),
     * and create a Command object to execute the finding of the query.
     * @param inputString String input that was given after the word "find".
     *        Describes what the user wants to filter, and the word query to search for.
     *        This param can only have "description" because other filters are redundant.
     * @return a Command object which will execute the desired command.
     * @throws BadInputException If the input format was not adhered to.
     */
    public Command parse(String inputString) throws BadInputException, InsufficientInfoException {
        String[] inputArr = inputString.split(" ", 2);
        String option = inputArr[0];
        Command findCommand;

        switch (option) {
        case "description":
            if (!Parser.isCommandComplete(inputString, 1)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("find description"));
            }
            String searchQuery = inputArr[1];
            findCommand = processFindDescription(searchQuery);
            break;
        default:
            throw new BadInputException(CommandDictionary.getCommandUsage("find"));
        }

        return findCommand;
    }

}
//@@author
