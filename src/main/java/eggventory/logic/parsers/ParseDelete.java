package eggventory.logic.parsers;

import eggventory.logic.commands.Command;
import eggventory.logic.commands.CommandDictionary;
import eggventory.logic.commands.delete.DeleteLoanCommand;
import eggventory.logic.commands.delete.DeleteStockCommand;
import eggventory.logic.commands.delete.DeleteStockTypeCommand;
import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.commons.exceptions.InsufficientInfoException;

//@@author cyanoei
public class ParseDelete {

    private Command processDeleteLoan(String input) {
        String[] deleteInput = input.split(" +");
        return new DeleteLoanCommand(CommandType.DELETE, deleteInput[0], deleteInput[1]);
    }

    /**
     * Processes a user command that began with the word "add".
     * Used to differentiate between the different elements the user is able to add (stock, stocktype, loan),
     * and create a command object to execute the adding of the element.
     *
     * @param inputString The input that was given after the word add.
     *                    Describes what the user wants to add, and any other details.
     * @return a Command object which will execute the desired command.
     * @throws InsufficientInfoException if not all compulsory attributes were specified.
     */
    public Command parse(String inputString) throws InsufficientInfoException, BadInputException {

        String[] deleteInput = inputString.split(" +", 2); //Obtains the first word of the input.

        Command deleteCommand;

        switch (deleteInput[0]) {

        case "stock":
            if (!Parser.isCommandComplete(inputString, 1)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("delete stock"));
            }
            deleteInput[1] = deleteInput[1].strip();
            deleteCommand = new DeleteStockCommand(CommandType.DELETE, deleteInput[1]);
            break;

        case "stocktype":
            if (!Parser.isCommandComplete(inputString, 1)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("delete stocktype"));
            }
            deleteCommand = new DeleteStockTypeCommand(CommandType.DELETE, deleteInput[1]);
            break;

        case "loan":
            if (!Parser.isCommandComplete(inputString, 2)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("delete loan"));
            }
            deleteCommand = processDeleteLoan(deleteInput[1]);
            break;

        default:
            throw new BadInputException("Unexpected value: " + deleteInput[0]);
        }

        return deleteCommand;

    }
}
