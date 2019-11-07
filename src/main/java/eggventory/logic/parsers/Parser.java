package eggventory.logic.parsers;

import eggventory.logic.commands.Command;
import eggventory.logic.commands.CommandDictionary;
import eggventory.logic.commands.FindCommand;
import eggventory.logic.HelpCommand;
import eggventory.logic.commands.ByeCommand;
import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.commons.exceptions.InsufficientInfoException;

import java.util.Arrays;
import java.util.HashSet;

//@@author cyanoei
/**
 * Interprets command strings by the user, and converts them to command objects that can be executed.
 */
public class Parser {

    private static HashSet<String> reservedNames = new HashSet<>(Arrays.asList(
            "all" // Add more when needed
    ));

    private ParseAdd addParser;
    private ParseDelete deleteParser;
    private ParseEdit editParser;
    private ParseList listParser;

    /**
     * Parser object contains submodules for parsing commands with many different options.
     */
    public Parser() {
        addParser = new ParseAdd();
        deleteParser = new ParseDelete();
        editParser = new ParseEdit();
        listParser = new ParseList();
    }

    //@@author Raghav-B
    /**
     * Checks if input String is an invalid String based on the reserved keywords in the
     * reservedNames HashSet.
     * @param input input String to check.
     * @return True if invalid, false otherwise.
     */
    public static boolean isReserved(String input) {
        return reservedNames.contains(input);
    }

    /**
     * Checks if a command is complete by checking for existence of its arguments.
     * This method should only be used within the ParseX.parse method, not processX methods.
     * @param command String passed into one of the subparsers, e.g. "stock ...", "stocktype ...".
     *                Checks if command entered by user are valid by comparing against
     *                ideal argument count for command in question.
     *                This DOES NOT include the first word in the command, eg. "stock".
     * @param reqArguments Least number of arguments required by command in question.
     * @return True if user input matches required number of arguments, false otherwise.
     */
    public static boolean isCommandComplete(String command, int reqArguments) {
        command = command.strip();
        String[] commandArr = command.split(" ");
        return commandArr.length - 1 >= reqArguments;
    }
    //@@author cyanoei

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
            if (inputArr.length == 1) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("list"));
            } else {
                command = listParser.parse(inputArr[1]);
            }
            break;
        case "bye":
            command = new ByeCommand(CommandType.BYE);
            break;

        case "delete":
            if (inputArr.length == 1) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("delete"));
            }
            inputArr[1] = inputArr[1].strip(); //Removes whitespace after the stockCode so that it can parse correctly.
            command = deleteParser.parse(inputArr[1]);
            break;

        //Commands which require string input.
        case "add": {
            if (inputArr.length == 1) { //User command only said "add" and nothing else.
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("add"));
            } else {
                command = addParser.parse(inputArr[1]);
            }
            break;
        }
        case "find": {
            if (inputArr.length == 1) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("find"));
            }

            String description = inputArr[1].trim(); //Might need to catch empty string exceptions?
            if (!description.isBlank()) {
                command = new FindCommand(CommandType.FIND, description);
            } else {
                throw new InsufficientInfoException("Please enter the search description.");
            }
            break;
        }
        case "edit": {
            if (inputArr.length == 1) {
                throw new InsufficientInfoException(CommandDictionary.getCommandUsage("edit"));
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

//@@author
