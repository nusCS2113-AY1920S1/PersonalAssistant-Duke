package duke.Data;

import duke.Task.*;
import duke.Module.Reminder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Parser is the controller for the string inputs received by the standard input.
 */
public class Parser {


    /**
     * This function takes the standard input defined by the user and
     * parses it into instructions for the Storage to read.
     *
     * @param io
     */
    public void parseInput(String io) {
        int index = 1;
        String input = io;
        String[] word = io.split(" ");
        String cmd = word[0];

        switch (cmd) {

            case "list":
                TaskList.getList();
                break;

            case "done":
                try {
                    index = Integer.parseInt(input.substring(5)) - 1;
                    TaskList.doneTask(index);
                }
                catch (NullPointerException | IndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The following task does not exist!");
                }
                break;
        /**
         * TODO Fix saving of ToDo class, is causing the load file error due to save formatting
         */
            case "todo":
                try {
                    String[] tempString = input.split(" ");
                    List<String> listString = new ArrayList<String>(Arrays.asList(tempString));
                    listString.remove(0);
                    String info1 = String.join(" ", listString);
                    String[] parseString = info1.split("/in");
                    ToDo todo = new ToDo(parseString[0], false, parseString[1]);
                    TaskList.addTask(todo, "T");
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The description of a todo cannot be empty.");
                }
                break;

            case "deadline":
                try {
                    index = input.indexOf("/by");
                    String info = input.substring(9, index);
                    String endDate = input.substring(index + 4);
                    Deadline deadline = new Deadline(info, false, endDate);
                    TaskList.addTask(deadline, "D");
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The task needs a deadline");
                }
                break;

            case "event":
                try {
                    index = input.indexOf("/at");
                    String info = input.substring(6, index);
                    String endDate = input.substring(index + 4);
                    Event event = new Event(info, false, endDate);
                    TaskList.addTask(event, "E");
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The task needs a deadline");
                }
                break;
            /**
             * Command should be in the form: reminder deadlines before 18/09/2019 1900
             * Push date before date into
             */
            case "reminder":
                try{
                    index = input.indexOf("before");
                    Date date = TaskList.dateConvert(input.substring(index + 7));
                    Reminder reminder = new Reminder(date);
                    reminder.getReminders();
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.err.println("Incorrect format");
                }
                break;

            /**
             * Command should be in the form: aftertask return book /after exam
             * It will be stored as type [A].
             */
            case "aftertask":
                try {
                    index = input.indexOf("/after");
                    String info = input.substring(10, index);
                    String endDate = input.substring(index + 7);
                    After after = new After(info, false, endDate);
                    TaskList.addTask(after, "A");
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! Please enter input in the form: aftertask XXX /after YYY");
                }
                break;

            case "delete":
                index = Integer.parseInt(input.substring(7)) - 1;
                TaskList.deleteTask(index);
                break;

            case "find":
                String searchWord = input.substring(5);
                TaskList.findTask(searchWord);
                break;

            case "date":
                String searchDate = input.substring(5);
                if (searchDate.length() < 10) {
                    System.out.println("Please enter input in the form: date dd/MM/YYYY");
                } else {
                    TaskList.findDate(searchDate);
                }
                break;

            default:
                System.out.println("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
                break;
        }

    }
}
