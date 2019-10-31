package eggventory.logic.parsers;

import eggventory.commons.exceptions.InsufficientInfoException;
import eggventory.logic.commands.Command;
import eggventory.logic.commands.list.ListPersonCommand;
import eggventory.logic.commands.list.ListStockCommand;
import eggventory.logic.commands.list.ListStockTypeCommand;
import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;

public class ParseList {

    private final String listErrorMessageGeneric = "Usage of list: 'list stock', 'list stocktype all', "
            + "'list stocktype <Stock Type>', or 'list person'";

    private Command processListStock(String input) throws BadInputException {
        String[] inputArr = input.split(" ");
        if (inputArr.length > 1) { // Checking if anything extraneous is after stock
            throw new BadInputException(listErrorMessageGeneric);
        }
        return new ListStockCommand(CommandType.LIST);
    }

    private Command processListStockType(String input) throws BadInputException {
        String[] inputArr = input.split(" ");
        if (inputArr.length > 2) { // Checking for extra arguments
            throw new BadInputException(listErrorMessageGeneric);
        }
        return new ListStockTypeCommand(CommandType.LIST, inputArr[0]);
    }

    private Command processListPerson(String input) throws BadInputException {
        String[] inputArr = input.split(" +");
        if (inputArr.length > 1) {
            throw new BadInputException(listErrorMessageGeneric);
        }

        return new ListPersonCommand(CommandType.LIST);

    }

    /**
     * Processes a user command that began with the word "list".
     * Used to differentiate between the different elements the user is able to list (stock, stocktype, etc),
     * and create a Command object to execute the listing of the element.
     * @param inputString String input that was given after the word "list".
     *              Describes what the user wants to list, and any other details.
     * @return a Command object which will execute the desired command.
     * @throws BadInputException If the input format was not adhered to.
     */
    public Command parse(String inputString) throws BadInputException, InsufficientInfoException {
        String[] inputArr = inputString.split(" ", 2);

        Command listCommand;

        switch (inputArr[0]) {
        case "stock":
            listCommand = processListStock(inputArr[0]);
            break;

        case "stocktype":
            if (!Parser.isCommandComplete(inputString, 1)) {
                throw new InsufficientInfoException("Please enter stock information after the 'list' command in"
                        + " this format:\nlist stocktype <StockType> OR list stocktype all");
            }
            listCommand = processListStockType(inputArr[1]);
            break;

        case "person":
            listCommand = processListPerson(inputArr[0]);
            break;

        default:
            throw new BadInputException("Usage of list: 'list stock', 'list stocktype all' or "
                    + "'list stocktype <Stock Type>'");
        }

        return listCommand;
    }


}
