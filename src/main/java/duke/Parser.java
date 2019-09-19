package duke;

import duke.commands.Command;
import duke.commands.AddCommand;
import duke.commands.FindCommand;
import duke.commands.NumCommand;
import duke.exceptions.InsufficientInfoException;
import duke.exceptions.BadInputException;


/**
 * Interprets command strings by the user.
 */

public class Parser {

    private String addTodo(String input) throws InsufficientInfoException {
        if (input.isBlank()) {
            throw new InsufficientInfoException("Sorry, the description of a Todo cannot be blank!");
        } else {
            return input;
        }
    }

    private String[] addDeadline(String input) throws InsufficientInfoException {
        String[] deadline = input.split("/by ");

        //Checks if either field is blank.
        if (deadline[0].isBlank()) {
            throw new InsufficientInfoException("Sorry, the description of a Deadline cannot be blank!");
        } else if ((deadline.length < 2) || deadline[1].isBlank()) { //If the field is empty or does not exist
            throw new InsufficientInfoException("Sorry, the Deadline must have a date to be completed /by.");
        } else {
            return deadline;
        }
    }

    private String[] addEvent(String input) throws InsufficientInfoException {
        String[] event = input.split("/at ");

        if (event[0].length() == 0) {
            throw new InsufficientInfoException("Sorry, the description of an Event cannot be blank!");
        } else if ((event.length < 2) || event[1].length() == 0) {
            throw new InsufficientInfoException("Sorry, the event must have a timeframe it happens /at.");
        } else {
            return event;
        }
    }

    /**
     * Checks if the command keyword (first word is valid).
     * Determines what to do with the remaining string depending on the command.
     * Also handles exceptions for bad description string inputs.
     *
     * @param listInput array containing the command and description from the user.
     * @return an array where the first item is the command word and the second item is the rest of the text.
     * @throws BadInputException If the first word is not one of the recognised commands.
     */
    private Command handleListInput(String listInput) throws BadInputException,
            InsufficientInfoException, NumberFormatException {

        String[] keyword = listInput.split(" ", 2);
        Command command;

        switch (keyword[0]) {
        //Commands which are single words.
        case "list":
            command = new Command(Command.CommandType.LIST);
            break;
        case "bye":
            command = new Command(Command.CommandType.BYE);
            break;
        case "reminder":
            command = new Command(Command.CommandType.REMINDER);
            break;

        //Commands which require numerical input.
        case "done":
            command = new NumCommand(Command.CommandType.DONE, Integer.parseInt(keyword[1]));

            break;
        case "delete": {
            command = new NumCommand(Command.CommandType.DELETE, Integer.parseInt(keyword[1]));
            break;
        }
        case "snooze": {
            command = new NumCommand(Command.CommandType.SNOOZE, Integer.parseInt(keyword[1]));
            break;
        }

        //Commands which require string input.
        case "todo":
            command = new AddCommand(Command.CommandType.TODO, addTodo(keyword[1]), null);
            break;
        case "deadline": {
            String[] temp = addDeadline(keyword[1]);
            command = new AddCommand(Command.CommandType.DEADLINE, temp[0], temp[1]);
            break;
        }
        case "event": {
            String[] temp = addEvent(keyword[1]);
            command = new AddCommand(Command.CommandType.EVENT, temp[0], temp[1]);
            break;
        }
        case "find": {
            String description = keyword[1].trim(); //Might need to catch empty string exceptions?
            if (!description.isBlank()) {
                command = new FindCommand(Command.CommandType.FIND, description);
            } else {
                command = new Command();
                System.out.println("Please enter the search description.");
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
     * Reads in the command string from the user and looks at the first word.
     * The first word and any remaining characters are separated and passed to the handler.
     *
     * @return an array where the first item is the command word and the second item is the rest of the text.
     */
    public Command parse(String userInput) {

        Command userCommand;

        //TODO: Make this a do-while that waits for a good input?
        //TODO: Shift this implementation to the Ui class
        try {
            userCommand = handleListInput(userInput);
        } catch (NumberFormatException e) {
            System.out.println("Please input only an integer after the command.");
            userCommand = new Command();

        } catch (Exception e) {
            System.out.println("Parser error: ");
            System.out.println(e);
            userCommand = new Command();
        }

        return userCommand;
    }

}
