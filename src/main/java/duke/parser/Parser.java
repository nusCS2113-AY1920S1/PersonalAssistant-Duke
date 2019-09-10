package duke.parser;

import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.AddCommand;
import duke.command.DeleteCommand;
import duke.command.Command;
import duke.command.ListCommand;
import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.dukeexception.DukeException;

/**
 * Represents a parser that breaks down user input into commands.
 */
public class Parser {

    /**
     * Generates a command based on the user input.
     *
     * @param sentence User input.
     * @param items The task list that contains a list of tasks.
     * @return Command to be executed afterwards.
     * @throws Exception  If there is an error interpreting the user input.
     */
    public static Command parse(String sentence, TaskList items) throws Exception {
        String[] arr = sentence.split(" ");
        String taskDesc = "";
        String dateDesc = "";
        boolean getDate = false;
        if (sentence.equals("list")) {
            return new ListCommand();
        } else if (arr.length > 0 && (arr[0].equals("done") || arr[0].equals("delete"))) {
            if (arr.length == 1) {
                throw new DukeException("     (>_<) OOPS!!! The task number cannot be empty.");
            } else {
                int tasknum = Integer.parseInt(arr[1]) - 1;
                if (tasknum < 0 || tasknum >= items.size()) {
                    throw new DukeException("     (>_<) OOPS!!! Invalid task number.");
                } else {
                    if (arr[0].equals("done")) {
                        return new DoneCommand(tasknum);
                    } else { //delete
                        return new DeleteCommand(tasknum);
                    }
                }
            }
        } else if (arr.length > 0 && arr[0].equals("find")) {
            if (arr.length == 1) {
                throw new DukeException("     (>_<) OOPS!!! The keyword cannot be empty.");
            } else {
                if (arr[1].trim().isEmpty()) {
                    throw new DukeException("     (>_<) OOPS!!! The keyword cannot be empty.");
                } else {
                    return new FindCommand(arr[1]);
                }
            }
        } else if (arr.length > 0 && arr[0].equals("todo")) {
            for (int i = 1; i < arr.length; i++) {
                taskDesc += arr[i] + " ";
            }
            taskDesc = taskDesc.trim();
            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a todo cannot be empty.");
            } else {
                Task taskObj = new Todo(taskDesc);
                return new AddCommand(taskObj);
            }
        } else if (arr.length > 0 && (arr[0].equals("deadline") || arr[0].equals("event"))) {
            for (int i = 1; i < arr.length; i++) {
                if ((arr[i].trim().isEmpty() || !arr[i].substring(0, 1).equals("/")) && !getDate) {
                    taskDesc += arr[i] + " ";
                } else {
                    if (!getDate) { //detect "/"
                        getDate = true;
                    } else {
                        dateDesc += arr[i] + " ";
                    }
                }
            }
            taskDesc = taskDesc.trim();
            dateDesc = dateDesc.trim();
            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a " + arr[0] + " cannot be empty.");
            } else if (dateDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of date/time for "
                        + arr[0] + " cannot be empty.");
            } else {
                Task taskObj;
                if (arr[0].equals("deadline")) {
                    taskObj = new Deadline(taskDesc, dateDesc);
                } else {
                    taskObj = new Event(taskDesc, dateDesc);
                }
                return new AddCommand(taskObj);
            }
        } else if (sentence.equals("bye")) {
            return new ExitCommand();
        } else {
            throw new DukeException("     (>_<) OoPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
