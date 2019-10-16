package eggventory.parsers;

import eggventory.commands.Command;
import eggventory.commands.AddCommand;
import eggventory.commands.add.AddStockTypeCommand;
import eggventory.enums.CommandType;
import eggventory.exceptions.BadInputException;
import eggventory.exceptions.InsufficientInfoException;


public class ParseAdd {

    /**
     * Processes the contents of an add stock command (everything after the words "add" and "stock").
     * Splits up the input string into an array containing the various attributes of the stock being added.
     * Ignores leading/trailing whitespace between the first word and subsequent string,
     * and between all commands' arguments.
     *
     * @param input String containing the attributes of the stock.
     * @return an array consisting of StockType, StockCode, Quantity and Description.
     * @throws InsufficientInfoException If any of the required attributes is missed out.
     */
    private Command processAddStock(String input) throws InsufficientInfoException {

        String[] addInput = input.split(" +", 4); //There are 4 attributes for now.

        if (addInput.length < 4) {
            throw new InsufficientInfoException("Please enter stock information after the 'add' command in"
                    + " this format:\nadd stock <StockType> <StockCode> <Quantity> <Description>");
        } else if (addInput[0].isBlank() | addInput[1].isBlank() | addInput[2].isBlank() | addInput[3].isBlank()) {
            throw new InsufficientInfoException("Please enter stock information after the 'add' command in"
                    + " this format:\nadd stock <StockType> <StockCode> <Quantity> <Description>");
        }

        return new AddCommand(CommandType.ADD, addInput[0], addInput[1],
                Integer.parseInt(addInput[2]), addInput[3]);
    }


    /**
     * Processes the contents of an add stocktype command (everything after the words "add" and "stocktype").
     * Splits up the input string into an array containing the various attributes of the stocktype being added.
     *
     * @param input the user input describing the stockType to be added.
     * @return the command to execute.
     * @throws InsufficientInfoException if there are insufficient details provided.
     */
    private Command processAddStockType(String input) throws InsufficientInfoException {
        String[] addInput = input.split(" +");

        if (addInput[0].isBlank()) {
            throw new InsufficientInfoException("Please enter stock information after the 'add' command in"
                    + " this format:\nadd stocktype <StockType>");
        }

        return new AddStockTypeCommand(CommandType.ADD, addInput[0]);
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

        String[] addInput = inputString.split(" +", 2); //Obtains the first word of the input.

        Command addCommand;

        switch (addInput[0]) {

        case "stock":
            addCommand = processAddStock(addInput[1]);
            break;

        case "stocktype":
            addCommand = processAddStockType(inputString);
            break;
        default:
            throw new BadInputException("Unexpected value: " + addInput[0]);
        }

        return addCommand;

    }

}