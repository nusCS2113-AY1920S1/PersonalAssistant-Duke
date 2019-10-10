package eggventory.parsers;
import eggventory.commands.Command;
import eggventory.commands.AddCommand;
import eggventory.enums.CommandType;
import eggventory.exceptions.InsufficientInfoException;


public class ParseAdd {

    /**
     * Processes the contents of an add command (everything after the word "add").
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
                    + " this format:\nadd <StockType> <StockCode> <Quantity> <Description>");
        } else {

            if (addInput[0].isBlank() | addInput[1].isBlank() | addInput[2].isBlank() | addInput[3].isBlank()) {
                throw new InsufficientInfoException("Please enter stock information after the 'add' command in"
                        + " this format:\nadd <StockType> <StockCode> <Quantity> <Description>");
            }
        }

        return new AddCommand(CommandType.ADD, addInput[0], addInput[1],
                Integer.parseInt(addInput[2]), addInput[3]);
    }


    /**
     *
     * @param inputString The input that was given after the word add.
     *                    Describes what the user wants to add, and any other details.
     * @return a Command object which will execute the desired command. 
     * @throws InsufficientInfoException if not all compulsory attributes were specified.
     */
    public Command parse(String inputString) throws InsufficientInfoException {

        String[] addInput = inputString.split(" +", 2); //Obtains the first word of the input.



        //In the future this should be a switch statement to process what kind of add it is.
        //For now we only have adding stocks.
        Command addCommand = processAddStock(inputString);

        return addCommand;

    }

}
