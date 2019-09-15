package duke.parser;

import duke.command.*;
import duke.dukeexception.DukeException;
import duke.task.*;

import java.util.ArrayList;

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
        }
        else if (arr.length > 0 && arr[0].equals("repeat")) {
            //repeat <task> /from <date time> /for 3 <day/week/month>
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
                String repeatSettings;
                int repeatTimes;
                String repeatPeriod;
                try {
                    repeatSettings = dateDesc.split("/for ")[1];
                    repeatTimes = Integer.parseInt(repeatSettings.replaceAll("[\\D]", ""));
                    repeatPeriod = repeatSettings.split(repeatTimes + " ")[1];

                } catch (Exception e) {
                    throw new DukeException("Format is in: repeat <task> /from <date time> " +
                            "/for <repeat times> <days/weeks>");
                }

                ArrayList<Task> repeatList = new ArrayList<>();
                String timeDesc = dateDesc.split(" ", 3)[1];
                for (int i = 0; i < repeatTimes; i++) {
                    Task taskObj;
                    taskObj = new Repeat(taskDesc, dateDesc);
                    dateDesc = DateParser.add(dateDesc, repeatPeriod) + " " + timeDesc;
                    repeatList.add(taskObj);
                }
                return new AddMultipleCommand(repeatList);
            }
        }
        else if (sentence.equals("bye")) {
            return new ExitCommand();
        } else {
            throw new DukeException("     (>_<) OoPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}