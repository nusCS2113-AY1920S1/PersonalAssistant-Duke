package eggventory.parsers;

import eggventory.commands.Command;
import eggventory.commands.edit.EditStockCommand;
import eggventory.commands.edit.EditStockTypeCommand;
import eggventory.enums.CommandType;
import eggventory.enums.StockProperty;
import eggventory.exceptions.BadInputException;
import eggventory.exceptions.InsufficientInfoException;


public class ParseEdit {
    private Command processEditStock(String input) throws BadInputException {
        String[] addInput = input.split(" +", 3); //<stockCode> <property> <newValue>

        String stockCode = addInput[0];
        switch (addInput[1]) {
        case "stockCode":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.STOCKCODE, addInput[2]);
        case "quantity":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.QUANTITY, addInput[2]);
        case "loaned":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.LOANED, addInput[2]);
        case "lost":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.LOST, addInput[2]);
        case "description":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.DESCRIPTION, addInput[2]);
        case "minimum":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.MINIMUM, addInput[2]);
        default:
            throw new BadInputException("The property you are trying to edit does not exist.");
        }
    }

    private Command processEditStockType(String input) throws BadInputException {
        String[] addInput = input.split(" +", 2);

        if (Parser.isReserved(addInput[1])) {
            throw new BadInputException("'" + addInput[0] + "' is an invalid name as it is a keyword"
                    + " for an existing command.");
        }

        return new EditStockTypeCommand(CommandType.EDIT, addInput[0], addInput[1]);
    }

    /**
     * Parses the second word in edit command to determine whether edit stock or edit stocktype.
     * @param inputString String input stating the details of the edit command.
     * @return The editCommand corresponding to either editStock or editStockType depending on the input.
     * @throws InsufficientInfoException Exception when input has less parameters than required.
     * @throws BadInputException Exception when error in the command attribute (not stock or stocktype).
     */
    public Command parse(String inputString) throws InsufficientInfoException, BadInputException {
        String[] addInput = inputString.split(" +", 2); // <stock/stocktype> <...>
        Command editCommand;

        switch (addInput[0]) {
        case "stock":
            if (!Parser.isCommandComplete(inputString, 3)) {
                throw new InsufficientInfoException("Please enter the edit information after the 'edit' command in"
                        + "this format:\nedit stock <stockCode> <property> <newValue>");
            }
            editCommand = processEditStock(addInput[1]);
            break;

        case "stocktype":
            if (!Parser.isCommandComplete(inputString, 2)) {
                throw new InsufficientInfoException("Please enter the edit information after the 'edit' command in"
                        + "this format:\nedit stocktype <StockType> <New StockType Name>");
            }
            editCommand = processEditStockType(addInput[1]);
            break;

        default:
            throw new BadInputException("Unexpected value:" + addInput[0]);
        }
        return editCommand;
    }
}
