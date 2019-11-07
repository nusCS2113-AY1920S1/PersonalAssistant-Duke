package eggventory.logic.parsers;

import eggventory.logic.commands.Command;
import eggventory.logic.commands.CommandDictionary;
import eggventory.logic.commands.edit.EditStockCommand;
import eggventory.logic.commands.edit.EditStockTypeCommand;
import eggventory.commons.enums.CommandType;
import eggventory.commons.enums.StockProperty;
import eggventory.commons.exceptions.BadInputException;
import eggventory.commons.exceptions.InsufficientInfoException;

//@@author patwaririshab
public class ParseEdit {

    //@@author cyanoei
    private void checkStockCode(String stockCode) throws BadInputException {
        if (stockCode.contains(" ")) {
            throw new BadInputException("Sorry, the new StockCode cannot contain spaces!");
        }
    }

    //@@author patwaririshab

    private Command processEditStock(String input) throws BadInputException {
        String[] editInput = input.split(" +", 3); //<stockCode> <property> <newValue>

        String stockCode = editInput[0];
        switch (editInput[1]) {
        case "stockcode":
            checkStockCode(editInput[2]);
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.STOCKCODE,
                    editInput[2].toLowerCase());
        case "quantity":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.QUANTITY,
                    editInput[2].toLowerCase());
        case "loaned":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.LOANED, editInput[2].toLowerCase());
        case "lost":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.LOST, editInput[2].toLowerCase());
        case "description":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.DESCRIPTION,
                    editInput[2].toLowerCase());
        case "minimum":
            return new EditStockCommand(CommandType.EDIT, stockCode, StockProperty.MINIMUM, editInput[2].toLowerCase());
        default:
            throw new BadInputException("The property you are trying to edit does not exist.");
        }
    }

    private Command processEditStockType(String input) throws BadInputException {
        String[] addInput = input.split(" +", 2);


        if (Parser.isReserved(addInput[0])) {
            throw new BadInputException("'" + addInput[0] + "' is an invalid name as it is a keyword"
                    + " for an existing command.");
        }
        if (Parser.isReserved(addInput[1])) {
            throw new BadInputException("'" + addInput[1] + "' is an invalid name as it is a keyword"
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
            //Required: stock <stockCode> <property> <new>
            if (!Parser.isCommandComplete(inputString, 3)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("edit stock"));
            }
            editCommand = processEditStock(addInput[1]);
            break;

        case "stocktype":
            //Required: stockType <name> <new>
            if (!Parser.isCommandComplete(inputString, 2)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("edit stocktype"));
            }
            editCommand = processEditStockType(addInput[1]);
            break;

        default:
            throw new BadInputException("Unexpected value:" + addInput[0]);
        }
        return editCommand;
    }
    //@@ author
}
