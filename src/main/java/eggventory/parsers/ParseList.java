package eggventory.parsers;

import eggventory.commands.Command;
import eggventory.commands.list.ListStockCommand;
import eggventory.commands.list.ListStockTypeCommand;
import eggventory.enums.CommandType;
import eggventory.exceptions.BadInputException;

public class ParseList {

    private Command processListStock(String input) throws BadInputException {
        String[] inputArr = input.split(" ");
        if (inputArr.length > 1) { // Checking if anything extraneous is after stock
            throw new BadInputException("Usage of list: 'list stock', 'list stocktype all' or "
                    + "'list stocktype <Stock Type>'");
        }
        return new ListStockCommand(CommandType.LIST);
    }

    private Command processListStockType(String input) throws BadInputException {
        String[] inputArr = input.split(" ");
        if (inputArr.length > 2) { // Checking for extra arguments
            throw new BadInputException("Usage of list: 'list stock', 'list stocktype all' or "
                    + "'list stocktype <Stock Type>'");
        }
        return new ListStockTypeCommand(CommandType.LIST, inputArr[0]);
    }

    /**
     * Processes a user command that began with the word "list".
     * Used to differentiate between the different elements the user is able to list (stock, stocktype, etc),
     * and create a Command object to execute the listing of the element.
     * @param input String input that was given after the word "list".
     *              Describes what the user wants to list, and any other details.
     * @return a Command object which will execute the desired command.
     * @throws BadInputException If the input format was not adhered to.
     */
    public Command parse(String input) throws BadInputException {
        String[] inputArr = input.split(" ", 2);

        Command listCommand;

        switch (inputArr[0]) {
        case "stock":
            listCommand = processListStock(inputArr[0]);
            break;

        case "stocktype":
            listCommand = processListStockType(inputArr[1]);
            break;

        default:
            throw new BadInputException("Usage of list: 'list stock', 'list stocktype all' or "
                    + "'list stocktype <Stock Type>'");
        }

        return listCommand;
    }
}
