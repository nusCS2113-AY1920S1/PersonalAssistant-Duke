package eggventory.logic.parsers;

import eggventory.logic.commands.Command;
import eggventory.logic.commands.CommandDictionary;
import eggventory.logic.commands.add.AddPersonCommand;
import eggventory.logic.commands.add.AddLoanCommand;
import eggventory.logic.commands.add.AddStockCommand;
import eggventory.logic.commands.add.AddStockTypeCommand;
import eggventory.logic.commands.add.AddTemplateCommand;
import eggventory.logic.commands.add.AddLoanByTemplateCommand;
import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.commons.exceptions.InsufficientInfoException;
import eggventory.model.TemplateList;
import javafx.util.Pair;

import java.util.ArrayList;

//@@author cyanoei
public class ParseAdd {

    /**
     * Extracts the minimum quantity parameter from the string.
     * @param input the entire add stock string.
     * @return the minimum quantity, if specified, or -1 if not specified.
     * @throws BadInputException if the minimum quantity is not an integer.
     */
    private int processAddStockMinimum(String input) throws BadInputException {
        String[] parseMinimum = input.split(" -m ");

        if (parseMinimum.length == 1) { //If there is no minimum quantity specified.
            return -1; //Consider: Edge case where user specifies minQty of 0. Command still needs to have -m removed.

        } else {
            String minString = parseMinimum[1].split(" ", 2)[0]; //Take only the first word after " -m ".

            Parser.isCheckIsInteger(minString, "minimum quantity");
            return Integer.parseInt(minString);
        }
    }

    /**
     * Removes the optional parameter "minimum quantity" from the input string, for description to be parsed normally.
     * @param fullInput the original user command string.
     * @param minQuantity the minimum quantity (integer) as found earlier.
     * @return the string without " -m Quantity".
     */
    private String removeMinInput(String fullInput, int minQuantity) {
        fullInput = fullInput.replace(" -m " + Integer.toString(minQuantity), "");
        return fullInput;
    }

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
    private Command processAddStock(String input) throws BadInputException {
        int minimumQuantity = processAddStockMinimum(input);

        if (minimumQuantity != -1) { //There was a min quantity set.
            input = removeMinInput(input, minimumQuantity); //Cleans the command.
        } else {
            minimumQuantity = 0; //Set it to 0 to be added.
        }

        String[] addInput = input.split(" +", 4); //There are 4 attributes for now.

        if (Parser.isReserved(addInput[1])) {
            throw new BadInputException("'" + input + "' is an invalid StockCode as it is a keyword"
                    + " for an existing command.");
        }

        Parser.isCheckIsInteger(addInput[2], "quantity");

        return new AddStockCommand(CommandType.ADD, addInput[0], addInput[1],
                Integer.parseInt(addInput[2]), addInput[3], minimumQuantity);
    }


    //@@author Deculsion
    /**
     * Processes the contents of an add stocktype command (everything after the words "add" and "stocktype").
     * Splits up the input string into an array containing the various attributes of the stocktype being added.
     *
     * @param input the user input describing the stockType to be added.
     * @return the command to execute.
     * @throws InsufficientInfoException if there are insufficient details provided.
     */
    private Command processAddStockType(String input) throws BadInputException {

        input = input.strip();  //In case of trailing spaces.

        if (Parser.isReserved(input)) {
            throw new BadInputException("'" + input + "' is an invalid name as it is a keyword"
                    + " for an existing command.");
        }

        if (input.contains(" ")) {
            throw new BadInputException("Sorry, the StockType name cannot contain spaces!");
        }

        return new AddStockTypeCommand(CommandType.ADD, input);
    }

    /**
     * Processes input sequence for adding a person.
     */
    private Command processAddPerson(String input) throws BadInputException {
        String[] addInput = input.split(" +", 2); //Permits spaces in names.

        if (Parser.isReserved(addInput[0])) {
            throw new BadInputException("'" + addInput[0] + "' is an invalid name as it is a keyword"
                    + " for an existing command.");
        }

        return new AddPersonCommand(CommandType.ADD, addInput[0], addInput[1]);
    }

    /**
     * Processes the input sequence for adding a template.
     */
    public Command processAddTemplate(String input) throws BadInputException {
        String[] addInput = input.split(" +");

        //Checks if template name is reserved.
        if (Parser.isReserved(addInput[0])) {
            throw new BadInputException("'" + addInput[0] + "' is an invalid name as it is a keyword"
                    + " for an existing command.");
        }

        if (addInput.length % 2 == 0) { // A parameter is missing if there are odd number of arguments.
            throw new BadInputException("Template is missing a <StockCode> or <Quantity>");
        }

        ArrayList<Pair<String, String>> loanPairs = new ArrayList<>();

        for (int i = 1; i < addInput.length; i += 2) {
            loanPairs.add(new Pair<>(addInput[i], addInput[i + 1]));
        }

        return new AddTemplateCommand(CommandType.ADD, addInput[0], loanPairs);
    }

    //@@author cyanoei
    /**
     * Processes the user command to add a loan.
     * @param input string in the format matricNo, stockCode and quantity.
     * @return a command to add a loan.
     */
    private Command processAddLoan(String input) throws InsufficientInfoException, BadInputException {
        String[] addInput = input.split(" +");

        System.out.println("Working");
        if (TemplateList.templateExists(addInput[1])) {
            return new AddLoanByTemplateCommand(CommandType.ADD, addInput[0], addInput[1]);
        }
        if (addInput.length < 3) {
            throw new InsufficientInfoException(CommandDictionary.getCommandUsage("add loan"));
        }

        Parser.isCheckIsInteger(addInput[2], "loan quantity");

        return new AddLoanCommand(CommandType.ADD, addInput[0], addInput[1], Integer.parseInt(addInput[2]));
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
            //Required: stock <stockType> <stockCode> <quantity> <description>
            if (!Parser.isCommandComplete(inputString, 4)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("add stock"));
            }
            addCommand = processAddStock(addInput[1]);
            break;

        case "stocktype":
            //Required: stocktype <stockType name>
            if (!Parser.isCommandComplete(inputString, 1)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("add stocktype"));
            }
            addCommand = processAddStockType(addInput[1]);
            break;

        case "loan":
            //Required: loan <matric> <stockCode> <quantity>
            //OR:       loan <matrix> <TemplateName>
            if (!Parser.isCommandComplete(inputString, 3) && !Parser.isCommandComplete(inputString,
                    2)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("add loan"));
            }
            addCommand = processAddLoan(addInput[1]);
            break;

        case "person":
            //Required: person <matric> <name>
            if (!Parser.isCommandComplete(inputString, 2)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("add person"));
            }

            addCommand = processAddPerson(addInput[1]);
            break;

        case "template":
            //Required: template <name> <stockCode> <quantity> (and other pairs if needed)
            if (!Parser.isCommandComplete(inputString, 3)) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("add template"));
            }
            addCommand = processAddTemplate(addInput[1]);
            break;

        default:
            throw new BadInputException("Unexpected value: " + addInput[0]);
        }

        return addCommand;

    }

}