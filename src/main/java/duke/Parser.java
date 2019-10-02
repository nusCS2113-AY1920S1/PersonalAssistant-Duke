package duke;

import duke.commands.Command;
import duke.commands.AddCommand;
import duke.commands.FindCommand;
import duke.commands.NumCommand;
import duke.commands.RecurCommand;
import duke.commands.ViewScheduleCommand;
import duke.exceptions.InsufficientInfoException;
import duke.exceptions.BadInputException;
import duke.enums.CommandType;


/**
 * Interprets command strings by the user.
 */

public class Parser {

    private String[] addTodo(String input) throws InsufficientInfoException {
        if (input.isBlank()) {
            throw new InsufficientInfoException("Sorry, the description of a Todo cannot be blank!");
        }

        String[] checkHours = input.split(" /needs ", -1);

        return checkHours;

    }

    /**
     * Splits the user input and checks for formatting errors inside input string.
     * @param input User input with "deadline" token removed
     * @return Array of size 2 with elem[0] being the description and elem[1] the formatted datetime.
     * @throws InsufficientInfoException Thrown if user formatting is wrong.
     */
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

    /**
     * Splits the user input and checks for formatting error inside input string.
     *
     * @param input User input with "event" token removed
     * @return Array of size 2. elem[0] is the description and elem[1] is the datetime in format of
     *      "dd/MM/YYYY HHmm to dd/MM/YYYY HHmm".
     * @throws InsufficientInfoException Thrown if user formatting is wrong.
     */
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

    private int processDoAfter(String input) throws BadInputException {
        String shortStr;
        String[] splitStr;
        int taskIndex;

        shortStr = input.substring(input.indexOf("/after"));

        try {
            splitStr = shortStr.split(" ", 3); //splits into "/after" "x" and other stuff, where "x" is an int
            taskIndex = Integer.parseInt(splitStr[1]); //check if this is an int
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new BadInputException("Please input the index number of the task that has to be done first.");
        }
        return taskIndex;
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

        /*TODO: Update parser to handle Task requests separately to process optional commands better
            eg. doAfter or repeating tasks
        */

        int afterIndex;
        afterIndex = -1;
        if (listInput.contains("/after")) {
            afterIndex = processDoAfter(listInput);
            listInput = listInput.replace(" /after " + Integer.toString(afterIndex), "");
        }

        String[] keyword = listInput.split(" ", 2);
        Command command;

        switch (keyword[0]) {
        //Commands which are single words.
        case "list":
            command = new Command(CommandType.LIST);
            break;
        case "bye":
            command = new Command(CommandType.BYE);
            break;

        //Commands which require numerical input.
        case "done":
            command = new NumCommand(CommandType.DONE, Integer.parseInt(keyword[1]));
            break;
        case "delete":
            command = new NumCommand(CommandType.DELETE, Integer.parseInt(keyword[1]));
            break;
        case "snooze":
            command = new NumCommand(CommandType.SNOOZE, Integer.parseInt(keyword[1]));
            break;

        //Commands which require string input.
        case "todo":
            String[] todoTemp = addTodo(keyword[1]);
            command = new AddCommand(CommandType.TODO, todoTemp[0],
                    (todoTemp.length > 1) ? todoTemp[1] : "");
            break;

        case "deadline": {
            String[] temp = addDeadline(keyword[1]);
            command = new AddCommand(CommandType.DEADLINE, temp[0], temp[1]);
            break;
        }
        case "event": {
            String[] temp = addEvent(keyword[1]);
            command = new AddCommand(CommandType.EVENT, temp[0], temp[1]);
            break;
        }
        case "find": {
            String description = keyword[1].trim(); //Might need to catch empty string exceptions?
            if (!description.isBlank()) {
                command = new FindCommand(CommandType.FIND, description);
            } else {
                command = new Command();
                System.out.println("Please enter the search description.");
            }
            break;
        }
        case "recur": {
            //Input format: recur 5 12 deadline description /by dd/mm/yyyy HHMM
            //Event: recur 5 12 event description /at dd/mm/yyyy HHMM to dd/nn/yyyy HHMM
            String taskInput = keyword[1].trim();
            String[] newKeyword = taskInput.split(" ", 3);
            int recurInterval = Integer.parseInt(newKeyword[0]);
            int numberOfRecur = Integer.parseInt(newKeyword[1]);
            String[] finalKeyword = newKeyword[2].split(" ", 2);
            switch (finalKeyword[0]) {
            case "deadline": {
                String[] temp = addDeadline(finalKeyword[1]);
                command = new RecurCommand(CommandType.DEADLINE, temp[0], temp[1],
                        recurInterval, numberOfRecur);
                break;
            }
            case "event": {
                String[] temp = addEvent(finalKeyword[1]);
                command = new RecurCommand(CommandType.EVENT, temp[0], temp[1], recurInterval, numberOfRecur);
                break;
            }
            default:
                command = new Command();
                throw new BadInputException("Sorry, I don't recognise that input keyword");
            }
            break;
        }

        case "view": {
            command = new ViewScheduleCommand(CommandType.VIEW, keyword[1]);
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
    public Command parse(String userInput) throws Exception {
        Command userCommand;

        //TODO: Make this a do-while that waits for a good input?
        try {
            userCommand = handleListInput(userInput);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please input only an integer after the command.");
        }

        return userCommand;
    }
}
