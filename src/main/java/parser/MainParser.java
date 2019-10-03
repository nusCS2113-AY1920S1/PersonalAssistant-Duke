package parser;

import dolla.Ui;
import dolla.command.Command;
import dolla.command.CompleteCommand;
import dolla.command.RemoveCommand;
import dolla.command.ShowListCommand;
import dolla.command.FindStringCommand;
import dolla.command.ViewScheduleCommand;
import dolla.command.SnoozeCommand;
import dolla.command.ErrorCommand;
import dolla.command.AddTodoCommand;
import dolla.command.AddDeadlineCommand;
import dolla.command.AddEventCommand;
import dolla.command.AddDoAfterTaskCommand;
import dolla.command.AddFixDurationCommand;
import dolla.command.AddRecurringTaskCommand;

import dolla.task.TaskList;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * MainParser checks the user input and creates a command corresponding to the user input.
 */
public class MainParser {

    /**
     * Returns a command corresponding to the user input.
     * <p>
     *     This method checks the first word of the 'inputLine' and returns the case
     *     accordingly.
     * </p>
     * <p>
     *     If the first word is not 'list', 'done', 'remove' or 'find', addToList() will
     *     run instead.
     * </p>
     * <p>
     *     If a number is not provided in a done or remove command, an error will be printed,
     *     and an ErrorCommand will be returned.
     * </p>
     * @param inputLine The entire line input from the user.
     * @return a command corresponding to the user input.
     */
    public static Command handleInput(String mode) { // TODO: Rename to something else

        Scanner input = new Scanner(System.in);
        String inputLine = input.nextLine();
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        if (command.equals("bye")) {
            //return new ExitCommand(); // TODO

        } else if (command.equals("dolla") || command.equals("entries") ||
        command.equals("limits") || command.equals("debts") ||
        command.equals("shortcuts")) {
            //return new SwitchModeCommand(); // TODO
        }

        switch (mode) {
        case "dolla":
            DollaParser dollaParser = new DollaParser(inputLine);
            return dollaParser.handleInput();
        default:
            return new ErrorCommand();
        }

        /*
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
        case "list":
            return new ShowListCommand();
        case "done":
            try {
                return new CompleteCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(
                Arrays.asList("Please use the format 'done <number>'!"
                ));
                Ui.printMsg(msg);
                break;
            }
        case "remove":
            try {
                return new RemoveCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                        "Please use the format 'remove <number>'!"
                ));
                Ui.printMsg(msg);
                break;
            }
        case "find":
            return new FindStringCommand(inputLine);
        case "view":
            try {
                return new ViewScheduleCommand(inputArray[1]);
            } catch (IndexOutOfBoundsException e) {
                ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                        "Please use the format 'view today' or 'view <date>'!"
                ));
                Ui.printMsg(msg);
                break;
            } catch (DateTimeParseException e) {
                Ui.printDateFormatError();
                break;
            }
        case "snooze":
            try {
                String dateTimeArray = inputArray[2] + " " + inputArray[3];
                return new SnoozeCommand(inputArray[1], dateTimeArray);
            } catch (IndexOutOfBoundsException e) {
                Ui.printMsg("Please use the format 'snooze <task number> <new date> <new time>'!");
                break;
            } catch (DateTimeParseException e) {
                Ui.printDateFormatError();
                break;
            }
        default:
            return addToList(command, inputLine);
        }
        */
        return new ErrorCommand();
    }


    /*
     * Returns an add command corresponding to the specified command, otherwise alert the user
     * that the command is invalid.
     * @param command The command to be created,
     * @param inputLine The entire line input from the user.
     * @return Add command corresponding to the specified command.
     */
    /*
    public static Command addToList(String command, String inputLine) {

        String taskDescription;
        Command commandToRun = new ErrorCommand();

        try {
            taskDescription = inputLine.substring(command.length() + 1);
            switch (command) {
            case "todo":
                commandToRun = new AddTodoCommand(taskDescription);
                break;
            case "event":
                commandToRun = new AddEventCommand(taskDescription);
                break;
            case "deadline":
                commandToRun = new AddDeadlineCommand(taskDescription);
                break;
            case "duration":
                commandToRun = new AddFixDurationCommand(taskDescription);
                break;
            case "recurring":
                commandToRun = new AddRecurringTaskCommand(taskDescription);
                break;
            case "after":
                commandToRun = new AddDoAfterTaskCommand(taskDescription);
                break;
            default:
                Ui.printInvalidCommandError();
            }
        } catch (IndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Invalid command given!"
            ));
            Ui.printMsg(msg);
        }
        return commandToRun;
    }
    */

    /**
     * This method will exit the entire program.
     */
    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        Ui.printMsg(msg);
        //duke.Storage.save(tasks); // Don't need to save since any previous commands are already saved
    }
}
