package eggventory.parsers;

import eggventory.commands.Command;
import eggventory.commands.FindCommand;
import eggventory.commands.help.HelpCommand;
import eggventory.commands.ListCommand;
import eggventory.commands.ByeCommand;
import eggventory.enums.CommandType;
import eggventory.exceptions.BadInputException;
import eggventory.exceptions.InsufficientInfoException;

/**
 * Interprets command strings by the user, and converts them to command objects that can be executed.
 */
public class Parser {

    private ParseAdd addParser;
    private ParseDelete deleteParser;
    private ParseEdit editParser;

    /**
     * Parser object contains submodules for parsing commands with many different options.
     */
    public Parser() {
        addParser = new ParseAdd();
        deleteParser = new ParseDelete();
        editParser = new ParseEdit();
    }

    /**
     * Checks if the command keyword (first word is valid).
     * Determines what to do with the remaining string depending on the command.
     * Also handles exceptions for bad description string inputs.
     *
     * @param listInput array containing the command and description from the user.
     * @return a Command object which will execute the user's command.
     * @throws BadInputException If the first word is not one of the recognised commands.
     * @throws InsufficientInfoException If any of the commands had compulsory parameters missed out.
     * @throws NumberFormatException If non-integer inputs are received for numerical parameters.
     */
    private Command handleListInput(String listInput) throws BadInputException,
            InsufficientInfoException, NumberFormatException {

        listInput = listInput.strip(); //Remove leading/trailing whitespace.

        //Extract the first word.
        //inputArr[0] is the main command word.
        //inputArr[1] is the subsequent command string, and may also be empty.
        String[] inputArr = listInput.split(" +", 2);
        Command command;

        switch (inputArr[0]) {
        //Commands which are single words.
        case "list":
            if (inputArr.length != 2) {
                throw new BadInputException("Usage of list: list stock, list stocktypes or list <stocktype>");
            } else {
                command = new ListCommand(CommandType.LIST, inputArr[1]);
            }
            break;
        case "bye":
            command = new ByeCommand(CommandType.BYE);
            break;

        case "delete":
            inputArr[1] = inputArr[1].strip(); //Removes whitespace after the stockCode so that it can parse correctly.
            command = deleteParser.parse(inputArr[1]);
            break;

        //Commands which require string input.
        case "add": {
            if (inputArr.length == 1) { //User command only said "add" and nothing else.
                //Instead of BadInputException, we should be returning a helpCommand.
                throw new BadInputException("'" + inputArr[0] + "' requires 1 or more arguments.");
            } else {
                command = addParser.parse(inputArr[1]);
            }
            break;
        }
        case "find": {
            if (inputArr.length == 1) {
                throw new BadInputException("'" + inputArr[0] + "' requires 1 or more arguments.");
            }

            String description = inputArr[1].trim(); //Might need to catch empty string exceptions?
            if (!description.isBlank()) {
                command = new FindCommand(CommandType.FIND, description);
            } else {
                command = new Command();
                throw new BadInputException("Please enter the search description.");
            }
            break;
        }
        case "edit": {
            if (inputArr.length == 1) {
                throw new BadInputException("'" + inputArr[0] + "' requires 1 or more arguments.");
            } else {
                command = editParser.parse(inputArr[1]);
            }
            break;
        }
        case "help": {
            if (inputArr.length == 1) {
                //display general help
                command = new HelpCommand(CommandType.HELP);
            } else {
                //display full help.
                command = new HelpCommand(CommandType.HELP, inputArr[1]);
            }
            break;
        }
        default:
            command = new Command(); //Bad Command
            throw new BadInputException("Sorry, I don't recognise that input keyword!");
        }
        return command;
    }

    /**
     * Takes command string from the user and passes to the handleListInput method.
     * Also catches exceptions for invalid commands.
     *
     * @return A command object corresponding to the user's inputs.
     */
    public Command parse(String userInput) throws Exception {
        Command userCommand;

        //TODO: Make this a do-while that waits for a good input?
        try {
            userCommand = handleListInput(userInput);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please input only an integer after the command.");
        } catch (Exception e) { // Redundant code, but demonstrates throwing all other exceptions higher
            throw e;
        }

        return userCommand;
    }
}
