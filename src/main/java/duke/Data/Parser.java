package duke.Data;

import Menu.ManageStudents;
import duke.Module.Schedule;
import duke.Sports.MyClass;
import duke.Task.*;
import duke.Module.Reminder;
import duke.Ui;
import duke.Sports.MyPlan;

import java.io.FileNotFoundException;
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
    public void parseInput(String io, TaskList tasks, Storage storage) throws FileNotFoundException {
        int index = 1;
        String input = io;
        String[] word = io.split(" ");
        String cmd = word[0];

        switch (cmd) {

        case "list":
            tasks.showList();
            break;

        case "done":
            try {
                index = Integer.parseInt(input.substring(5)) - 1;
                tasks.doneTask(index);
                storage.updateFile(tasks.getList());
            } catch (NullPointerException | IndexOutOfBoundsException e) {
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
                    tasks.addTask(todo, "T");
                    storage.saveFile("T",todo,todo.getDate());
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The description of a todo cannot be empty.");
                }
                break;

            case "deadline":
                try {
                    index = input.indexOf("/by");
                    String info = input.substring(9, index-1);
                    String endDate = input.substring(index + 4);
                    Deadline deadline = new Deadline(info, false, endDate);
                    tasks.addTask(deadline, "D");
                    storage.saveFile("D",deadline,deadline.getDate());
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! The task needs a deadline");
                }
                break;

            case "event":
                try {
                    index = input.indexOf("/at");
                    String info = input.substring(6, index-1);
                    String endDate = input.substring(index + 4);
                    Event event = new Event(info, false, endDate);
                    tasks.addTask(event, "E");
                    storage.saveFile("E",event,event.getDate());
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
                    Date date = tasks.dateConvert(input.substring(index + 7));
                    Reminder reminder = new Reminder(date);
                    reminder.getReminders(tasks);
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
                    String info = input.substring(10, index-1);
                    String endDate = input.substring(index + 7);
                    After after = new After(info, false, endDate);
                    tasks.addTask(after, "A");
                    storage.saveFile("A",after,after.getDate());
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! Please enter input in the form: aftertask XXX /after YYY");
                }
                break;

            case "delete":
                index = Integer.parseInt(input.substring(7)) - 1;
                tasks.deleteTask(index);
                storage.updateFile(tasks.getList());
                break;

            case "find":
                String searchWord = input.substring(5);
                tasks.findTask(searchWord);
                break;

            case "date":
                String searchDate = input.substring(5);
                if (searchDate.length() < 10) {
                    System.out.println("Please enter input in the form: date dd/MM/YYYY");
                } else {
                    tasks.findDate(searchDate);
                }
                break;

            /**
             * Command is in the form: plan new [intensity level] or plan view [intensity] [plan number]
             *
             */
            case "plan":
                MyPlan plan = new MyPlan();
                if (word[1].equals("view")) {
                    plan.loadPlan(word[2]);
                } else if (word[1].equals("new")) {
                    plan.createPlan(word[3]);
                }
                break;

            /**
             * Command should be in the form: class swimming /every monday
             * It will be stored as type [C].
             */
            case "class":
                try {
                    index = input.indexOf("/every");
                    String info = input.substring(6, index-1);
                    String day = input.substring(index + 7);
                    MyClass myclass = new MyClass(info, false, day);
                    tasks.addTask(myclass, "C");
                    storage.saveFile("C",myclass,myclass.getDay());
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\u2639 OOPS!!! Please enter input in the form: class XXX /every YYY");
                }
                break;
            
            /**
            * Command should be in form schedule view-day|month|year.
            */
            case "schedule":
            Schedule schedule = new Schedule();
            if (word[1].equals("view-week")) {
                System.out.println(schedule.getWeek());
            }
            else if (word[1].equals("view-month")) {
                System.out.println(schedule.getMonth());
            }

            else if (word[1].equals("view-day")) {
                try {
                    System.out.println(schedule.getDay(Integer.parseInt(word[2])));
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Enter a date please.");
                }
            }
            break;

            /**
             *  Cmd "home" will list the menu items;
             *  1. View Schedule
             *  2. Manage Students
             *  3. Training Circuits
             */
            case "home":
                Ui viewMenu = new Ui();
                viewMenu.mainMenu();
                break;
            
            /**
             // Choosing Option 1 wil direct to "Training Schedule"
             */
            case "1":
                // Write go to direct to View Schedule (Scott)
                break;

            /**
             * Choosing option 2 will direct to "Manage Students"
             */
            case "2":
                ManageStudents viewCategory = new ManageStudents();
                viewCategory.manageStudentsCategory();
                // Write Code to direct to manage Students (Danish)
                break;

            /**
             * Choosing 3 will direct to "Training Circuit"
             */

            case "3":
                //Write Code to direct to Training Circuits (JingSen)

            default:
                System.out.println("\u2639 OOPS!!! I'm sorry, but I don't know what that means :-(");
                break;
        }
    }
}
