package duke;

import duke.command.*;
import duke.task.TaskList;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parser checks the user input and creates a command corresponding to the user input.
 */
public class Parser {

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
    public static Command handleInput(String inputLine, TaskList tasks) {
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
            case "list":
                return new ShowListCommand();
            case "done":
                try {
                    return new CompleteCommand(inputArray[1]);
                } catch (IndexOutOfBoundsException e) {
                    ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                            "Please use the format 'done <number>'!"
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
            default:
                return addToList(command, inputLine);
        }
        return new ErrorCommand();
    }

    /**
     * Returns an add command corresponding to the specified command, otherwise alert the user
     * that the command is invalid.
     * @param command The command to be created,
     * @param inputLine The entire line input from the user.
     * @return Add command corresponding to the specified command.
     */
    public static Command addToList(String command, String inputLine) {

        String taskDescription;
        Command commandToRun = new ErrorCommand();

        try {
            taskDescription = inputLine.substring(command.length()+1);
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
                case "recurring":
                    commandToRun = new AddRecurringTasksCommand(taskDescription);


            }
        } catch (IndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Invalid command given!"
            ));
            Ui.printMsg(msg);
        }

        return commandToRun;
    }

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
