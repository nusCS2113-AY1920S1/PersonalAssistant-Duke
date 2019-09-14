package duke.parser;

import duke.command.*;
import duke.dukeexception.DukeException;
import duke.task.*;

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
        String repeatDesc = "";
        String[] listRepeatDates = {};
        int timesToRepeat = 0;
        int addDays = 0;
        boolean getDate = false;
        boolean storeFrequencyOfEvent = false;
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
            for (int i = 1; i < arr.length; i++) {
                if ((arr[i].trim().isEmpty() || !arr[i].substring(0, 1).equals("/")) && !getDate) {
                    taskDesc += arr[i] + " ";
                } else {
                    if (!getDate) { //detect "/"
                        getDate = true;
                    } else {
                        if (!arr[i].equals("daily") || !arr[i].equals("weekly") || !arr[i].equals("monthly")) {
                            dateDesc += arr[i] + " ";
                        } else {
                            if (!storeFrequencyOfEvent) {
                                repeatDesc = arr[i];
                                storeFrequencyOfEvent = true;
                            } else {
                                timesToRepeat = Integer.parseInt(arr[i]);
                            }
                        }
                    }
                }
            }
            taskDesc = taskDesc.trim();
            dateDesc = dateDesc.trim();
            repeatDesc = repeatDesc.trim();
            if (taskDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of a "
                        + arr[0] + " cannot be empty.");
            } else if (dateDesc.isEmpty()) {
                throw new DukeException("     (>_<) OOPS!!! The description of date/time for "
                        + arr[0] + " cannot be empty.");
            } else {
                int[] daysInEachMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                String splitDate[] = dateDesc.split("/", 3);
                String splitYearAndTime[] = splitDate[2].split(" ", 2);

                if (Integer.parseInt(splitYearAndTime[0]) % 4 == 0) {
                    daysInEachMonth[1] = 29;
                }

                Task taskObj;
                switch (repeatDesc) {
                    case "daily":
                        addDays = 1;
                        break;
                    case "weekly":
                        addDays = 7;
                        break;
                    default:
                        addDays = 0;
                        break;
                }
                int date = Integer.parseInt(splitDate[0]);
                int month = Integer.parseInt(splitDate[1]);
                int year = Integer.parseInt(splitYearAndTime[0]);

                for (int i = 0; i < timesToRepeat; i++) {
                    int curDate = date;
                    int curMonth = month;
                    int curYear = year;
                    curDate += i * addDays;
                    while(curDate > daysInEachMonth[month]){
                        curDate -= daysInEachMonth[month];
                        curMonth++;
                        if(curMonth == 13){
                            curYear++;
                            curMonth = 1;
                        }
                    }
                    String dateInString = Integer.toString(curDate);
                    String monthInString = Integer.toString(curMonth);
                    String yearInString = Integer.toString(curYear);

                    dateDesc = dateInString + "/" + monthInString + "/" + yearInString + " " + splitYearAndTime[1];
                    listRepeatDates[i] = dateDesc;
                }
                taskObj = new Repeat(taskDesc, dateDesc, listRepeatDates, timesToRepeat);
                return new AddCommand(taskObj);
            }
        } else if (sentence.equals("bye")) {
            return new ExitCommand();
        } else {
            throw new DukeException("     (>_<) OoPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}